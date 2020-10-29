package com.enongm.dianji.payment.wechat.v2;


import com.enongm.dianji.payment.autoconfigure.WechatPayProperties;
import com.enongm.dianji.payment.wechat.v2.model.BaseModel;

/**
 * @author Dax
 * @since 15:15
 */
public class WechatPayV2Service {

    private final WechatPayProperties wechatPayProperties;

    public WechatPayV2Service(WechatPayProperties wechatPayProperties) {
        this.wechatPayProperties = wechatPayProperties;
    }


    public <M extends BaseModel> BaseModel model(M model) {
        WechatPayProperties.V3 v3 = wechatPayProperties.getV3();
        return model.appId(v3.getAppId())
                .mchId(v3.getMchId());
    }

}
