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
 */
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

import java.util.function.Consumer;

/**
 * 支付分回调复合消费器
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PayScoreConsumer {
    /**
     * 用户确认回调消费接口
     */
    private Consumer<PayScoreUserConfirmConsumeData> confirmConsumeDataConsumer;
    /**
     * 用户支付回调消费接口
     */
    private Consumer<PayScoreUserPaidConsumeData> paidConsumeDataConsumer;
}
