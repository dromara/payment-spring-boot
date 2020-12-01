package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 10:13
 */
@Data
public class CallbackParams {
    private String id;
    private String createTime;
    private String eventType;
    private String resourceType;
    private String summary;
    private Resource resource;


    @Data
    public static class Resource {
        private String algorithm;
        private String ciphertext;
        private String associatedData;
        private String nonce;
        private String originalType;
    }

}
