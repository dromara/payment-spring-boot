package com.enongm.dianji.payment.wechat.v3;

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
 * @author Dax
 * @since 14 :01
 */
@Getter
public class WechatRequestEntity<T>  extends RequestEntity<T> {

    private final Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer;


    /**
     * Instantiates a new Wechat request entity.
     *
     * @param method               the method
     * @param url                  the url
     * @param responseBodyConsumer the response body consumer
     */
    public WechatRequestEntity(HttpMethod method, URI url, Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
        super(method, url);
        this.responseBodyConsumer = responseBodyConsumer;
    }

    /**
     * Instantiates a new Wechat request entity.
     *
     * @param body                 the body
     * @param method               the method
     * @param url                  the url
     * @param responseBodyConsumer the response body consumer
     */
    public WechatRequestEntity(T body, HttpMethod method, URI url, Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
        super(body, method, url);
        this.responseBodyConsumer = responseBodyConsumer;
    }

    /**
     * Instantiates a new Wechat request entity.
     *
     * @param body                 the body
     * @param method               the method
     * @param url                  the url
     * @param type                 the type
     * @param responseBodyConsumer the response body consumer
     */
    public WechatRequestEntity(T body, HttpMethod method, URI url, Type type, Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
        super(body, method, url, type);
        this.responseBodyConsumer = responseBodyConsumer;
    }

    /**
     * Instantiates a new Wechat request entity.
     *
     * @param headers              the headers
     * @param method               the method
     * @param url                  the url
     * @param responseBodyConsumer the response body consumer
     */
    public WechatRequestEntity(MultiValueMap<String, String> headers, HttpMethod method, URI url, Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
        super(headers, method, url);
        this.responseBodyConsumer = responseBodyConsumer;
    }

    /**
     * Instantiates a new Wechat request entity.
     *
     * @param body                 the body
     * @param headers              the headers
     * @param method               the method
     * @param url                  the url
     * @param responseBodyConsumer the response body consumer
     */
    public WechatRequestEntity(T body, MultiValueMap<String, String> headers, HttpMethod method, URI url, Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
        super(body, headers, method, url);
        this.responseBodyConsumer = responseBodyConsumer;
    }

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
