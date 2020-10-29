package com.enongm.dianji.payment.wechat.v3;


import com.enongm.dianji.payment.wechat.enumeration.V3PayType;
import com.enongm.dianji.payment.wechat.enumeration.WeChatServer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The type Wechat pay request.
 *
 * @author Dax
 * @since 9 :18
 */
@Getter
public class WechatPayRequest {
    private V3PayType v3PayType;
    private WeChatServer weChatServer;
    private String body;
    private final Map<String, String> headers = new HashMap<>();
    private Consumer<String> responseBodyConsumer;


    /**
     * Pay type wechat pay request.
     *
     * @param v3PayType the pay type
     * @return the wechat pay request
     */
    public WechatPayRequest v3PayType(V3PayType v3PayType) {
        this.v3PayType = v3PayType;
        return this;
    }

    /**
     * We chat server.
     *
     * @param weChatServer the we chat server
     */
    public void weChatServer(WeChatServer weChatServer) {
        this.weChatServer = weChatServer;
    }


    /**
     * Body wechat pay request.
     *
     * @param body the body
     * @return the wechat pay request
     */
    public WechatPayRequest body(String body) {
        this.body = body;
        return this;
    }

    /**
     * Add header wechat pay request.
     *
     * @param name  the name
     * @param value the value
     * @return the wechat pay request
     */
    public WechatPayRequest addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * Response consumer wechat pay request.
     *
     * @param responseConsumer the response consumer
     * @return the wechat pay request
     */
    public WechatPayRequest responseConsumer(Consumer<String> responseConsumer) {
        this.responseBodyConsumer = responseConsumer;
        return this;
    }

    /**
     * Url string.
     *
     * @return the string
     */
    public String url() {
        return this.v3PayType.defaultUri(this.weChatServer);
    }

    /**
     * Default sandbox uri string.
     *
     * @return the string
     */
    public String defaultSandboxUri() {
        return this.v3PayType.defaultSandboxUri(this.weChatServer);
    }

    /**
     * Partner uri string.
     *
     * @return the string
     */
    public String partnerUri() {
        return this.v3PayType.partnerUri(this.weChatServer);
    }

    /**
     * Partner sandbox uri string.
     *
     * @return the string
     */
    public String partnerSandboxUri() {
        return this.v3PayType.partnerSandboxUri(this.weChatServer);
    }

}
