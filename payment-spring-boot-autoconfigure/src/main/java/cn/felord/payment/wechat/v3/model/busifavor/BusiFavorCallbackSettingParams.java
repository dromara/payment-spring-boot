/*
 *  Copyright 2019-2021 felord.cn
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
package cn.felord.payment.wechat.v3.model.busifavor;

import lombok.Data;

/**
 * 设置商家券事件通知地址API请求参数
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class BusiFavorCallbackSettingParams {

    /**
     * 微信支付商户的商户号，由微信支付生成并下发，不填默认查询调用方商户的通知URL。
     */
    private String mchid;
    /**
     * 商户提供的用于接收商家券事件通知的url地址，必须支持https。
     */
    private String notifyUrl;
}
