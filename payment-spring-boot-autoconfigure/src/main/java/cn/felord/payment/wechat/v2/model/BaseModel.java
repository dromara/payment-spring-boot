/*
 *  Copyright 2019-2021 felord.cn
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

package cn.felord.payment.wechat.v2.model;


import cn.felord.payment.PayException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * The type Base model.
 *
 * @author felord.cn
 * @since 1.0.5.RELEASE
 */
@Getter
public abstract class BaseModel {
    public static final String HMAC_SHA256="HMAC-SHA256";
    private static final XmlMapper XML_MAPPER = new XmlMapper();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 忽略null
        XML_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // 属性使用 驼峰首字母小写
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        OBJECT_MAPPER
//                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }


    private static final IdGenerator ID_GENERATOR = new AlternativeJdkIdGenerator();
    private final String nonceStr = ID_GENERATOR.generateId()
            .toString()
            .replaceAll("-", "");
    private String sign;
    @JsonIgnore
    private String appSecret;
    @JsonIgnore
    private String certPath;
    @JsonIgnore
    private String signType;

    public BaseModel appSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public BaseModel certPath(String certPath) {
        this.certPath = certPath;
        return this;
    }


    public BaseModel signType(String signType) {
        this.signType = signType;
        return this;
    }

    /**
     * Xml string.
     *
     * @return the string
     */
    @SneakyThrows
    private String xml() {
        String link = link(this);
        if (HMAC_SHA256.equals(signType)) {
            this.sign = this.hmacSha256(link);
        } else {
            this.sign = this.md5(link);
        }
        return XML_MAPPER.writer()
                .withRootName("xml")
                .writeValueAsString(this);
    }

    /**
     * md5摘要.
     *
     * @param src the src
     * @return the string
     */
    private String md5(String src) {
        Digest digest = new MD5Digest();
        byte[] bytes = src.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes, 0);
        return Hex.toHexString(md5Bytes).toUpperCase();
    }

    /**
     * hmacSha256.
     *
     * @param src the src
     * @return the string
     */
    @SneakyThrows
    private String hmacSha256(String src) {
        String algorithm = "HmacSHA256";
        Mac sha256HMAC = Mac.getInstance(algorithm,"BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(appSecret.getBytes(), algorithm);
        sha256HMAC.init(secretKeySpec);
        byte[] bytes = sha256HMAC.doFinal(src.getBytes(StandardCharsets.UTF_8));
        return Hex.toHexString(bytes).toUpperCase();
    }

    /**
     * 按照格式拼接参数以生成签名
     *
     * @param <T> the type parameter
     * @param t   the t
     * @return the map
     */
    @SneakyThrows
    private <T> String link(T t) {
        Assert.hasText(appSecret, "wechat pay appSecret is required");
          String json = OBJECT_MAPPER
                .writeValueAsString(t);

        TreeMap<String, String> map = OBJECT_MAPPER.readValue(json, new TypeReference<TreeMap<String, String>>() {
        });

        String query = map.entrySet()
                .stream()
                .map(entry -> entry.getKey().concat("=").concat(entry.getValue()))
                .collect(Collectors.joining("&"));
        return query.concat("&key=").concat(this.appSecret);
    }


    @SneakyThrows
    public JsonNode request(String mchId, HttpMethod method, String url) {
        String xml = this.xml();
        RequestEntity<String> body = RequestEntity.method(method, UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri())
                .contentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=UTF-8"))
                .body(xml);
        ResponseEntity<String> responseEntity = this.getRestTemplateClientAuthentication(mchId)
                .exchange(url, method, body, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new PayException("wechat pay v2 error ");
        }
        String result = responseEntity.getBody();

        return XML_MAPPER.readTree(result);
    }


    private RestTemplate getRestTemplateClientAuthentication(String mchId)
            throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {
        ClassPathResource resource = new ClassPathResource(certPath == null ? "wechat/apiclient_cert.p12" : certPath);

        char[] pem = mchId.toCharArray();

        KeyStore store = KeyStore.getInstance("PKCS12");
        store.load(resource.getInputStream(), pem);

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContextBuilder.create()
                .loadKeyMaterial(store, pem)
                .build();
        // Allow TLSv1 protocol only
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"},
                null, hostnameVerifier);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
        return new RestTemplate(clientHttpRequestFactory);
    }
}
