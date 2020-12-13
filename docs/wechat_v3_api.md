## 入口类

`WechatApiProvider`是本项目微信支付的入口类，已被注入**Spring IoC**。它目前包含以下几个**API**（后续会增加）。

### 代金券API

`WechatMarketingFavorApi`是微信支付营销工具-[代金券相关API](https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pages/convention.shtml)的封装。

#### 创建代金券批次API

`WechatResponseEntity<ObjectNode> createStock(StocksCreateParams params)`

#### 激活代金券批次API

 `WechatResponseEntity<ObjectNode> startStock(String stockId)`

#### 发放代金券API

`WechatResponseEntity<ObjectNode> sendStock(StocksSendParams params)`

#### 暂停代金券批次API

`WechatResponseEntity<ObjectNode> pauseStock(String stockId)`

#### 重启代金券批次API

`WechatResponseEntity<ObjectNode> restartStock(String stockId)`

#### 条件查询批次列表API
通过此接口可查询多个批次的信息，包括批次的配置信息以及批次概况数据。

`WechatResponseEntity<ObjectNode> queryStocksByMch(StocksQueryParams params)`

#### 查询批次详情API

`WechatResponseEntity<ObjectNode> queryStockDetail(String stockId)`

#### 查询代金券详情API

`WechatResponseEntity<ObjectNode> queryCouponDetails(CouponDetailsQueryParams params)`

#### 查询代金券可用商户API

`WechatResponseEntity<ObjectNode> queryMerchantsByStockId(StocksQueryParams params)`

#### 查询代金券可用单品API

`WechatResponseEntity<ObjectNode> queryStockItems(StocksQueryParams params)`

#### 根据商户号查用户的券API

`WechatResponseEntity<ObjectNode> queryUserCouponsByMchId(UserCouponsQueryParams params)`

#### 下载批次核销明细API

`WechatResponseEntity<ObjectNode> downloadStockUseFlow(String stockId)`

#### 下载批次退款明细API

`WechatResponseEntity<ObjectNode> downloadStockRefundFlow(String stockId)`

#### 营销图片上传API

`WechatResponseEntity<ObjectNode> marketingImageUpload(MultipartFile file)`

#### 代金券核销回调通知API

`WechatResponseEntity<ObjectNode> setMarketingFavorCallback(String notifyUrl)`

### 普通支付-直连模式API

`WechatDirectPayApi`是微信基础支付工具-[普通支付-直连模式API](https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pages/transactions.shtml)的封装。

#### APP下单API

`WechatResponseEntity<ObjectNode> appPay(PayParams payParams)`

#### JSAPI/小程序下单API

`WechatResponseEntity<ObjectNode> jsPay(PayParams payParams)`

#### Native下单API

`WechatResponseEntity<ObjectNode> nativePay(PayParams payParams)`

#### H5下单API

`WechatResponseEntity<ObjectNode> h5Pay(PayParams payParams)`

#### 微信支付订单号查询API

`WechatResponseEntity<ObjectNode> queryTransactionById(TransactionQueryParams params)`

#### 商户订单号查询API

`WechatResponseEntity<ObjectNode> queryTransactionByOutTradeNo(TransactionQueryParams params)`

#### 关单API

`WechatResponseEntity<ObjectNode> close(String outTradeNo)`

### 回调API

所有需要回调处理的微信支付业务通过`WechatPayCallback`来进行处理。

#### 微信支付代金券核销回调API

`Map<String, ?> couponCallback(ResponseSignVerifyParams params, Consumer<CouponConsumeData> couponConsumeDataConsumer)`

#### 微信支付普通支付回调API

`Map<String, ?> transactionCallback(ResponseSignVerifyParams params, Consumer<TransactionConsumeData> couponConsumeDataConsumer)`