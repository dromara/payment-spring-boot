package cn.felord.payment.wechat.v3;

import cn.felord.payment.PayException;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.PayParams;
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

    public WechatPayApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * APP下单API
     *
     * @param payParams the pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> app(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.APP, payParams)
                .function(this::appPayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * JSAPI/小程序下单API
     *
     * @param payParams the pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> js(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.JSAPI, payParams)
                .function(this::appPayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> appPayFunction(WechatPayV3Type type, PayParams payParams) {
        WechatPayProperties.V3 v3 = this.container().getWechatMeta(tenantId()).getV3();
        payParams.setAppid(v3.getAppId());
        payParams.setMchid(v3.getMchId());
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParam("pay_tenantId", this.tenantId())
                .build()
                .toUri();
        return post(uri, payParams);
    }
}
