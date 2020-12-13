package cn.felord.payment.wechat.enumeration;


/**
 * The enum We chat server domain.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum WeChatServer {
    /**
     * 中国
     */
    CHINA("https://api.mch.weixin.qq.com"),
    /**
     * 中国国内(备用域名)
     */
    CHINA2("https://api2.mch.weixin.qq.com"),
    /**
     * 香港
     */
    HK("https://apihk.mch.weixin.qq.com"),
    /**
     * 美国
     */
    US("https://apius.mch.weixin.qq.com"),
    /**
     * 获取公钥
     */
    FRAUD("https://fraud.mch.weixin.qq.com"),
    /**
     * 活动
     */
    ACTION("https://action.weixin.qq.com");


    /**
     * 域名
     */
    private final String domain;

    WeChatServer(String domain) {
        this.domain = domain;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String domain() {
        return domain;
    }

    @Override
    public String toString() {
        return domain;
    }
}
