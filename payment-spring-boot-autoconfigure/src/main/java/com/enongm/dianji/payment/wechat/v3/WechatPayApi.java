package com.enongm.dianji.payment.wechat.v3;

import com.enongm.dianji.payment.wechat.WechatPayProperties;
import com.enongm.dianji.payment.wechat.enumeration.WeChatServer;
import com.enongm.dianji.payment.wechat.enumeration.WechatPayV3Type;
import com.enongm.dianji.payment.wechat.v3.model.AppPayParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * The type Wechat pay api.
 *
 * @author Dax
 * @since 16 :15
 */
public class WechatPayApi extends AbstractApi {


    public WechatPayApi(WechatPayClient wechatPayClient, WechatMetaBean wechatMetaBean) {
        super(wechatPayClient, wechatMetaBean);
    }

    /**
     * APP下单API.
     *
     * @param payParams the pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> appPay(AppPayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.getWechatPayClient().withType(WechatPayV3Type.APP, payParams)
                .function(this::appPayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> appPayFunction(WechatPayV3Type type, AppPayParams payParams) {
        WechatPayProperties.V3 v3 = this.getWechatMetaBean().getWechatPayProperties().getV3();
        payParams.setAppid(v3.getAppId());
        payParams.setMchid(v3.getMchId());
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().toUri();
        return postRequestEntity(uri, payParams);
    }
}
