/*
 *  Copyright 2019-2022 felord.cn
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
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
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
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.*;
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
    private static final Set<X509WechatCertificateInfo> CERTIFICATE_SET = Collections.synchronizedSet(new HashSet<>());

    private static final Set<WeChatPublicKeyInfo> PUBLIC_KEY_SET = Collections.synchronizedSet(new HashSet<>());

    private static final String  PUBLIC_KYE_ID_PREFIX = "PUB_KEY_ID";

    /**
     * 加密算法提供方 - BouncyCastle
     */
    private static final String BC_PROVIDER = "BC";


    /**
     * The Rest operations.
     */
    private final RestOperations restOperations;
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
        RestTemplate restOperations = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restOperations.getMessageConverters();
        messageConverters.removeIf(httpMessageConverter -> httpMessageConverter instanceof MappingJackson2XmlHttpMessageConverter);
        restOperations.setMessageConverters(messageConverters);
        this.restOperations = restOperations;
        this.wechatMetaContainer = wechatMetaContainer;
        wechatMetaContainer.getTenantIds().forEach(this::refreshCertificate);
    }

    public  static void addWeChatPublicKey(WeChatPublicKeyInfo weChatPublicKeyInfo) {
        PUBLIC_KEY_SET.add(weChatPublicKeyInfo);
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

        long timestamp = Instant.now().getEpochSecond();
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
        final String signatureStr = createSign(orderedComponents);
        signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(signer.sign());
    }

    /**
     * 我方对响应验签，和应答签名做比较，使用微信平台证书.
     *
     * @param params the params
     * @return the boolean
     */
    public boolean responseSignVerify(ResponseSignVerifyParams params) {
        log.debug("wechatpaySerial: {}", params.getWechatpaySerial());
        boolean verifyResult=  params.getWechatpaySerial().startsWith(PUBLIC_KYE_ID_PREFIX)?
                responseSignVerifyWithWeChatPublicKeyInfo(params):
                responseSignVerifyWithX509WechatCertificate(params);
        log.debug("responseSignVerify: {}", verifyResult);
        return  verifyResult;
    }

    /***
     *通过平台证书进行验签
     */
    private  boolean responseSignVerifyWithX509WechatCertificate(ResponseSignVerifyParams params){
        log.debug("responseSignVerifyWithX509WechatCertificate: {}", params);
        String wechatpaySerial = params.getWechatpaySerial();
        X509WechatCertificateInfo certificate = CERTIFICATE_SET.stream()
                .filter(cert -> Objects.equals(wechatpaySerial, cert.getWechatPaySerial()))
                .findAny()
                .orElseGet(() -> {
                    wechatMetaContainer.getTenantIds().forEach(this::refreshCertificate);
                    return CERTIFICATE_SET.stream()
                            .filter(cert -> Objects.equals(wechatpaySerial, cert.getWechatPaySerial()))
                            .findAny()
                            .orElseThrow(() -> new PayException("cannot obtain the certificate"));
                });

        try {
            final String signatureStr = createSign(params.getWechatpayTimestamp(), params.getWechatpayNonce(), params.getBody());
            Signature signer = Signature.getInstance("SHA256withRSA", BC_PROVIDER);
            signer.initVerify(certificate.getX509Certificate());
            signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));
            return signer.verify(Base64Utils.decodeFromString(params.getWechatpaySignature()));
        } catch (Exception e) {
            throw new PayException("An exception occurred during the response verification, the cause: " + e.getMessage());
        }
    }

    /***
     *通过微信支付公钥进行验签
     */
    private boolean responseSignVerifyWithWeChatPublicKeyInfo(ResponseSignVerifyParams params){
        log.debug("responseSignVerifyWithWeChatPublicKeyInfo: {}", params);
        String wechatpaySerial = params.getWechatpaySerial();
        if (wechatpaySerial.startsWith(PUBLIC_KYE_ID_PREFIX)){
            WeChatPublicKeyInfo info = PUBLIC_KEY_SET.stream()
                    .filter(key -> Objects.equals(wechatpaySerial, key.getPublicKeyId()))
                    .findAny()
                    .orElseThrow(() -> new PayException("cannot obtain the public key"));

            try {
                final String signatureStr = createSign(params.getWechatpayTimestamp(), params.getWechatpayNonce(), params.getBody());
                Signature signer = Signature.getInstance("SHA256withRSA", BC_PROVIDER);
                signer.initVerify(info.getPublicKey());
                signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));
                return signer.verify(Base64Utils.decodeFromString(params.getWechatpaySignature()));
            } catch (Exception e) {
                throw new PayException("An exception occurred during the response verification, the cause: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * 当我方服务器不存在平台证书或者证书同当前响应报文中的证书序列号不一致时应当刷新  调用/v3/certificates
     *
     * @param tenantId tenantId
     */
    @SneakyThrows
    private synchronized void refreshCertificate(String tenantId) {

        WechatMetaBean wechatMetaBean = wechatMetaContainer.getWechatMeta(tenantId);
        if (wechatMetaBean.getV3().getEnableWechatPayPublic() && wechatMetaBean.getV3().getSwitchVerifySignMethod()){
            return;
        }
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
        if (certificates.isArray() && !certificates.isEmpty()) {
            CERTIFICATE_SET.removeIf(x509WechatCertificateInfo ->
                    Objects.equals(tenantId, x509WechatCertificateInfo.getTenantId()));
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
                    X509WechatCertificateInfo x509WechatCertificateInfo = new X509WechatCertificateInfo();
                    x509WechatCertificateInfo.setWechatPaySerial(responseSerialNo);
                    x509WechatCertificateInfo.setTenantId(tenantId);
                    x509WechatCertificateInfo.setX509Certificate((X509Certificate) certificate);
                    CERTIFICATE_SET.add(x509WechatCertificateInfo);
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
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchProviderException e) {
            throw new PayException(e);
        }
    }


    public String encryptRequestMessage(String message, WechatMetaBean wechatMetaBean) {
        PublicKey publicKey;
        if (wechatMetaBean.getEnableWechatPayPublicEncrypt()){
            WeChatPublicKeyInfo info=this.getWechatPublicKeyInfo(wechatMetaBean.getTenantId());
            publicKey=info.getPublicKey();
        }else {
            X509WechatCertificateInfo certificate = getCertificate(wechatMetaBean.getTenantId());
            final X509Certificate x509Certificate = certificate.getX509Certificate();
            publicKey=x509Certificate.getPublicKey();
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", BC_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            byte[] cipherData = cipher.doFinal(data);
            return Base64Utils.encodeToString(cipherData);

        } catch (Exception e) {
            throw new PayException(e);
        }
    }

    /**
     * 对请求敏感字段进行加密
     *
     * @param message     the message
     * @param publicKey the wechatPubicKey certificate
     * @return encrypt message
     * @since 1.0.6.RELEASE
     */
    @Deprecated
    public String encryptRequestMessage(String message, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", BC_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            byte[] cipherData = cipher.doFinal(data);
            return Base64Utils.encodeToString(cipherData);

        } catch (Exception e) {
            throw new PayException(e);
        }
    }

    /**
     * 对响应的敏感字段进行解密
     *
     * @param message  the message
     * @param tenantId the tenant id
     * @return encrypt message
     * @since 1.0.14.RELEASE
     */
    public String decryptResponseMessage(String message, String tenantId) {
        try {
            WechatMetaBean wechatMetaBean = wechatMetaContainer.getWechatMeta(tenantId);
            PrivateKey privateKey = wechatMetaBean.getKeyPair().getPrivate();
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", BC_PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] data = Base64Utils.decodeFromString(message);
            byte[] cipherData = cipher.doFinal(data);
            return new String(cipherData, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new PayException(e);
        }
    }

    /**
     * Get certificate x 509 wechat certificate info.
     *
     * @param tenantId the tenant id
     * @return the x 509 wechat certificate info
     */
    private X509WechatCertificateInfo getCertificate(String tenantId) {

        return CERTIFICATE_SET.stream()
                .filter(cert -> Objects.equals(tenantId, cert.getTenantId()))
                .findAny()
                .orElseGet(() -> {
                    wechatMetaContainer.getTenantIds().forEach(this::refreshCertificate);
                    return CERTIFICATE_SET.stream()
                            .filter(cert -> Objects.equals(tenantId, cert.getTenantId()))
                            .findAny()
                            .orElseThrow(() -> new PayException("cannot obtain the certificate"));
                });
    }

    private WeChatPublicKeyInfo getWechatPublicKeyInfo(String tenantId) {
        return PUBLIC_KEY_SET.stream()
                .filter(publicKeyInfo -> Objects.equals(tenantId, publicKeyInfo.getTenantId()))
                .findAny()
                .orElseThrow(
                        () -> new PayException("cannot obtain the public key")
                );
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
    private static String createSign(String... components) {
        return Arrays.stream(components)
                .collect(Collectors.joining("\n", "", "\n"));
    }

    public boolean isSwitchVerifySignMethod(String tenantId) {

        String publicKeyId=wechatMetaContainer.getWechatMeta(tenantId).getV3().getWechatPayPublicKeyId();

        Boolean switchVerifySignMethod = wechatMetaContainer.getWechatMeta(tenantId).getV3().getSwitchVerifySignMethod();

        return switchVerifySignMethod && StringUtils.hasLength(publicKeyId);
    }

    public String getWechatPublicKeyId(String tenantId) {
        return  wechatMetaContainer.getWechatMeta(tenantId).getV3().getWechatPayPublicKeyId();
    }

    public String getWechatPaySerial(WechatMetaBean wechatMetaBean) {
        if (wechatMetaBean.getEnableWechatPayPublicEncrypt()){
            return this.getWechatPublicKeyInfo(wechatMetaBean.getTenantId()).getPublicKeyId();
        }else {
            return this.getCertificate(wechatMetaBean.getTenantId()).getWechatPaySerial();
        }
    }
}
