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

package cn.felord.payment.wechat.v3.model.ecommerce;

import cn.felord.payment.wechat.enumeration.IdDocType;
import lombok.Data;

import java.time.LocalDate;

/**
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
@Data
public class UboInfo {
    private IdDocType idDocType;
    private IdCardInfo idCardInfo;
    private IdDocInfo idDocInfo;

    /**
     * The type Id card info.
     */
    @Data
    public static class IdCardInfo {
        private String idCardCopy;
        private String idCardNational;
        private String idCardName;
        private String idCardNumber;
        private LocalDate idCardPeriodBegin;
        private String idCardPeriodEnd;
    }

    /**
     * The type Id doc info.
     */
    @Data
    public static class IdDocInfo {
        private String idDocCopy;
        private String idDocName;
        private String idDocNumber;
        private LocalDate idDocPeriodBegin;
        private String idDocPeriodEnd;
    }
}