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
 *
 */
package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.CouponStatus;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.busifavor.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * 微信支付商家券.
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
public class WechatMarketingBusiFavorApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatMarketingBusiFavorApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 创建商家券券批次API
     * <p>
     * 商家券介绍详见 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/marketing/busifavor/chapter1_1.shtml">微信支付商家券</a>
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> createStock(BusiFavorCreateParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_CREATE_STOCKS, params)
                .function(this::createStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> createStocksFunction(WechatPayV3Type type, BusiFavorCreateParams busiFavorCreateParams) {
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        if (!StringUtils.hasText(busiFavorCreateParams.getBelongMerchant())) {
            WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
            String mchId = v3.getMchId();
            busiFavorCreateParams.setBelongMerchant(mchId);
        }
        return Post(uri, busiFavorCreateParams);
    }

    /**
     * 查询商家券详情API
     * <p>
     * 商户可通过该接口查询已创建的商家券批次详情信息。
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryStockDetail(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_STOCKS_DETAIL, stockId)
                .function((type, id) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(id)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 核销用户券API，暂时appid需要手工在参数中传递
     * <p>
     * 在用户满足优惠门槛后，服务商可通过该接口核销用户微信卡包中具体某一张商家券。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> use(BusiFavorUseParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_COUPON_USE, params)
                .function((type, useParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    if (!StringUtils.hasText(params.getAppid())) {
                        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                        String appId = v3.getAppId();
                        useParams.setAppid(appId);
                    }
                    return Post(uri, useParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 根据过滤条件查询用户券API
     * <p>
     * 商户自定义筛选条件（如创建商户号、归属商户号、发放商户号等），查询指定微信用户卡包中满足对应条件的所有商家券信息。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryUserStocks(UserBusiFavorQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_USER_COUPONS, params)
                .function((type, userBusiFavorQueryParams) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

                    String appid = userBusiFavorQueryParams.getAppid();
                    if (StringUtils.hasText(appid)) {
                        queryParams.add("appid", appid);
                    }
                    queryParams.add("stock_id", userBusiFavorQueryParams.getStockId());

                    Optional.ofNullable(userBusiFavorQueryParams.getCouponState())
                            .ifPresent(state -> queryParams.add("coupon_state", state.name()));

                    queryParams.add("creator_merchant", userBusiFavorQueryParams.getCreatorMerchant());
                    queryParams.add("belong_merchant", userBusiFavorQueryParams.getBelongMerchant());
                    queryParams.add("sender_merchant", userBusiFavorQueryParams.getSenderMerchant());
                    queryParams.add("offset", String.valueOf(params.getOffset()));
                    queryParams.add("limit", String.valueOf(params.getLimit()));

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .expand(userBusiFavorQueryParams.getOpenid())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询用户单张券详情API
     * <p>
     * 服务商可通过该接口查询微信用户卡包中某一张商家券的详情信息。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryUserCoupon(UserBusiCouponQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_USER_COUPON, params)
                .function((type, queryParams) -> {
                    if (!StringUtils.hasText(params.getAppid())) {
                        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                        String appId = v3.getAppId();
                        queryParams.setAppid(appId);
                    }

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(queryParams.getOpenid(), queryParams.getCouponCode(), queryParams.getAppid())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 上传预存code API
     * <p>
     * 商家券的Code码可由微信后台随机分配，同时支持商户自定义。
     * 如商家已有自己的优惠券系统，可直接使用自定义模式。
     * 即商家预先向微信支付上传券Code，当券在发放时，微信支付自动从已导入的Code中随机取值（不能指定），派发给用户。
     *
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> uploadCouponCodes(BusiCouponCodeUploadParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_UPLOAD_COUPON_CODES, params)
                .function((type, queryParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(queryParams.getStockId())
                            .toUri();
                    queryParams.setStockId(null);
                    return Post(uri, queryParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 设置商家券事件通知地址API
     * <p>
     * 用于设置接收商家券相关事件通知的URL，可接收商家券相关的事件通知、包括发放通知等。
     * 需要设置接收通知的URL，并在商户平台开通 <a target= "_blank" href= "https://pay.weixin.qq.com/index.php/xphp/cmkt_product/index#/pages/product/product?pid=3">营销事件推送</a> 的能力，即可接收到相关通知。
     * <p>
     * 营销事件推送：<a target= "_blank" href= "https://pay.weixin.qq.com/index.php/core/home/login?return_url=%2Findex.php%2Fxphp%2Fcmkt_product%2Findex#/pages/product/product?pid=3">点击开通产品权限</a>。由商家券批次创建方登录Pay平台，操作开通
     * <p>
     * 注意：
     * <ul>
     *     <li>仅可以收到由商户自己创建的批次相关的通知</li>
     *     <li>需要设置apiv3秘钥，否则无法收到回调。</li>
     *     <li>如果需要领券回调中的参数openid。需要创券时候传入 notify_appid参数。</li>
     * </ul>
     *
     * @param params the params
     * @return callbacks callbacks
     */
    public WechatResponseEntity<ObjectNode> setCallbacks(BusiFavorCallbackSettingParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_SETTING_CALLBACKS, params)
                .function((type, queryParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, queryParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询商家券事件通知地址API
     * <p>
     * 通过调用此接口可查询设置的通知URL。
     * <p>
     * 注意：
     * <ul>
     *     <li>仅可以查询由请求商户号设置的商家券通知url</li>
     * </ul>
     *
     * @param mchId the mch id
     * @return callbacks callbacks
     */
    public WechatResponseEntity<ObjectNode> getCallbacks(String mchId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_GET_CALLBACKS, mchId)
                .function((type, id) -> {
                    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA));
                    if (StringUtils.hasText(id)) {
                        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                        queryParams.add("mchid", id);
                        uriComponentsBuilder.queryParams(queryParams);
                    }
                    URI uri = uriComponentsBuilder
                            .build()
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商家券关联订单信息API
     * <p>
     * 将有效态（未核销）的商家券与订单信息关联，用于后续参与摇奖以及返佣激励等操作的统计。
     * <p>
     * 注意：
     * 仅对有关联订单需求的券进行该操作
     *
     * @param associateInfo the associate info
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> associate(BusiFavorAssociateInfo associateInfo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_ASSOCIATE, associateInfo)
                .function(this::associateFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商家券取消关联订单信息API
     * <p>
     * 取消商家券与订单信息的关联关系。
     * <p>
     * 注意：
     * 建议取消前调用查询接口，查到当前关联的商户单号并确认后，再进行取消操作
     *
     * @param associateInfo the associate info
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> disassociate(BusiFavorAssociateInfo associateInfo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_DISASSOCIATE, associateInfo)
                .function(this::associateFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * associate and disassociate function
     *
     * @param type          WechatPayV3Type
     * @param associateInfo the associateInfo
     * @return RequestEntity
     */
    private RequestEntity<?> associateFunction(WechatPayV3Type type, BusiFavorAssociateInfo associateInfo) {
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return Post(uri, associateInfo);
    }

    /**
     * 修改批次预算API
     * <p>
     * 商户可以通过该接口修改批次单天发放上限数量或者批次最大发放数量
     *
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> budget(BusiFavorBudgetParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_BUDGET, params)
                .function((type, budgetParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(budgetParams.getStockId())
                            .toUri();
                    budgetParams.setStockId(null);
                    return Post(uri, budgetParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 修改商家券基本信息API
     * <p>
     * 商户可以通过该接口修改商家券基本信息
     *
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> updateStock(BusiFavorUpdateParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_UPDATE, params)
                .function((type, updateParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(updateParams.getStockId())
                            .toUri();
                    updateParams.setStockId(null);
                    return Patch(uri, updateParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 申请退券API
     * <p>
     * 商户可以通过该接口为已核销的券申请退券
     *
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> refund(BusiFavorRefundParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_RETURN, params)
                .function((type, refundParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, refundParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 使券失效API
     * <p>
     * 前置条件：券的状态为{@link CouponStatus#SENDED}
     * <p>
     * 商户可以通过该接口将可用券进行失效处理，券被失效后无法再被核销
     *
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> deactivate(BusiFavorDeactivateParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_DEACTIVATE, params)
                .function((type, deactivateParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, deactivateParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 营销补差付款API
     * <p>
     * 该API主要用于商户营销补贴场景，支持基于单张券进行不同商户账户间的资金补差，从而提升财务结算、资金利用效率。<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/download/%E5%95%86%E5%AE%B6%E5%88%B8%E8%A1%A5%E5%B7%AE%E4%BA%A7%E5%93%81%E6%93%8D%E4%BD%9C%E6%96%87%E6%A1%A3.pdf">具体可参考操作文档</a>
     *
     * @param params the params
     * @return the wechat response entity
     * @since 1.0.13.RELEASE
     */
    public WechatResponseEntity<ObjectNode> payMakeup(BusiFavorSubsidyParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_BUSI_FAVOR_SUBSIDY, params)
                .function((type, makeUpParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, makeUpParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

}
