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
 * 商家券当天可用时间段
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class AvailableDayTimeItem {

    /**
     * 当天可用开始时间，单位：秒，1代表当天0点0分1秒。
     */
    private Integer endTime;
    /**
     * 当天可用结束时间，单位：秒，86399代表当天23点59分59秒。
     */
    private Integer beginTime;
}