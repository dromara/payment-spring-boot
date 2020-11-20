package com.enongm.dianji.payment.wechat.v3;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

/**
 * @author Dax
 * @since 13:20
 */
@Slf4j
@Data
public class WechatResponseEntity<T> {
    private int httpStatus;
    private T body;


    public void convert(ResponseEntity<T> responseEntity) {
        if (log.isDebugEnabled()) {
            log.info("wechat response {}", responseEntity);
        }
        this.httpStatus = responseEntity.getStatusCodeValue();
        this.body = responseEntity.getBody();
    }
}
