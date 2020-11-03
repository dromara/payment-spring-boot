package com.enongm.dianji.payment.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Dax
 * @since 9:49
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PayConfiguration.class)
public @interface EnableMobilePay {
}
