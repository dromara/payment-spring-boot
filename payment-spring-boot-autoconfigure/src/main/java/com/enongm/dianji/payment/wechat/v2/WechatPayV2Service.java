package com.enongm.dianji.payment.wechat.v2;


import com.enongm.dianji.payment.wechat.WechatPayProperties;
import com.enongm.dianji.payment.wechat.v2.model.BaseModel;

/**
 * The type Wechat pay v 2 service.
 *
 * @author Dax
 * @since 15 :15
 */
public class WechatPayV2Service {

    private final WechatPayProperties wechatPayProperties;

    /**
     * Instantiates a new Wechat pay v 2 service.
     *
     * @param wechatPayProperties the wechat pay properties
     */
    public WechatPayV2Service(WechatPayProperties wechatPayProperties) {
        this.wechatPayProperties = wechatPayProperties;
    }


    /**
     * Model base model.
     *
     * @param <M>   the type parameter
     * @param model the model
     * @return the base model
     */
    public <M extends BaseModel> BaseModel model(M model) {
        WechatPayProperties.V3 v3 = wechatPayProperties.getV3();
        return model.appId(v3.getAppId())
                .mchId(v3.getMchId())
                .appSecret(v3.getAppSecret());
    }

}
