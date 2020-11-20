package com.enongm.dianji.payment.wechat.v3;


import com.enongm.dianji.payment.PayException;
import org.springframework.core.io.ClassPathResource;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * KeyPairFactory
 *
 * @author dax
 * @since 13:41
 **/
public class KeyPairFactory {

    private KeyStore store;

    private final Object lock = new Object();

    /**
     * 获取公私钥.
     *
     * @param keyPath  the key path
     * @param keyAlias the key alias
     * @param keyPass  password
     * @return the key pair
     */
    public WechatMetaBean createPKCS12(String keyPath, String keyAlias, String keyPass) {
        ClassPathResource resource = new ClassPathResource(keyPath);
        char[] pem = keyPass.toCharArray();
        try {
            synchronized (lock) {
                if (store == null) {
                    synchronized (lock) {
                        store = KeyStore.getInstance("PKCS12");
                        store.load(resource.getInputStream(), pem);
                    }
                }
            }
            X509Certificate certificate = (X509Certificate) store.getCertificate(keyAlias);
                     certificate.checkValidity();
            String serialNumber = certificate.getSerialNumber().toString(16).toUpperCase();
            PublicKey publicKey = certificate.getPublicKey();
            PrivateKey storeKey = (PrivateKey) store.getKey(keyAlias, pem);
            WechatMetaBean wechatMetaBean = new WechatMetaBean();
            wechatMetaBean.setKeyPair(new KeyPair(publicKey, storeKey));
            wechatMetaBean.setSerialNumber(serialNumber);
            return wechatMetaBean;
        } catch (Exception e) {
            throw new PayException("Cannot load keys from store: " + resource, e);
        }
    }
}
