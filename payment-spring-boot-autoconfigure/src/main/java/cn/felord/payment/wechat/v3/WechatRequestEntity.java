/*
 *
 *  Copyright 2019-2020 felord.cn
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

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.function.Consumer;

/**
 * The type Wechat request entity.
 *
 * @param <T> the type parameter
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Getter
public class WechatRequestEntity<T> extends RequestEntity<T> {

    /**
     * The Response body consumer.
     */
    private final Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer;

    /**
     * Instantiates a new Wechat request entity.
     *
     * @param body                 the body
     * @param headers              the headers
     * @param method               the method
     * @param url                  the url
     * @param type                 the type
     * @param responseBodyConsumer the response body consumer
     */
    public WechatRequestEntity(T body, MultiValueMap<String, String> headers, HttpMethod method, URI url, Type type, Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
        super(body, headers, method, url, type);
        this.responseBodyConsumer = responseBodyConsumer;
    }

    /**
     * Headers wechat request entity.
     *
     * @param httpHeaders the http headers
     * @return the wechat request entity
     */
    public WechatRequestEntity<T> headers(HttpHeaders httpHeaders) {
        return new WechatRequestEntity<>(this.getBody(),
                httpHeaders,
                this.getMethod(),
                this.getUrl(),
                this.getType(),
                this.responseBodyConsumer);
    }


    /**
     * Of wechat request entity.
     *
     * @param requestEntity        the request entity
     * @param responseBodyConsumer the response body consumer
     * @return the wechat request entity
     */
    public static WechatRequestEntity<?> of(RequestEntity<?> requestEntity, Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
        return new WechatRequestEntity<>(requestEntity.getBody(),
                requestEntity.getHeaders(),
                requestEntity.getMethod(),
                requestEntity.getUrl(),
                requestEntity.getType(),
                responseBodyConsumer);
    }
}
