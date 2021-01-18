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

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;
/**
 * 商家券核销规则-券可核销时间-无规律的有效时间段
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class IrregularyAvaliableTimeItem{

	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
	private OffsetDateTime beginTime;
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
	private OffsetDateTime endTime;
}