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
package cn.felord.payment.wechat.v3.model.busifavor;

import cn.felord.payment.wechat.enumeration.BusiFavorCodeDisplayMode;
import lombok.Data;

/**
 * 商家券核销规则-自定义入口
 * <p>
 * 卡详情页面，可选择多种入口引导用户。
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class CustomEntrance {

    /**
     * 可用门店id
     * <p>
     *  不可修改项
     */
    private String storeId;
    /**
     * 营销馆id
     */
    private String hallId;
    /**
     * 小程序入口
     */
    private MiniProgramsInfo miniProgramsInfo;
    /**
     * 商户公众号appid
     * <p>
     * 从券详情可跳转至公众号
     */
    private String appid;
    /**
     * code展示模式
     */
    private BusiFavorCodeDisplayMode codeDisplayMode;

}