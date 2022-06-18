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
package cn.felord.payment.wechat.enumeration;

import org.springframework.http.HttpMethod;

/**
 * 微信支付类型.
 *
 * @author felord.cn
 * @see cn.felord.payment.wechat.v3.WechatPayClient
 * @since 1.0.0.RELEASE
 */
public enum WechatPayV3Type {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * 获取证书.
     *
     * @since 1.0.0.RELEASE
     */
    CERT(HttpMethod.GET, "%s/v3/certificates"),

    /**
     * 文件下载
     *
     * @since 1.0.0.RELEASE
     */
    FILE_DOWNLOAD(HttpMethod.GET, "%s/v3/billdownload/file"),
    /**
     * 申请交易账单API.
     *
     * @since 1.0.3.RELEASE
     */
    TRADE_BILL(HttpMethod.GET, "%s/v3/bill/tradebill"),
    /**
     * 申请资金账单API.
     *
     * @since 1.0.3.RELEASE
     */
    FUND_FLOW_BILL(HttpMethod.GET, "%s/v3/bill/fundflowbill"),
    /**
     * 图片上传API.
     *
     * @since 1.0.14.RELEASE
     */
    MERCHANT_MEDIA_IMG(HttpMethod.POST, "%s/v3/merchant/media/upload"),
    /**
     * 视频上传API.
     *
     * @since 1.0.14.RELEASE
     */
    MERCHANT_MEDIA_VIDEO(HttpMethod.POST, "%s/v3/merchant/media/video_upload"),
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 微信公众号支付或者小程序支付.
     *
     * @since 1.0.0.RELEASE
     */
    JSAPI(HttpMethod.POST, "%s/v3/pay/transactions/jsapi"),

    /**
     * 微信扫码支付.
     *
     * @since 1.0.0.RELEASE
     */
    NATIVE(HttpMethod.POST, "%s/v3/pay/transactions/native"),

    /**
     * 微信APP支付.
     *
     * @since 1.0.0.RELEASE
     */
    APP(HttpMethod.POST, "%s/v3/pay/transactions/app"),

    /**
     * H5支付.
     *
     * @since 1.0.0.RELEASE
     */
    MWEB(HttpMethod.POST, "%s/v3/pay/transactions/h5"),
    /**
     * 关闭订单.
     *
     * @since 1.0.0.RELEASE
     */
    CLOSE(HttpMethod.POST, "%s/v3/pay/transactions/out-trade-no/{out_trade_no}/close"),
    /**
     * 微信支付订单号查询API.
     *
     * @since 1.0.0.RELEASE
     */
    TRANSACTION_TRANSACTION_ID(HttpMethod.GET, "%s/v3/pay/transactions/id/{transaction_id}"),
    /**
     * 商户订单号查询API.
     *
     * @since 1.0.0.RELEASE
     */
    TRANSACTION_OUT_TRADE_NO(HttpMethod.GET, "%s/v3/pay/transactions/out-trade-no/{out_trade_no}"),
    /**
     * 申请退款API.
     *
     * @since 1.0.6.RELEASE
     */
    REFUND(HttpMethod.POST, "%s/v3/refund/domestic/refunds"),
    /**
     * 查询退款API.
     *
     * @since 1.0.6.RELEASE
     */
    QUERY_REFUND(HttpMethod.GET, "%s/v3/refund/domestic/refunds/{out_refund_no}"),

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 合单下单-APP支付API.
     *
     * @since 1.0.0.RELEASE
     */
    COMBINE_APP(HttpMethod.POST, "%s/v3/combine-transactions/app"),

    /**
     * 合单下单-微信公众号支付或者小程序支付.
     *
     * @since 1.0.0.RELEASE
     */
    COMBINE_JSAPI(HttpMethod.POST, "%s/v3/combine-transactions/jsapi"),
    /**
     * 合单下单-H5支付API.
     *
     * @since 1.0.0.RELEASE
     */
    COMBINE_MWEB(HttpMethod.POST, "%s/v3/combine-transactions/h5"),
    /**
     * 合单下单-Native支付API.
     *
     * @since 1.0.0.RELEASE
     */
    COMBINE_NATIVE(HttpMethod.POST, "%s/v3/combine-transactions/native"),
    /**
     * 合单查询订单API.
     *
     * @since 1.0.0.RELEASE
     */
    COMBINE_TRANSACTION_OUT_TRADE_NO(HttpMethod.GET, "%s/v3/combine-transactions/out-trade-no/{combine_out_trade_no}"),

    /**
     * 合单关闭订单API.
     *
     * @since 1.0.0.RELEASE
     */
    COMBINE_CLOSE(HttpMethod.POST, "%s/v3/combine-transactions/out-trade-no/{combine_out_trade_no}/close"),

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 商户预授权API.
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_PERMISSIONS(HttpMethod.POST, "%s/v3/payscore/permissions"),
    /**
     * 创单结单合并API.
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_DIRECT_COMPLETE(HttpMethod.POST, "%s/payscore/serviceorder/direct-complete"),
    /**
     * 查询与用户授权记录（授权协议号）API.
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_PERMISSIONS_AUTH_CODE(HttpMethod.GET, "%s/v3/payscore/permissions/authorization-code/{authorization_code}"),
    /**
     * 解除用户授权关系（授权协议号）API.
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_TERMINATE_PERMISSIONS_AUTH_CODE(HttpMethod.POST, "%s/v3/payscore/permissions/authorization-code/{authorization_code}/terminate"),
    /**
     * 查询与用户授权记录（openid）API.
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_PERMISSIONS_OPENID(HttpMethod.GET, "%s/v3/payscore/permissions/openid/{openid}"),
    /**
     * 解除用户授权关系（openid）API.
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_TERMINATE_PERMISSIONS_OPENID(HttpMethod.POST, "%s/v3/payscore/permissions/openid/{openid}/terminate"),
    /**
     * 查询用户授权状态API（旧版接口）.
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_USER_SERVICE_STATE(HttpMethod.GET, "%s/v3/payscore/user-service-state?service_id={service_id}&appid={appid}&openid={openid}"),
    /**
     * 创建支付分订单API
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_CREATE_USER_SERVICE_ORDER(HttpMethod.POST, "%s/v3/payscore/serviceorder"),
    /**
     * 查询支付分订单API
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_QUERY_USER_SERVICE_ORDER(HttpMethod.GET, "%s/v3/payscore/serviceorder"),
    /**
     * 取消支付分订单API
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_CANCEL_USER_SERVICE_ORDER(HttpMethod.POST, "%s/v3/payscore/serviceorder/{out_order_no}/cancel"),
    /**
     * 修改订单金额API
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_MODIFY_USER_SERVICE_ORDER(HttpMethod.POST, "%s/v3/payscore/serviceorder/{out_order_no}/modify"),
    /**
     * 完结支付分订单API
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_COMPLETE_USER_SERVICE_ORDER(HttpMethod.POST, "%s/v3/payscore/serviceorder/{out_order_no}/complete"),
    /**
     * 商户发起催收扣款API
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_PAY_USER_SERVICE_ORDER(HttpMethod.POST, "%s/v3/payscore/serviceorder/{out_order_no}/pay"),
    /**
     * 同步服务订单信息API
     *
     * @since 1.0.2.RELEASE
     */
    PAY_SCORE_SYNC_USER_SERVICE_ORDER(HttpMethod.POST, "%s/v3/payscore/serviceorder/{out_order_no}/sync"),
    /**
     * 商户申请获取对账单API
     *
     * @since 1.0.13.RELEASE
     */
    PAY_SCORE_MERCHANT_BILL(HttpMethod.GET, "%s/v3/payscore/merchant-bill"),
    /**
     * 查询车牌服务开通信息API
     *
     * @since 1.0.13.RELEASE
     */
    PAY_SCORE_PARKING_FIND(HttpMethod.GET, "%s/v3/vehicle/parking/services/find"),
    /**
     * 创建停车入场API
     *
     * @since 1.0.13.RELEASE
     */
    PAY_SCORE_PARKING_PARKINGS(HttpMethod.POST, "%s/v3/vehicle/parking/parkings"),
    /**
     * 扣费受理API
     *
     * @since 1.0.13.RELEASE
     */
    PAY_SCORE_PARKING_TRANSACTIONS_PARKINGS(HttpMethod.POST, "%s/v3/vehicle/transactions/parking"),
    /**
     * 查询订单API
     *
     * @since 1.0.13.RELEASE
     */
    PAY_SCORE_PARKING_TRANSACTIONS_OUTTRADENO(HttpMethod.GET, "%s/v3/vehicle/transactions/out-trade-no/{out_trade_no}"),
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 微信先享卡预受理领卡请求API.
     *
     * @since 1.0.2.RELEASE
     */
    DISCOUNT_CARD_PRE_REQUEST(HttpMethod.POST, "%s/v3/discount-card/cards"),
    /**
     * 微信先享卡增加用户记录API.
     *
     * @since 1.0.2.RELEASE
     */
    DISCOUNT_CARD_ADD_USER_RECORDS(HttpMethod.POST, "%s/v3/discount-card/cards/{out_card_code}/add-user-records"),
    /**
     * 微信先享卡查询先享卡订单API.
     *
     * @since 1.0.2.RELEASE
     */
    DISCOUNT_CARD_INFO(HttpMethod.POST, "%s/v3/discount-card/cards/{out_card_code}"),

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 创建代金券批次API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_COUPON_STOCKS(HttpMethod.POST, "%s/v3/marketing/favor/coupon-stocks"),
    /**
     * 激活代金券批次API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_START(HttpMethod.POST, "%s/v3/marketing/favor/stocks/{stock_id}/start"),
    /**
     * 暂停代金券批次API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_PAUSE(HttpMethod.POST, "%s/v3/marketing/favor/stocks/{stock_id}/pause"),

    /**
     * 发放代金券API、根据商户号查用户的券.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_USERS_COUPONS(HttpMethod.POST, "%s/v3/marketing/favor/users/{openid}/coupons"),
    /**
     * 重启代金券API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_RESTART(HttpMethod.POST, "%s/v3/marketing/favor/stocks/{stock_id}/restart"),
    /**
     * 条件查询批次列表API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS(HttpMethod.GET, "%s/v3/marketing/favor/stocks"),
    /**
     * 查询批次详情API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_DETAIL(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}"),
    /**
     * 查询代金券详情API
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_USERS_COUPONS_DETAIL(HttpMethod.GET, "%s/v3/marketing/favor/users/{openid}/coupons/{coupon_id}"),
    /**
     * 查询代金券可用商户API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_MERCHANTS(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/merchants"),
    /**
     * 查询代金券可用单品API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_ITEMS(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/items"),
    /**
     * 下载批次核销明细API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_USE_FLOW(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/use-flow"),
    /**
     * 下载批次退款明细API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_STOCKS_REFUND_FLOW(HttpMethod.GET, "%s/v3/marketing/favor/stocks/{stock_id}/refund-flow"),
    /**
     * 营销图片上传API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_IMAGE_UPLOAD(HttpMethod.POST, "%s/v3/marketing/favor/media/image-upload"),
    /**
     * 设置核销回调通知API.
     *
     * @since 1.0.0.RELEASE
     */
    MARKETING_FAVOR_CALLBACKS(HttpMethod.POST, "%s/v3/marketing/favor/callbacks"),
    /**
     * 发放代金券消费卡API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_FAVOR_COUPONS_SEND(HttpMethod.POST, "%s/v3/marketing/busifavor/coupons/{card_id}/send"),
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 创建商家券API.
     *
     * @since 1.0.4.RELEASE
     */
    MARKETING_BUSI_FAVOR_CREATE_STOCKS(HttpMethod.POST, "%s/v3/marketing/busifavor/stocks"),
    /**
     * 查询商家券详情API.
     *
     * @since 1.0.4.RELEASE
     */
    MARKETING_BUSI_FAVOR_STOCKS_DETAIL(HttpMethod.GET, "%s/v3/marketing/busifavor/stocks/{stock_id}"),
    /**
     * 核销用户券API.
     *
     * @since 1.0.4.RELEASE
     */
    MARKETING_BUSI_FAVOR_COUPON_USE(HttpMethod.POST, "%s/v3/marketing/busifavor/coupons/use"),
    /**
     * 根据过滤条件查询用户券API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_USER_COUPONS(HttpMethod.GET, "%s/v3/marketing/busifavor/users/{openid}/coupons"),
    /**
     * 查询用户单张券详情API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_USER_COUPON(HttpMethod.GET, "%s/v3/marketing/busifavor/users/{openid}/coupons/{coupon_code}/appids/{appid}"),
    /**
     * 商家券上传预存Code API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_UPLOAD_COUPON_CODES(HttpMethod.POST, "%s/v3/marketing/busifavor/stocks/{stock_id}/couponcodes"),
    /**
     * 设置商家券事件通知地址API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_SETTING_CALLBACKS(HttpMethod.POST, "%s/v3/marketing/busifavor/callbacks"),
    /**
     * 查询商家券事件通知地址API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_GET_CALLBACKS(HttpMethod.GET, "%s/v3/marketing/busifavor/callbacks"),
    /**
     * 关联订单信息API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_ASSOCIATE(HttpMethod.POST, "%s/v3/marketing/busifavor/coupons/associate"),
    /**
     * 取消关联订单信息API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_DISASSOCIATE(HttpMethod.POST, "%s/v3/marketing/busifavor/coupons/disassociate"),
    /**
     * 取消关联订单信息API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_BUDGET(HttpMethod.POST, "%s/v3/marketing/busifavor/stocks/{stock_id}/budget"),
    /**
     * 修改商家券基本信息API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_UPDATE(HttpMethod.PATCH, "%s/v3/marketing/busifavor/stocks/{stock_id}"),
    /**
     * 申请退券API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_RETURN(HttpMethod.POST, "%s/v3/marketing/busifavor/coupons/return"),
    /**
     * 使券失效API.
     *
     * @since 1.0.4.RELEASES
     */
    MARKETING_BUSI_FAVOR_DEACTIVATE(HttpMethod.POST, "%s/v3/marketing/busifavor/coupons/deactivate"),
    /**
     * 营销补差付款API.
     *
     * @since 1.0.13.RELEASES
     */
    MARKETING_BUSI_FAVOR_SUBSIDY(HttpMethod.POST, "%s/v3/marketing/busifavor/subsidy/pay-receipts"),
    /**
     * 查询营销补差付款单详情API.
     *
     * @since 1.0.13.RELEASES
     */
    MARKETING_BUSI_FAVOR_SUBSIDY_QUERY(HttpMethod.GET, "%s/v3/marketing/busifavor/subsidy/pay-receipts/{subsidy_receipt_id}"),
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * 发起批量转账API.
     *
     * @since 1.0.6.RELEASES
     */
    BATCH_TRANSFER_REQ(HttpMethod.POST, "%s/v3/transfer/batches"),
    /**
     * 微信批次单号查询批次单API.
     *
     * @since 1.0.6.RELEASES
     */
    BATCH_TRANSFER_BATCH_ID(HttpMethod.GET, "%s/v3/transfer/batches/batch-id/{batch_id}"),
    /**
     * 微信明细单号查询明细单API.
     *
     * @since 1.0.6.RELEASES
     */
    BATCH_TRANSFER_DETAIL_WECHAT(HttpMethod.GET, "%s/v3/transfer/batches/batch-id/{batch_id}/details/detail-id/{detail_id}"),
    /**
     * 商家批次单号查询批次单API.
     *
     * @since 1.0.6.RELEASES
     */
    BATCH_TRANSFER_OUT_BATCH_NO(HttpMethod.GET, "%s/v3/transfer/batches/out-batch-no/{out_batch_no}"),
    /**
     * 商家明细单号查询明细单API.
     *
     * @since 1.0.6.RELEASES
     */
    BATCH_TRANSFER_DETAIL_MCH(HttpMethod.GET, "%s/v3/transfer/batches/out-batch-no/{out_batch_no}/details/out-detail-no/{out_detail_no}"),
    /**
     * 转账电子回单申请受理API.
     *
     * @since 1.0.6.RELEASES
     */
    BATCH_TRANSFER_BILL_RECEIPT(HttpMethod.POST, "%s/v3/transfer/bill-receipt"),
    /**
     * 查询转账电子回单并下载.
     *
     * @since 1.0.6.RELEASES
     */
    BATCH_TRANSFER_DOWNLOAD_BILL(HttpMethod.GET, "%s/v3/transfer/bill-receipt/{out_batch_no}"),
    /**
     * 转账明细电子回单受理API.
     *
     * @since 1.0.11.RELEASES
     */
    BATCH_TRANSFER_ELECTRONIC(HttpMethod.POST, "%s/v3/transfer-detail/electronic-receipts"),
    /**
     * 查询转账明细电子回单受理结果API.
     * 请求方式同{@link WechatPayV3Type#BATCH_TRANSFER_ELECTRONIC}不同
     *
     * @since 1.0.11.RELEASES
     */
    BATCH_TRANSFER_ELECTRONIC_DETAIL(HttpMethod.GET, "%s/v3/transfer-detail/electronic-receipts"),
    /**
     * 查询账户实时余额API
     *
     * @since 1.0.11.RELEASES
     */
    BATCH_TRANSFER_FUND_BALANCE(HttpMethod.GET, "%s/v3/merchant/fund/balance/{account_type}"),
    /**
     * 查询账户日终余额API
     *
     * @since 1.0.11.RELEASES
     */
    BATCH_TRANSFER_FUND_DAY_BALANCE(HttpMethod.GET, "%s/v3/merchant/fund/dayendbalance/{account_type}"),
    /**
     * 商户银行来账查询API
     *
     * @since 1.0.11.RELEASES
     */
    BATCH_TRANSFER_FUND_INCOME_RECORDS(HttpMethod.GET, "%s/v3/merchantfund/merchant/income-records"),

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 服务商APP下单API.
     *
     * @since 1.0.8.RELEASES
     */
    APP_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/app"),

    /**
     * 微信公众号支付或者小程序支付.
     *
     * @since 1.0.8.RELEASE
     */
    JSAPI_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/jsapi"),

    /**
     * 微信扫码支付.
     *
     * @since 1.0.8.RELEASE
     */
    NATIVE_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/native"),

    /**
     * H5支付.
     *
     * @since 1.0.8.RELEASE
     */
    MWEB_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/h5"),

    /**
     * 关闭订单.
     *
     * @since 1.0.0.RELEASE
     */
    CLOSE_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/out-trade-no/{out_trade_no}/close"),
    /**
     * 微信支付订单号查询API.
     *
     * @since 1.0.0.RELEASE
     */
    TRANSACTION_TRANSACTION_ID_PARTNER(HttpMethod.GET, "%s/v3/pay/partner/transactions/id/{transaction_id}"),
    /**
     * 商户订单号查询API.
     *
     * @since 1.0.0.RELEASE
     */
    TRANSACTION_OUT_TRADE_NO_PARTNER(HttpMethod.GET, "%s/v3/pay/partner/transactions/out-trade-no/{out_trade_no}"),

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * 请求分账API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_ORDERS(HttpMethod.POST, "%s/v3/profitsharing/orders"),
    /**
     * 查询分账结果API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_ORDERS_RESULT(HttpMethod.GET, "%s/v3/profitsharing/orders/{out_order_no}"),
    /**
     * 请求分账回退API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_RETURN_ORDERS(HttpMethod.POST, "%s/v3/profitsharing/return-orders"),
    /**
     * 查询分账回退结果API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_RETURN_ORDERS_RESULT(HttpMethod.GET, "%s/v3/profitsharing/return-orders"),
    /**
     * 解冻剩余资金API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_ORDERS_UNFREEZE(HttpMethod.POST, "%s/v3/profitsharing/orders/unfreeze"),
    /**
     * 查询剩余待分金额API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_AMOUNTS(HttpMethod.GET, "%s/v3/profitsharing/transactions/{transaction_id}/amounts"),
    /**
     * 服务商专用-查询最大分账比例API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_MCH_CONFIG(HttpMethod.GET, "%s/v3/profitsharing/merchant-configs/{sub_mchid}"),
    /**
     * 添加分账接收方API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_RECEIVERS_ADD(HttpMethod.POST, "%s/v3/profitsharing/receivers/add"),
    /**
     * 删除分账接收方API.
     *
     * @since 1.0.11.RELEASE
     */
    PROFITSHARING_RECEIVERS_DELETE(HttpMethod.POST, "%s/v3/profitsharing/receivers/delete"),
    /**
     * 申请分账账单API.
     *
     * @since 1.0.13.RELEASE
     */
    PROFITSHARING_BILLS(HttpMethod.GET, "%s/v3/profitsharing/bills"),
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * 服务商-商户进件-特约商户进件-提交申请单API.
     *
     * @since 1.0.14.RELEASE
     */
    SPEC_MCH_APPLY_PARTNER(HttpMethod.POST, "%s/v3/applyment4sub/applyment/"),
    /**
     * 服务商-商户进件-特约商户进件-通过业务申请编号查询申请状态API.
     *
     * @since 1.0.14.RELEASE
     */
    SPEC_MCH_APPLY_QUERY_BUSINESS_CODE(HttpMethod.GET, "%s/v3/applyment4sub/applyment/business_code/{business_code}"),
    /**
     * 服务商-商户进件-特约商户进件-通过申请单号查询申请状态API.
     *
     * @since 1.0.14.RELEASE
     */
    SPEC_MCH_APPLY_QUERY_APPLYMENT_ID(HttpMethod.GET, "%s/v3/applyment4sub/applyment/applyment_id/{applyment_id}"),
    /**
     * 服务商-商户进件-特约商户进件-修改结算账号API.
     *
     * @since 1.0.14.RELEASE
     */
    SPEC_MCH_SUB_MODIFY(HttpMethod.POST, "%s/v3/apply4sub/sub_merchants/{sub_mchid}/modify-settlement"),
    /**
     * 服务商-商户进件-特约商户进件-查询结算账户API.
     *
     * @since 1.0.14.RELEASE
     */
    SPEC_MCH_SUB_SETTLEMENT(HttpMethod.GET, "%s/v3/apply4sub/sub_merchants/{sub_mchid}/settlement");


    /**
     * The Pattern.
     *
     * @since 1.0.0.RELEASE
     */
    private final String pattern;
    /**
     * The Method.
     *
     * @since 1.0.0.RELEASE
     */
    private final HttpMethod method;

    /**
     * Instantiates a new Wechat pay v 3 type.
     *
     * @param method  the method
     * @param pattern the pattern
     * @since 1.0.0.RELEASE
     */
    WechatPayV3Type(HttpMethod method, String pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    /**
     * Method string.
     *
     * @return the string
     * @since 1.0.0.RELEASE
     */
    public HttpMethod method() {
        return this.method;
    }

    /**
     * Pattern string.
     *
     * @return the string
     * @since 1.0.0.RELEASE
     */
    public String pattern() {
        return this.pattern;
    }

    /**
     * 默认支付URI.
     *
     * @param weChatServer the we chat server
     * @return the string
     * @since 1.0.0.RELEASE
     */
    public String uri(WeChatServer weChatServer) {
        return String.format(this.pattern, weChatServer.domain());
    }
}
