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
package cn.felord.payment.wechat.v3.model.batchtransfer;

import cn.felord.payment.wechat.enumeration.TransferAcceptType;
import lombok.Data;

/**
 * 转账明细电子回单受理API请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class TransferDetailElectronicParams {
    /**
     * 电子回单受理类型，必填。
     *
     * @see TransferAcceptType
     */
    private TransferAcceptType acceptType;
    /**
     * 商家转账批次单号，选填。
     * <p>
     * 需要电子回单的批量转账明细单所在的转账批次单号，该单号为商户申请转账时生成的商户单号。
     * 受理类型为{@code BATCH_TRANSFER}时该单号必填，否则该单号留空。
     */
    private String outBatchNo;
    /**
     * 商家转账明细单号，必填。
     * <ul>
     *     <li>受理类型为{@code BATCH_TRANSFER}时填写商家批量转账明细单号。</li>
     *     <li>受理类型为{@code TRANSFER_TO_POCKET}或{@code TRANSFER_TO_BANK}时填写商家转账单号。</li>
     * </ul>
     */
    private String outDetailNo;
}
