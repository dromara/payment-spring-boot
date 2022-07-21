/*
 *
 *  Copyright 2019-2022 felord.cn
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
 *
 */
package cn.felord.payment.wechat.v3;


import cn.felord.payment.PayException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.security.*;
import java.security.cert.X509Certificate;

/**
 * 证书工具
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class KeyPairFactory {

    private static final KeyStore PKCS12_KEY_STORE;

       static {
           try {
               PKCS12_KEY_STORE = KeyStore.getInstance("PKCS12");
           } catch (KeyStoreException e) {
             throw new PayException(" wechat pay keystore initialization failed");
           }
       }


    /**
     * 获取公私钥.
     *
     * @param resource the resource
     * @param keyAlias the key alias
     * @param keyPass  password
     * @return the key pair
     */
    public WechatMetaBean initWechatMetaBean(Resource resource, String keyAlias, String keyPass) {

        char[] pem = keyPass.toCharArray();
        try {
            PKCS12_KEY_STORE.load(resource.getInputStream(), pem);
            X509Certificate certificate = (X509Certificate) PKCS12_KEY_STORE.getCertificate(keyAlias);
            certificate.checkValidity();
            String serialNumber = certificate.getSerialNumber().toString(16).toUpperCase();
            PublicKey publicKey = certificate.getPublicKey();
            PrivateKey storeKey = (PrivateKey) PKCS12_KEY_STORE.getKey(keyAlias, pem);
            WechatMetaBean wechatMetaBean = new WechatMetaBean();
            wechatMetaBean.setKeyPair(new KeyPair(publicKey, storeKey));
            wechatMetaBean.setSerialNumber(serialNumber);
            return wechatMetaBean;
        } catch (Exception e) {
            throw new PayException("Cannot load keys from store: " + resource, e);
        }
    }
}
