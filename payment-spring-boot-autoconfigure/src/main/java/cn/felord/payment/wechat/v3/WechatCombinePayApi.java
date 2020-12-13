package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.combine.CombinePayParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 微信合单支付.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatCombinePayApi extends AbstractApi{
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatCombinePayApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 合单下单-APP支付API
     * <p>
     * 使用合单支付接口，用户只输入一次密码，即可完成多个订单的支付。目前最多一次可支持50笔订单进行合单支付。
     * <p>
     * 注意：
     * • 订单如果需要进行抽佣等，需要在合单中指定需要进行分账（profit_sharing为true）；指定后，交易资金进入二级商户账户，处于冻结状态，可在后续使用分账接口进行分账，利用分账完结进行资金解冻，实现抽佣和对二级商户的账期。
     *
     * @param combinePayParams the combine pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> appPay(CombinePayParams combinePayParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.APP, combinePayParams)
                .function(this::combinePayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * Combine pay function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> combinePayFunction(WechatPayV3Type type, CombinePayParams params) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
        params.setCombineAppid(v3.getAppId());
        params.setCombineMchid(v3.getMchId());

        String notifyUrl = v3.getDomain().concat(params.getNotifyUrl());
        params.setNotifyUrl(notifyUrl);
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return Post(uri, params);
    }

}
