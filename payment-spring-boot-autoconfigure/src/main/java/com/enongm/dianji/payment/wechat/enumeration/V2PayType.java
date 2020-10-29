package com.enongm.dianji.payment.wechat.enumeration;

/**
 * @author Dax
 * @since 10:33
 */
public enum V2PayType {

    /**
     * 企业向微信用户个人付款,目前支持向指定微信用户的openid付款。
     */
    PAY_TO_WECHAT("POST", "%s%s/mmpaymkttransfers/promotion/transfers");

    private final String method;
    private final String pattern;

    V2PayType(String method, String pattern) {
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

        final String sandboxKey = isSandbox ? "/sandboxnew" : "";
        final String partnerKey = isPartner ? "/partner" : "";
        return String.format(this.pattern, weChatServer.domain(), sandboxKey, partnerKey);
    }
}
