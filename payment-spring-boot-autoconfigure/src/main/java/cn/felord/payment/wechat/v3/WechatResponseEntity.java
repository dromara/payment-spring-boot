/*
 *
 *  Copyright 2019-2022 felord.cn
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
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Slf4j
@Data
public class WechatResponseEntity<T> {
    /**
     * The Http status.
     */
    private int httpStatus;
    /**
     * The Body.
     */
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
