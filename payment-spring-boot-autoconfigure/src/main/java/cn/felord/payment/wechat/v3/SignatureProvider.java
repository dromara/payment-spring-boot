/*
 *
 *  Copyright 2019-2020 felord.cn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package cn.felord.payment.wechat.v3;


import cn.felord.payment.PayException;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 签名 加签 验签
 * <p>
 * 我方请求微信服务器时，需要根据我方的API证书对参数进行加签；微信服务器会根据我方签名验签以确定请求来自我方服务器；
 * <p>
 * 然后微信服务器响应我方请求并在响应报文中使用【微信平台证书】加签 我方需要根据规则验签是否响应来自微信支付服务器
 * <p>
 * 其中【微信平台证书】定期会进行更新，不受我方管控，我方需要适当的时候获取最新的证书列表。
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Slf4j
public class SignatureProvider {

    /**
     * The constant ID_GENERATOR.
     */
    private final IdGenerator nonceStrGenerator = new AlternativeJdkIdGenerator();
    /**
     * The constant SCHEMA.
     */
    private static final String SCHEMA = "WECHATPAY2-SHA256-RSA2048 ";
    /**
     * The constant TOKEN_PATTERN.
     */
    public static final String TOKEN_PATTERN = "mchid=\"%s\",nonce_str=\"%s\",timestamp=\"%d\",serial_no=\"%s\",signature=\"%s\"";
    /**
     * 微信平台证书容器  key = 序列号  value = 证书对象
     */
    private static final Map<String, Certificate> CERTIFICATE_MAP = new ConcurrentHashMap<>();
    /**
     * 加密算法提供方 - BouncyCastle
     */
    private static final String BC_PROVIDER = "BC";


    /**
     * The Rest operations.
     */
    private final RestOperations restOperations = new RestTemplate();
    /**
     * The Wechat meta container.
     */
    private final WechatMetaContainer wechatMetaContainer;

    /**
     * Instantiates a new Signature provider.
     *
     * @param wechatMetaContainer the wechat meta container
     */
    public SignatureProvider(WechatMetaContainer wechatMetaContainer) {
        Provider bouncyCastleProvider = new BouncyCastleProvider();
        Security.addProvider(bouncyCastleProvider);
        this.wechatMetaContainer = wechatMetaContainer;
        wechatMetaContainer.getTenantIds().forEach(this::refreshCertificate);
    }


    /**
     * 我方请求前用 SHA256withRSA 加签，使用API证书.
     *
     * @param tenantId     the properties key
     * @param method       the method
     * @param canonicalUrl the canonical url
     * @param body         the body
     * @return the string
     */
    @SneakyThrows
    public String requestSign(String tenantId, String method, String canonicalUrl, String body) {

        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String nonceStr = nonceStrGenerator.generateId()
                .toString()
                .replaceAll("-", "");
        WechatMetaBean wechatMetaBean = wechatMetaContainer.getWechatMeta(tenantId);
        PrivateKey privateKey = wechatMetaBean.getKeyPair().getPrivate();
        String encode = this.doRequestSign(privateKey, method, canonicalUrl, String.valueOf(timestamp), nonceStr, body);
        // 序列号
        String serialNo = wechatMetaBean.getSerialNumber();
        // 生成token
        String token = String.format(TOKEN_PATTERN,
                wechatMetaBean.getV3().getMchId(),
                nonceStr, timestamp, serialNo, encode);
        return SCHEMA.concat(token);
    }


    /**
     * Do request sign.
     *
     * @param privateKey        the private key
     * @param orderedComponents the orderedComponents
     * @return the string
     * @since 1.0.4.RELEASE
     */
    @SneakyThrows
    public String doRequestSign(PrivateKey privateKey, String... orderedComponents) {
        Signature signer = Signature.getInstance("SHA256withRSA", BC_PROVIDER);
        signer.initSign(privateKey);
        final String signatureStr = createSign(true, orderedComponents);
        signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(signer.sign());
    }

    /**
     * 我方对响应验签，和应答签名做比较，使用微信平台证书.
     *
     * @param params the params
     * @return the boolean
     */
    @SneakyThrows
    public boolean responseSignVerify(ResponseSignVerifyParams params) {

        String wechatpaySerial = params.getWechatpaySerial();
        if (CERTIFICATE_MAP.isEmpty() || !CERTIFICATE_MAP.containsKey(wechatpaySerial)) {
            wechatMetaContainer.getTenantIds().forEach(this::refreshCertificate);
        }
        Certificate certificate = CERTIFICATE_MAP.get(wechatpaySerial);

        final String signatureStr = createSign(true, params.getWechatpayTimestamp(), params.getWechatpayNonce(), params.getBody());
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initVerify(certificate);
        signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));

        return signer.verify(Base64Utils.decodeFromString(params.getWechatpaySignature()));
    }


    /**
     * 当我方服务器不存在平台证书或者证书同当前响应报文中的证书序列号不一致时应当刷新  调用/v3/certificates
     *
     * @param tenantId tenantId
     */
    @SneakyThrows
    private synchronized void refreshCertificate(String tenantId) {
        String url = WechatPayV3Type.CERT.uri(WeChatServer.CHINA);

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

        String canonicalUrl = uri.getPath();
        String encodedQuery = uri.getQuery();

        if (encodedQuery != null) {
            canonicalUrl += "?" + encodedQuery;
        }
        // 签名
        HttpMethod httpMethod = WechatPayV3Type.CERT.method();
        String authorization = requestSign(tenantId, httpMethod.name(), canonicalUrl, "");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authorization);
        headers.add("User-Agent", "X-Pay-Service");
        RequestEntity<?> requestEntity = new RequestEntity<>(headers, httpMethod, uri.toUri());
        ResponseEntity<ObjectNode> responseEntity = restOperations.exchange(requestEntity, ObjectNode.class);
        ObjectNode bodyObjectNode = responseEntity.getBody();

        if (Objects.isNull(bodyObjectNode)) {
            throw new PayException("cant obtain the response body");
        }
        ArrayNode certificates = bodyObjectNode.withArray("data");
        if (certificates.isArray() && certificates.size() > 0) {
            CERTIFICATE_MAP.remove(tenantId);
            final CertificateFactory certificateFactory = CertificateFactory.getInstance("X509", BC_PROVIDER);
            certificates.forEach(objectNode -> {
                JsonNode encryptCertificate = objectNode.get("encrypt_certificate");
                String associatedData = encryptCertificate.get("associated_data").asText();
                String nonce = encryptCertificate.get("nonce").asText();
                String ciphertext = encryptCertificate.get("ciphertext").asText();
                String publicKey = decryptResponseBody(tenantId, associatedData, nonce, ciphertext);

                ByteArrayInputStream inputStream = new ByteArrayInputStream(publicKey.getBytes(StandardCharsets.UTF_8));

                try {
                    Certificate certificate = certificateFactory.generateCertificate(inputStream);
                    String responseSerialNo = objectNode.get("serial_no").asText();
                    CERTIFICATE_MAP.put(responseSerialNo, certificate);
                } catch (CertificateException e) {
                    throw new PayException("An error occurred while generating the wechat v3 certificate, reason : " + e.getMessage());
                }
            });
        }
    }

    /**
     * 解密响应体.
     *
     * @param tenantId       the properties key
     * @param associatedData the associated data
     * @param nonce          the nonce
     * @param ciphertext     the ciphertext
     * @return the string
     */
    public String decryptResponseBody(String tenantId, String associatedData, String nonce, String ciphertext) {

        try {
            Assert.hasText(associatedData, "associatedData is invalid");
            Assert.hasText(nonce, "nonce is invalid");
            Assert.hasText(ciphertext, "ciphertext is invalid");
        } catch (Exception e) {
            throw new PayException(e.getMessage());
        }

        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", BC_PROVIDER);
            String apiV3Key = wechatMetaContainer.getWechatMeta(tenantId).getV3().getAppV3Secret();
            SecretKeySpec key = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(128, nonce.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData.getBytes(StandardCharsets.UTF_8));

            byte[] bytes;
            try {
                bytes = cipher.doFinal(Base64Utils.decodeFromString(ciphertext));
            } catch (GeneralSecurityException e) {
                throw new PayException(e);
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            throw new PayException(e);
        }
    }

    /**
     * 对请求敏感字段进行加密
     *
     * @param message     the message
     * @param certificate the certificate
     * @return encrypt message
     * @since 1.0.6.RELEASE
     */
    public String encryptRequestMessage(String message, Certificate certificate) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", BC_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());

            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            byte[] cipherData = cipher.doFinal(data);
            return Base64Utils.encodeToString(cipherData);

        } catch (Exception e) {
            throw new PayException(e);
        }
    }

    /**
     * Get certificate x 509 wechat certificate info.
     *
     * @return the x 509 wechat certificate info
     */
    public X509WechatCertificateInfo getCertificate() {
        for (String serial : CERTIFICATE_MAP.keySet()) {
            X509Certificate x509Cert = (X509Certificate) CERTIFICATE_MAP.get(serial);
            try {
                x509Cert.checkValidity();
                X509WechatCertificateInfo x509WechatCertificateInfo = new X509WechatCertificateInfo();
                x509WechatCertificateInfo.setWechatPaySerial(serial);
                x509WechatCertificateInfo.setX509Certificate(x509Cert);
                return x509WechatCertificateInfo;
            } catch (Exception e) {
                log.warn("the wechat certificate is invalid , {}", e.getMessage());
                // Async?
                wechatMetaContainer.getTenantIds().forEach(this::refreshCertificate);
            }
        }
        throw new PayException("failed to obtain wechat pay x509Certificate ");
    }


    /**
     * Wechat meta container.
     *
     * @return the wechat meta container
     */
    public WechatMetaContainer wechatMetaContainer() {
        return wechatMetaContainer;
    }

    /**
     * Nonce generator.
     *
     * @return the id generator
     * @since 1.0.4.RELEASE
     */
    public IdGenerator nonceStrGenerator() {
        return nonceStrGenerator;
    }

    /**
     * 请求时设置签名   组件
     *
     * @param components the components
     * @return string string
     */
    private static String createSign(boolean newLine, String... components) {

        String suffix = newLine ? "\n" : "";
        return Arrays.stream(components)
                .collect(Collectors.joining("\n", "", suffix));
    }

}
