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
 */
package cn.felord.payment.wechat.v3.model.discountcard;

import lombok.Data;

import java.util.List;

/**
 * 增加用户记录请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class UserRecordsParams {
    /**
     * 商户领卡号，商户在请求领卡预受理接口时传入的领卡请求号，同一个商户号下必须唯一，要求32个字符内，只能是数字、大小写字母_-|*
     */
    private String outCardCode;
    /**
     * 先享卡模板ID，唯一定义此资源的标识。创建模板后可获得
     */
    private String cardTemplateId;
    /**
     * 微信先享卡目标完成纪录
     */
    private List<ObjectiveCompletionRecord> objectiveCompletionRecords;
    /**
     * 优惠使用纪录
     */
    private List<RewardUsageRecord> rewardUsageRecords;

}
