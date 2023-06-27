/*
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
 */

package cn.felord.payment.wechat.v3.client;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * The type Retrofit factory.
 *
 * @author dax
 * @since 2023 /5/21
 */
public final class RetrofitFactory {

    /**
     * The constant JACKSON_CONVERTER_FACTORY.
     */
    public static final JacksonConverterFactory JACKSON_CONVERTER_FACTORY = JacksonConverterFactoryBuilder.build();

    /**
     * Create retrofit.
     *
     * @param baseUrl the base url
     * @return the retrofit
     */
    public static Retrofit create(String baseUrl) {
        return create(baseUrl, new ConnectionPool());
    }

    /**
     * Create retrofit.
     *
     * @param baseUrl        the base url
     * @param connectionPool the connection pool
     * @return the retrofit
     */
    public static Retrofit create(String baseUrl, ConnectionPool connectionPool) {
        return create(baseUrl, connectionPool, HttpLoggingInterceptor.Level.NONE);
    }

    /**
     * Create retrofit.
     *
     * @param baseUrl        the base url
     * @param connectionPool the connection pool
     * @param level          the level
     * @return the retrofit
     */
    public static Retrofit create(String baseUrl, ConnectionPool connectionPool, HttpLoggingInterceptor.Level level) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient(connectionPool, level))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JACKSON_CONVERTER_FACTORY)
                .build();
    }

    private static OkHttpClient okHttpClient(ConnectionPool connectionPool, HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(level);
        return new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }


    /**
     * The type Jackson converter factory builder.
     */
    static final class JacksonConverterFactoryBuilder {
        private JacksonConverterFactoryBuilder() {
        }

        /**
         * Build jackson converter factory.
         *
         * @return the jackson converter factory
         */
        public static JacksonConverterFactory build() {
            ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    // empty string error
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .registerModule(new JavaTimeModule());
            return JacksonConverterFactory.create(objectMapper);
        }
    }
}
