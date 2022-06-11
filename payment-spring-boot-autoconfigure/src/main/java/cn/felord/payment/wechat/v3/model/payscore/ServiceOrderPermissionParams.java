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
 *
 */
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 商户预授权API请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class ServiceOrderPermissionParams {

    /**
     * 服务id，必填
     */
    private String serviceId;
    /**
     * 服务商申请的公众号或移动应用APPID，必填
     */
    private String appid;
    /**
     * 授权协议号，必填
     * <p>
     * 预授权成功时的授权协议号，要求此参数只能由数字、大小写字母_-*组成，且在同一个商户号下唯一。详见[商户订单号]。
     */
    private String authorizationCode;
    /**
     * 商户接收授权回调通知的地址，选填
     */
    private String notifyUrl;

}
