package com.enongm.dianji.payment.autoconfigure;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dax
 * @since 14:35
 */
@Configuration
@EnableConfigurationProperties(AliPayProperties.class)
public class AliPayConfiguration {
}
