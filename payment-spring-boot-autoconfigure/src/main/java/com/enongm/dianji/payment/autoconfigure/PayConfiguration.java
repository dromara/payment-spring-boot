package com.enongm.dianji.payment.autoconfigure;

import com.enongm.dianji.payment.alipay.AliPayConfiguration;
import com.enongm.dianji.payment.wechat.WechatPayConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Dax
 * @since 14:47
 */
@Configuration
@Import({WechatPayConfiguration.class, AliPayConfiguration.class})
public class PayConfiguration {
}
