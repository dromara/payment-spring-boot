package com.enongm.dianji.payment.wechat.v2.model;


import com.enongm.dianji.payment.PayException;
import com.enongm.dianji.payment.wechat.enumeration.V2PayType;
import com.enongm.dianji.payment.wechat.enumeration.WeChatServer;
import com.enongm.dianji.payment.wechat.v2.WechatResponseBody;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import okhttp3.*;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * The type Base model.
 *
 * @author Dax
 * @since 16 :03
 */
@Getter
public class BaseModel {
    private static final XmlMapper MAPPER = new XmlMapper();

    static {
        // 忽略null
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // 属性使用 驼峰首字母小写
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }


    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final IdGenerator ID_GENERATOR = new AlternativeJdkIdGenerator();
    private final String nonceStr = ID_GENERATOR.generateId()
            .toString()
            .replaceAll("-", "");
    private String mchAppid;
    private String mchid;
    private String sign;
    @JsonIgnore
    private String appSecret;
    @JsonIgnore
    private V2PayType payType;
    @JsonIgnore
    private final WeChatServer weChatServer = WeChatServer.CHINA;
    @JsonIgnore
    private boolean sandboxMode;
    @JsonIgnore
    private boolean partnerMode;


    public BaseModel appId(String appId) {
        this.mchAppid = appId;
        return this;
    }

    public BaseModel mchId(String mchId) {
        this.mchid = mchId;
        return this;
    }

    public BaseModel payType(V2PayType payType) {
        this.payType = payType;
        return this;
    }

    public BaseModel appSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public BaseModel sandboxMode(boolean sandboxMode) {
        this.sandboxMode = sandboxMode;
        return this;
    }
    public BaseModel partnerMode(boolean partnerMode) {
        this.partnerMode = partnerMode;
        return this;
    }

    /**
     * Xml string.
     *
     * @return the string
     */
    @SneakyThrows
    public String xml() {
        String link = link(this);
        this.sign = this.bouncyCastleMD5(link);
        return MAPPER.writer()
                .withRootName("xml")
                .writeValueAsString(this);
    }

    /**
     * md5摘要.
     *
     * @param src the src
     * @return the string
     */
    private String bouncyCastleMD5(String src) {
        Digest digest = new MD5Digest();
        byte[] bytes = src.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes, 0);
        return Hex.toHexString(md5Bytes).toUpperCase();
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
        Assert.hasText(this.mchAppid, "wechat pay appId is required");
        Assert.hasText(this.mchid, "wechat pay mchId is required");
        Assert.hasText(appSecret, "wechat pay appSecret is required");
        return new ObjectMapper()
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .writer()
                .writeValueAsString(t)
                .replaceAll("\":\"", "=")
                .replaceAll("\",\"", "&")
                .replaceAll("\\{\"", "")
                .replaceAll("\"}", "")
                .concat("&key=").concat(this.appSecret);
    }


    @SneakyThrows
    public WechatResponseBody request() {
        Assert.notNull(payType, "wechat pay payType is required");

        String url = payType.defaultUri(this.weChatServer);
        if (sandboxMode) {
            url = partnerMode ? this.payType.partnerSandboxUri(this.weChatServer) :
                    this.payType.defaultSandboxUri(this.weChatServer);
        }

        Request request = new Request.Builder()
                .method(payType.method(), RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), this.xml()))
                .url(url)
                .build();
        System.out.println("request.toString() = " + request.toString());
        Response response = CLIENT.newCall(request).execute();

        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            return MAPPER.readValue(body.string(), WechatResponseBody.class);
        }
        throw new PayException("wechat pay response body is empty");
    }

}
