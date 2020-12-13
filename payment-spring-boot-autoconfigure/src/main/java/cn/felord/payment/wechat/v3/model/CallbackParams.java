package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 微信支付回调请求参数.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CallbackParams {
    /**
     * The Id.
     */
    private String id;
    /**
     * The Create time.
     */
    private String createTime;
    /**
     * The Event type.
     */
    private String eventType;
    /**
     * The Resource type.
     */
    private String resourceType;
    /**
     * The Summary.
     */
    private String summary;
    /**
     * The Resource.
     */
    private Resource resource;


    /**
     * The type Resource.
     */
    @Data
    public static class Resource {
        /**
         * The Algorithm.
         */
        private String algorithm;
        /**
         * The Ciphertext.
         */
        private String ciphertext;
        /**
         * The Associated data.
         */
        private String associatedData;
        /**
         * The Nonce.
         */
        private String nonce;
        /**
         * The Original type.
         */
        private String originalType;
    }

}
