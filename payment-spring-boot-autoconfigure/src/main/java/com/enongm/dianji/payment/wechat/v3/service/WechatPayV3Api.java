package com.enongm.dianji.payment.wechat.v3.service;


import com.enongm.dianji.payment.wechat.enumeration.V3PayType;
import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;
import com.enongm.dianji.payment.wechat.v3.model.AppPayModel;

/**
 * The type Wechat pay v 3 api.
 *
 * @author Dax
 * @since 17 :47
 */
public class WechatPayV3Api {

    /**
     * APP 支付.
     *
     * @param appPayModel the app pay model
     * @return the wechat pay request
     */
    public static WechatPayRequest appPay(AppPayModel appPayModel) {

        return new WechatPayRequest().responseConsumer(System.out::println)
                // 如果需要支持容灾 需要对server 进行一些处理
                .v3PayType(V3PayType.APP)
                .body(appPayModel.jsonBody());
    }


}
