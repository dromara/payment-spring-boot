## 入口类

`WechatApiProvider`是本项目微信支付的入口类，已被注入**Spring IoC**，由它来初始化微信支付相关的**API**，具体分为**直连商户**和**服务商**两个体系。

> 以下清单请搭配微信支付文档使用。

### 直连商户

#### 基础支付

- [x] `WechatDirectPayApi` 基础支付，通过`WechatApiProvider#directPayApi`初始化
    - [x] `jsPay` JSAPI/小程序下单
    - [x] `appPay` APP下单
    - [x] `h5Pay` H5下单
    - [x] `nativePay` Native下单
    - [x] 查询订单
        1. `queryTransactionById` 微信支付订单号查询
        2. `queryTransactionByOutTradeNo` 商户订单号查询
    - [x] `close` 关闭订单
    - [x] `WechatPayCallback#transactionCallback` 支付通知，参见下面回调说明
    - [x] `refund` 申请退款
    - [x] `queryRefundInfo` 查询单笔退款
    - [x] `WechatPayCallback#refundCallback` 退款结果通知，参见下面回调说明
    - [x] `downloadTradeBill` 申请交易账单，直接下载为gzip或者txt文件
    - [x] `downloadFundFlowBill` 申请资金账单，直接下载为gzip或者txt文件
- [x] `WechatCombinePayApi` 合单支付，通过`WechatApiProvider#combinePayApi`初始化
    - [x] `jsPay` 合单JSAPI/小程序下单
    - [x] `appPay` 合单APP下单
    - [x] `h5Pay` 合单H5下单
    - [x] `nativePay` 合单Native下单
    - [x] `queryTransactionByOutTradeNo` 查询订单，**合单支付目前只支持商户订单号查询**
    - [x] `close` 合单关闭订单
    - [x] `WechatPayCallback#combineTransactionCallback` 合单支付通知，参见下面回调说明
    - [x] `refund` 合单申请退款
    - [x] `queryRefundInfo` 合单查询单笔退款
    - [x] `WechatPayCallback#refundCallback` 退款结果通知，参见下面回调说明
    - [x] `downloadTradeBill` 申请交易账单，直接下载为gzip或者txt文件
    - [x] `downloadFundFlowBill` 申请资金账单，直接下载为gzip或者txt文件
- [ ] ~~付款码支付~~，暂时没有V3接口，可通过payment spring boot 提供的V2扩展功能自行实现。

#### 经营能力

##### 微信支付分

- [x] `WechatPayScoreApi` 微信支付分，通过`WechatApiProvider#payScoreApi`初始化
    - [x] 公共API
        - [x] `createServiceOrder` 创建支付分订单
        - [x] `queryServiceOrder` 查询支付分订单
        - [x] `cancelServiceOrder` 取消支付分订单
        - [x] `modifyServiceOrder` 修改订单金额
        - [x] `completeServiceOrder` 完结支付分订单
        - [x] `syncServiceOrder` 同步服务订单信息
        - [x] `WechatPayCallback#payscoreUserOrderCallback` 支付成功回调通知，参见下面回调说明
        - [x] `refund` 支付分申请退款
        - [x] `queryRefundInfo` 支付分查询单笔退款
        - [x] `WechatPayCallback#refundCallback` 支付分退款结果通知，参见下面回调说明
    - [x] 免确认预授权模式
        - [x] `permissions` 商户预授权
        - [x] `queryPermissionsByAuthCode` 查询与用户授权记录（授权协议号）
        - [x] `terminatePermissionsByAuthCode` 解除用户授权关系（授权协议号）
        - [x] `queryPermissionsByOpenId` 查询与用户授权记录（openid）
        - [x] `terminatePermissionsByOpenId` 解除用户授权关系（openid）
        - [x] `WechatPayCallback#permissionCallback` 开启/解除授权服务回调通知，参见下面回调说明
    - [x] 需确认模式
        - [x] `WechatPayCallback#payscoreUserOrderCallback` 确认订单回调通知，参见下面回调说明

##### 支付即服务

- [x] `WechatSmartGuideApi` 支付即服务，通过`WechatApiProvider#smartGuideApi`初始化
    - [x] `register` 服务人员注册
    - [x] `assign` 服务人员分配
    - [x] `query` 服务人员查询
    - [x] `modify` 服务人员信息更新

#### 行业方案

##### 智慧商圈

- [x] `WechatBusinessCircleApi` 智慧商圈，通过`WechatApiProvider#businessCircleApi`初始化
    - [ ] 商圈会员积分服务授权结果通知回调（未实现）
    - [x] `WechatPayCallback#mallTransactionCallback` 商圈会员场内支付结果通知，参见下面回调说明
    - [x] `apply` 商圈积分同步
    - [x] `WechatPayCallback#mallRefundCallback` 商圈会员场内退款通知，参见下面回调说明
    - [x] `queryAuthStatus` 商圈积分授权查询
    - [ ] 商圈会员待积分状态查询（未实现）
    - [ ] 商圈会员停车状态同步（未实现）

##### 微信支付分停车服务

- [x] `WechatPayScoreParkingApi` 微信支付分停车服务，通过`WechatApiProvider#payScoreParkingApi`初始化
    - [x] `find` 查询车牌服务开通信息
    - [x] `parking` 创建停车入场
    - [x] `transactionsParking` 扣费受理
    - [x] `queryTransactionByOutTradeNo` 查询订单
    - [x] `WechatPayCallback#payscoreParkingCallback` 停车入场状态变更通知，参见下面回调说明
    - [x] `WechatPayCallback#payscoreTransParkingCallback` 订单支付结果通知，参见下面回调说明
    - [x] `refund` 申请退款
    - [x] `queryRefundInfo` 查询单笔退款
    - [x] `WechatPayCallback#refundCallback` 退款结果通知，参见下面回调说明

#### 营销工具

##### 代金券

- [x] `WechatMarketingFavorApi` 代金券，通过`WechatApiProvider#payScoreParkingApi`初始化
    - [x] `createStock` 创建代金券批次
    - [x] `startStock` 激活代金券批次
    - [x] `sendStock` 发放代金券
    - [x] `pauseStock` 暂停代金券批次
    - [x] `restartStock` 重启代金券批次
    - [x] `queryStocksByMch` 条件查询批次列表
    - [x] `queryStockDetail` 查询批次详情
    - [x] `queryCouponDetails` 查询代金券详情
    - [x] `queryMerchantsByStockId` 查询代金券可用商户
    - [x] `queryStockItems` 查询代金券可用单品
    - [x] `queryUserCouponsByMchId` 根据商户号查用户的券
    - [x] `downloadStockUseFlow` 下载批次核销明细
    - [x] `downloadStockRefundFlow` 下载批次退款明细
    - [x] `setMarketingFavorCallback` 设置消息通知地址
    - [x] `WechatPayCallback#couponCallback` 核销事件回调通知，参见下面回调说明
    - [x] `sendCouponsCard` 发放消费卡

> `sendCouponsCard` 发放消费卡，功能仅向指定邀约商户开放，如有需要请联系微信支付运营经理。

##### 商家券

- [x] `WechatMarketingBusiFavorApi` 商家券，通过`WechatApiProvider#busiFavorApi`初始化
    - [x] `createStock` 创建商家券券批次
    - [x] `queryStockDetail` 查询商家券详情
    - [x] `use` 核销用户券
    - [x] `queryUserStocks` 根据过滤条件查询用户券
    - [x] `queryUserCoupon` 查询用户单张券详情
    - [x] `uploadCouponCodes` 上传预存code
    - [x] `setCallbacks` 设置商家券事件通知地址
    - [x] `getCallbacks` 查询商家券事件通知地址
    - [x] `associate` 关联订单信息
    - [x] `disassociate` 取消关联订单信息
    - [x] `budget` 修改批次预算
    - [x] `updateStock` 修改商家券基本信息
    - [x] `refund` 申请退券
    - [x] `deactivate` 使券失效
    - [x] `payMakeup` 营销补差付款
    - [x] `queryMakeup` 查询营销补差付款单详情
    - [x] `payMakeup` 营销补差付款
    - [x] `WechatPayCallback#busiFavorReceiveCallback` 领券事件回调通知，参见下面回调说明

##### 委托营销

- [x] `WechatMarketingPartnershipApi` 委托营销，通过`WechatApiProvider#marketingshipApi`初始化
    - [x] `build` 建立合作关系
    - [x] `query` 查询合作关系列表

##### 支付有礼

- [ ] 功能实现中……

##### 图片上传（营销专用）

- [x] 参见 **其它能力**

##### 现金红包（基于V2）

- [x] `WechatPayRedpackApi` 现金红包，通过`WechatApiProvider#redpackApi`初始化
    - [x] `sendRedpack` 发放随机红包
    - [x] `sendRedpack` 发放裂变红包
    - [x] `redpackInfo` 查询红包信息

> 重要：**基于V2实现，因此需要在配置文件中配置v2支付对应的`appSecret`参数**

#### 资金应用

##### 商家转账到零钱

- [x] `WechatBatchTransferApi` 商家转账到零钱，通过`WechatApiProvider#batchTransferApi`初始化
    - [x] `batchTransfer` 发起批量转账
    - [x] 查询转账批次单
        1. `queryBatchByBatchId` 通过微信批次单号查询批次单
        2. `queryBatchByOutBatchNo` 通过商家批次单号查询批次单
    - [x] 查询转账明细单
        1. `queryBatchDetailByWechat` 通过微信明细单号查询明细单
        2. `queryBatchDetailByMch` 通过商家明细单号查询明细单
    - [x] 申请转账电子回单
        1. `receiptBill` 转账账单电子回单申请受理接口
        2. `downloadBill` 查询转账账单电子回单接口，附带下载能力
    - [x] 申请转账明细电子回单
        1. `transferElectronic` 受理转账明细电子回单
        2. `queryTransferElectronicResult` 查询转账账单电子回单接口

##### 分账

- [x] `WechatProfitsharingApi` 分账，通过`WechatApiProvider#profitsharingApi`初始化
    - [x] `profitsharingOrders` 请求分账
    - [x] `queryProfitsharingOrder` 查询分账结果
    - [x] `returnOrders` 请求分账回退
    - [x] `queryReturnOrders` 查询分账回退结果
    - [x] `unfreeze` 解冻剩余资金
    - [x] `queryAmounts` 查询剩余待分金额
    - [x] `addReceivers` 添加分账接收方
    - [x] `deleteReceivers` 删除分账接收方
    - [x] `downloadMerchantBills` 申请分账账单

#### 风险合规

##### 消费者投诉2.0

- [ ] 功能实现中……

#### 其它能力

##### 清关报关

- [ ] 清关报关 暂时没有V3接口，可通过payment spring boot 提供的V2扩展功能自行实现。

##### 媒体上传

> 包含图片上传和视频上传

- [x] `WechatMediaApi` 媒体上传，通过`WechatApiProvider#mediaApi`初始化
    - [x] `mediaImageUpload` 图片上传
    - [x] `mediaVideoUpload` 视频上传
    - [x] `marketingImageUpload` 营销图片上传

> 通过营销**图片上传API**上传图片后可获得图片url地址。图片url可在微信支付营销相关的API使用，包括商家券、代金券、支付有礼等。
### 服务商
施工中……