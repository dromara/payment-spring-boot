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
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * The type User coupons query params.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class UserCouponsQueryParams {
    /**
     * 用户公众号服务号标识
     */
    private String openId;
    /**
     * 公众服务号ID
     */
    private String appId;
    /**
     * 批次号
     */
    private String stockId;
    /**
     * 券状态  null 不生效
     */
    private Status status;
    /**
     * 创建批次的商户号
     */
    private String creatorMchId;
    /**
     * 批次发放商户号
     */
    private String senderMchId;
    /**
     * 可用商户号
     */
    private String availableMchId;
    /**
     * 分页页码
     */
    private Integer offset = 0;
    /**
     * 分页大小
     */
    private Integer limit = 20;


    /**
     * The enum Status.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    public enum Status {
        /**
         * Sended status.
         */
        SENDED,
        /**
         * Used status.
         */
        USED
    }

}
