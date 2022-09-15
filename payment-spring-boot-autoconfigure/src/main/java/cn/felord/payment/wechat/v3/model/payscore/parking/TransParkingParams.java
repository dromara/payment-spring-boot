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

package cn.felord.payment.wechat.v3.model.payscore.parking;

import cn.felord.payment.wechat.v3.model.Amount;
import lombok.Data;

/**
 * 扣费受理API参数
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
@Data
public class TransParkingParams {
    /**
     * 应用ID，必传
     * <p>
     * appid是商户在微信申请公众号或移动应用成功后分配的账号ID，登录平台为mp.weixin.qq.com或open.weixin.qq.com
     */
    private String appid;
    /**
     * 服务描述，必传
     * <p>
     * 商户自定义字段，用于交易账单中对扣费服务的描述。
     */
    private String description;
    /**
     * 附加数据，非必传
     * <p>
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。
     */
    private String attach;
    /**
     * 商户订单号，必传
     * <p>
     * 商户系统内部订单号，只能是数字、大小写字母，且在同一个商户号下唯一
     */
    private String outTradeNo;
    /**
     * 交易场景，必传
     * <p>
     * 目前支持 {@code PARKING} 车场停车场景
     */
    private String tradeScene;
    /**
     * 订单优惠标记，非必传
     * <p>
     * 代金券或立减优惠功能的参数，说明详见 <a href="https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_7&index=3">代金券或立减优惠</a>
     */
    private String goodsTag;
    /**
     * 回调通知url，必传
     */
    private String notifyUrl;
    /**
     * 分账标识，非必传，不传则不分账
     * <p>
     * 枚举值：
     * <ul>
     *     <li>Y：是，需要分账</li>
     *     <li>N：否，不分账</li>
     * </ul>
     * <p>
     * 字母要求大写，不传默认不分账，分账详细说明见<a href="http://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=26_1">直连分账API</a>、<a href="http://pay.weixin.qq.com/wiki/doc/api/allocation_sl.php?chapter=24_1&index=1">服务商分账API</a>文档
     */
    private String profitSharing;
    /**
     * 订单金额，必传
     */
    private Amount amount;
     /**
     * 停车场景信息，非必传
     * <p>
     * 当交易场景为{@code PARKING}时，需要在该字段添加停车场景信息
     */
    private ParkingInfo parkingInfo;

}
