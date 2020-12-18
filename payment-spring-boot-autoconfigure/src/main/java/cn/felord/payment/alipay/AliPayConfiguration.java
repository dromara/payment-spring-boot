/*
 *
 *  Copyright 2019-2020 felord.cn
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
package cn.felord.payment.alipay;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import cn.felord.payment.PayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "ali.pay", name = "v1.app-id")
@EnableConfigurationProperties(AliPayProperties.class)
public class AliPayConfiguration {

    @Bean
    public AlipayClient alipayClient(AliPayProperties aliPayProperties) throws AlipayApiException {

        PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

        AliPayProperties.V1 v1 = aliPayProperties.getV1();
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        propertyMapper.from(v1::getServerUrl).to(certAlipayRequest::setServerUrl);
        propertyMapper.from(v1::getAppId).to(certAlipayRequest::setAppId);
        propertyMapper.from(v1::getAppPrivateKeyPath).as(this::appRSAPrivateKey).to(certAlipayRequest::setPrivateKey);
        propertyMapper.from(v1::getFormat).to(certAlipayRequest::setFormat);
        propertyMapper.from(v1::getCharset).to(certAlipayRequest::setCharset);
        propertyMapper.from(v1::getSignType).to(certAlipayRequest::setSignType);
        propertyMapper.from(v1::getAppCertPublicKeyPath).as(this::getFileAbsolutePath).to(certAlipayRequest::setCertPath);
        propertyMapper.from(v1::getAlipayPublicCertPath).as(this::getFileAbsolutePath).to(certAlipayRequest::setAlipayPublicCertPath);
        propertyMapper.from(v1::getAlipayRootCertPath).as(this::getFileAbsolutePath).to(certAlipayRequest::setRootCertPath);
        return new DefaultAlipayClient(certAlipayRequest);
    }

    private String getFileAbsolutePath(String classPath) {
        try {
            return new ClassPathResource(classPath).getFile().getAbsolutePath();
        } catch (IOException e) {
            log.error("ali pay cert path is not exist ,{}", e.getMessage());
            throw new PayException("ali pay cert path is not exist");
        }

    }


    private String appRSAPrivateKey(String classPath) {
        try {
            File file = new ClassPathResource(classPath).getFile();
            return new BufferedReader(new FileReader(file)).readLine();
        } catch (IOException e) {
            log.error("ali pay app private key is required ,{}", e.getMessage());
            throw new PayException("ali pay app private key is required");
        }
    }


}
