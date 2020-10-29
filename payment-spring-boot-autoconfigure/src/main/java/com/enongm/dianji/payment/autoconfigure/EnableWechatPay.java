package com.enongm.dianji.payment.autoconfigure;

import org.springframework.context.annotation.Import;

/**
 * @author Dax
 * @since 9:49
 */
@Import(WechatPayConfiguration.class)
public @interface EnableWechatPay {
}
