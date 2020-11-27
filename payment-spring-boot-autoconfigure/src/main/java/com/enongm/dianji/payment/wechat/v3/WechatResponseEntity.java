package com.enongm.dianji.payment.wechat.v3;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

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
            log.debug("wechat response {}", responseEntity);
        }
        if (Objects.nonNull(responseEntity)) {
            this.httpStatus = responseEntity.getStatusCodeValue();
            this.body = responseEntity.getBody();
        } else {
            this.httpStatus = HttpStatus.REQUEST_TIMEOUT.value();
            this.body = null;
        }
    }

    public boolean successful() {
        if (log.isDebugEnabled()) {
            log.debug("wechat httpStatus {}", this.httpStatus);
        }
        return this.httpStatus == HttpStatus.OK.value();
    }
}
