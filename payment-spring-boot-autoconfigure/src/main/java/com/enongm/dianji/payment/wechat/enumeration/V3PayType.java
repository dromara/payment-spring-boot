package com.enongm.dianji.payment.wechat.enumeration;

/**
 * The enum Pay type.
 *
 * @author Dax
 * @since 14 :25
 */
public enum V3PayType {
    /**
     * 获取证书.
     */
    CERT("GET", "%s/v3/certificates"),

    /**
     * 微信公众号支付或者小程序支付
     */
    JSAPI("POST", "%s%s/v3/pay%s/transactions/jsapi"),

    /**
     * 微信扫码支付
     */
    NATIVE("POST", "%s%s/v3/pay%s/transactions/native"),

    /**
     * 微信APP支付
     */
    APP("POST", "%s%s/v3/pay%s/transactions/app"),

    /**
     * H5支付
     */
    MWEB("POST", "%s%s/v3/pay%s/transactions/h5");

    private final String pattern;
    private final String method;

    V3PayType(String method, String pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    /**
     * Method string.
     *
     * @return the string
     */
    public String method() {
        return this.method;
    }


    /**
     * 默认支付URI.
     *
     * @param weChatServer the we chat server
     * @return the string
     */
    public String defaultUri(WeChatServer weChatServer) {
        return uri(weChatServer, false, false);
    }

    /**
     * 默认支付沙盒URI.
     *
     * @param weChatServer the we chat server
     * @return the string
     */
    public String defaultSandboxUri(WeChatServer weChatServer) {
        return uri(weChatServer, true, false);
    }

    /**
     * 合作商支付URI.
     *
     * @param weChatServer the we chat server
     * @return the string
     */
    public String partnerUri(WeChatServer weChatServer) {
        return uri(weChatServer, false, true);
    }

    /**
     * 合作商支付沙盒URI.
     *
     * @param weChatServer the we chat server
     * @return the string
     */
    public String partnerSandboxUri(WeChatServer weChatServer) {
        return uri(weChatServer, true, true);
    }

    /**
     * @param isSandbox 是否是沙盒测试
     * @param isPartner 是否是合作商
     * @return uri
     */
    private String uri(WeChatServer weChatServer, boolean isSandbox, boolean isPartner) {

        if (this.equals(V3PayType.CERT)) {
            return String.format(this.pattern, WeChatServer.CHINA.domain());
        }

        final String sandboxKey = isSandbox ? "/sandboxnew" : "";
        final String partnerKey = isPartner ? "/partner" : "";
        return String.format(this.pattern, weChatServer.domain(), sandboxKey, partnerKey);
    }
}
