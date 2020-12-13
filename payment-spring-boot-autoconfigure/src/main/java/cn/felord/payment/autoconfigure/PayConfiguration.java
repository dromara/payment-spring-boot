package cn.felord.payment.autoconfigure;

import cn.felord.payment.alipay.AliPayConfiguration;
import cn.felord.payment.wechat.WechatPayConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Configuration
@Import({WechatPayConfiguration.class, AliPayConfiguration.class})
public class PayConfiguration {
}
