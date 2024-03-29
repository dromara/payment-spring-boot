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

package cn.felord.payment.wechat.v3.model.profitsharing;

import lombok.Data;

import java.util.List;

/**
 * @author dax
 * @since 2023/1/3 10:27
 */
@Data
public class BrandProfitsharingOrder {
    private final String brandMchid;
    private final String subMchid;
    private String appid;
    private final String transactionId;
    private final String outOrderNo;
    private final List<Receiver> receivers;
    private final Boolean finish;
    private String subAppid;

}
