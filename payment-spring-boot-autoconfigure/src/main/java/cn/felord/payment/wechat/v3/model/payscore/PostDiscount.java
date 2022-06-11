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
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 后付费商户优惠，选填
 * <p>
 * 最多包含30条商户优惠。如果传入，用户侧则显示此参数。
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PostDiscount {

    /**
     * 优惠名称，条件选填
     *
     * 优惠名称说明；name和description若填写，则必须同时填写，优惠名称不可重复描述。
     */
    private String name;
    /**
     * 优惠说明，条件选填
     *
     * 优惠使用条件说明。{@link PostDiscount#name}若填写，则必须同时填写。
     */
    private String description;
    /**
     * 总金额，单位分，必填
     * todo 新增没有此字段，修改必填，感觉不太符合常理
     */
    private Long amount;
    /**
     * 优惠数量，选填。
     * <p>
     * 优惠的数量。
     * 特殊规则：数量限制100，不填时默认1。
     */
    private Long count = 1L;
}
