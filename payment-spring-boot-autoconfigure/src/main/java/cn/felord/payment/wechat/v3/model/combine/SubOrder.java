
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
 *
 */
package cn.felord.payment.wechat.v3.model.combine;

import cn.felord.payment.wechat.v3.model.SettleInfo;
import lombok.Data;

/**
 * 子单信息，最多50单.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class SubOrder {
    /**
     * 合单支付订单金额信息，必填。
     */
    private CombineAmount amount;

    /**
     * 附加数据，必填，在查询API和支付通知中原样返回，可作为自定义参数使用。
     */
    private String attach;

    /**
     * 商品描述，必填，需传入应用市场上的APP名字-实际商品名称，例如：天天爱消除-游戏充值。
     */
    private String description;

    /**
     * 子单发起方商户号，必填，必须与发起方appid有绑定关系。
     */
    private String mchid;

    /**
     * 子单商户订单号，必填，商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String outTradeNo;

    /**
     * 二级商户商户号，由微信支付生成并下发。
     * <p>
     * 服务商子商户的商户号，被合单方。
     * <p>
     * 直连商户不用传二级商户号。
     */
    private String subMchid;

    /**
     * 结算信息，选填
     */
    private SettleInfo settleInfo;

}
