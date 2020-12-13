package cn.felord.payment.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PayConfiguration.class)
public @interface EnableMobilePay {
}
