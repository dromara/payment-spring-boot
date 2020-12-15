package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.payscore.CancelServiceOrderParams;
import cn.felord.payment.wechat.v3.model.payscore.QueryServiceOrderParams;
import cn.felord.payment.wechat.v3.model.payscore.UserPayScoreOrderParams;
import cn.felord.payment.wechat.v3.model.payscore.UserServiceStateParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 微信支付分API.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
public class WechatPayScoreApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatPayScoreApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 微信支付分-查询用户授权状态API.
     * <p>
     * 免确认订单起始接口，【免确认订单模式】是高级接口权限，参见：<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/payscore/chapter2_5.shtml">业务流程说明</a>
     * <p>
     * 用户申请使用服务时，商户可通过此接口查询用户是否“已授权”本服务。在“已授权”状态下的服务，用户才可以申请使用。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> userServiceState(UserServiceStateParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_USER_SERVICE_STATE, params)
                .function((wechatPayV3Type, userServiceStateParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

                    MultiValueMap<String, String> expandParams = new LinkedMultiValueMap<>();
                    expandParams.add("appid", v3.getAppId());
                    expandParams.add("service_id", params.getServiceId());
                    expandParams.add("openid", params.getOpenId());

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(expandParams)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 创建支付分订单API
     * <p>
     * 用户申请使用服务时，商户可通过此接口申请创建微信支付分订单。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> createServiceOrder(UserPayScoreOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_CREATE_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

                    orderParams.setAppid(v3.getAppId());
                    orderParams.setNotifyUrl(v3.getDomain().concat(orderParams.getNotifyUrl()));

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询支付分订单API
     * <p>
     * 用于查询单笔微信支付分订单详细信息。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryServiceOrder(QueryServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_QUERY_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

                    String outOrderNo = orderParams.getOutOrderNo();
                    if (StringUtils.hasText(outOrderNo)) {
                        queryParams.add("out_order_no", outOrderNo);
                    }

                    String queryId = orderParams.getQueryId();
                    if (StringUtils.hasText(queryId)) {
                        queryParams.add("query_id", queryId);
                    }
                    queryParams.add("service_id", orderParams.getServiceId());

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    queryParams.add("appid", v3.getAppId());

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 取消支付分订单API
     * <p>
     * 微信支付分订单创建之后，由于某些原因导致订单不能正常支付时，可使用此接口取消订单。
     * <p>
     * 订单为以下状态时可以取消订单：CREATED（已创单）、DOING（进行中）（包括商户完结支付分订单后，且支付分订单收款状态为待支付USER_PAYING)
     * <p>
     * 注意：
     * • DOING状态包含了订单用户确认、已完结-待支付（USER_PAYING）的状态，因此这种状态下订单也是可以被取消的，请确认当前操作是否正确，防止误操作将完结后需要支付分收款的单据取消。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> cancelServiceOrder(CancelServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_CANCEL_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getOutOrderNo())
                            .toUri();
                    orderParams.setOutOrderNo(null);

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());

                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
