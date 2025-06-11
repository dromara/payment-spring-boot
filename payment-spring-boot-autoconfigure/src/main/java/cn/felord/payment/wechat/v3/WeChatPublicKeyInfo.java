package cn.felord.payment.wechat.v3;

import lombok.Data;

import java.security.interfaces.RSAPublicKey;

@Data
public class WeChatPublicKeyInfo {

    private RSAPublicKey publicKey;

    private String publicKeyId;

    private String tenantId;

    public WeChatPublicKeyInfo(RSAPublicKey publicKey, String publicKeyId, String tenantId) {
        this.publicKeyId = publicKeyId;
        this.tenantId = tenantId;
        this.publicKey = publicKey;
    }

    public WeChatPublicKeyInfo() {
    }
}
