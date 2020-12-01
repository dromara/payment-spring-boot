package cn.felord.payment.wechat.v3;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

/**
 * The type Wechat response entity.
 *
 * @param <T> the type parameter
 * @author Dax
 * @since 13 :20
 */
@Slf4j
@Data
public class WechatResponseEntity<T> {
    private int httpStatus;
    private T body;


    /**
     * Convert {@link ResponseEntity} to {@link WechatResponseEntity}.
     *
     * @param responseEntity the response entity
     */
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


    /**
     * Is 1 xx informational boolean.
     *
     * @return the boolean
     */
    public boolean is1xxInformational() {
        if (log.isDebugEnabled()) {
            log.debug("wechat httpStatus {}", this.httpStatus);
        }
        return HttpStatus.valueOf(this.httpStatus).is1xxInformational();
    }

    /**
     * Is 2xx successful boolean.
     *
     * @return the boolean
     */
    public boolean is2xxSuccessful() {
        if (log.isDebugEnabled()) {
            log.debug("wechat httpStatus {}", this.httpStatus);
        }
        return HttpStatus.valueOf(this.httpStatus).is2xxSuccessful();
    }

    /**
     * Is 3xx redirection boolean.
     *
     * @return the boolean
     */
    public boolean is3xxRedirection() {
        if (log.isDebugEnabled()) {
            log.debug("wechat httpStatus {}", this.httpStatus);
        }
        return HttpStatus.valueOf(this.httpStatus).is3xxRedirection();
    }

    /**
     * Is 4xx client error boolean.
     *
     * @return the boolean
     */
    public boolean is4xxClientError() {
        if (log.isDebugEnabled()) {
            log.debug("wechat httpStatus {}", this.httpStatus);
        }
        return HttpStatus.valueOf(this.httpStatus).is4xxClientError();
    }

    /**
     * Is 5xx server error boolean.
     *
     * @return the boolean
     */
    public boolean is5xxServerError() {
        if (log.isDebugEnabled()) {
            log.debug("wechat httpStatus {}", this.httpStatus);
        }
        return HttpStatus.valueOf(this.httpStatus).is5xxServerError();
    }
}
