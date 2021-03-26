## 1.0.9.RELEASE

- 微信支付
  - refactor: 服务商支付 WechatPartnerPayApi 加入Spring IOC
  - fix: 支付分支付成功回调反序列化异常 ([#21](https://github.com/NotFound403/payment-spring-boot/issues/21))
  - fix: 修复枚举空指针问题 ([#22](https://github.com/NotFound403/payment-spring-boot/issues/22))
## 1.0.8.RELEASE

- 微信支付
  - feat: 对基础支付-服务商支付进行支持
  - refactor: 在异常返回时对非2xx状态返回的元信息进行包装方便序列化([#16](https://github.com/NotFound403/payment-spring-boot/issues/16))
  - fix: 修复退款数据中时间无法解析的异常([#13](https://github.com/NotFound403/payment-spring-boot/issues/13))
  - fix: 修复类成员的属性([#14](https://github.com/NotFound403/payment-spring-boot/issues/14))
  - fix: 查询并下载转账电子回单API接口，下载文件接口签名失败([#18](https://github.com/NotFound403/payment-spring-boot/issues/18))

## 1.0.7.RELEASE

- 微信支付
  - refactor: X509证书加载优化。
  - refactor: 移除过期的`WechatPayRefundApi`。
  - refactor: 优化RestTemplate在低版本引起的一个I/O异常,详见 [spring-framework#21321](https://github.com/spring-projects/spring-framework/issues/21321)。
  - refactor: 在请求头Content-Type中声明字符集UTF-8,避免中文乱码。
  - fix: 修复退款回调中退款状态枚举无法正确被解析的异常([#11](https://github.com/NotFound403/payment-spring-boot/issues/11))。

## 1.0.6.RELEASE

- 微信支付
  - feat:实现微信支付V3批量转账到零钱所有API（WechatBatchTransferApi），助力抗击新冠疫情。
  - feat:实现微信支付V3退款以及退款通知等所有退款相关的API，推荐使用新的V3退款。
  - refactor: V2退款进入过期模式，由于V3已经推出了退款功能，所以V2退款 WechatPayRefundApi 被标记为 Deprecated 未来会被移除。
  - refactor: 交易状态增加等待扣款状态，根据微信最新的业务变动增加 “ACCEPT” 字段用来标记“已接收，等待扣款”状态。

## 1.0.5.RELEASE

- 微信支付
  - feat:增加V2退款接口
  - feat:增加V2企业付款到零钱接口
  - feat:增加V2红包接口
  - refactor:优化了一些底层功能
  - refactor:调整了支付配置Spring注入的顺序
  - refactor:JCE由SPI提供，不再使用JDK内置
  - fix: 关单接口调用异常

## 1.0.4.RELEASE

- 微信支付
  - feat: 增加微信支付商家券相关接口`WechatMarketingBusiFavorApi`，商家券请阅读相关产品文档。
  - feat: 代金券功能增加发放消费卡接口。
  - refactor: 现在app支付、小程序支付返回所有客户端拉起支付的参数，不再需要用户再进行签名操作了。
  - refactor: 其它一些代码优化。
  - build: SDK开发环境 Spring Boot 版本升级到2.4.2。
  - fix: 支付分`RiskFund`下枚举无法使用的问题[(#2)](https://github.com/NotFound403/payment-spring-boot/issues/2)。

## 1.0.3.RELEASE

- 微信支付
  - feat: 完善合单支付账单
    1. 增加合单支付-申请交易账单API。
    2. 增加合单支付-申请资金账单API。
  - fix: #I2BCMZ 合单支付url不正确的问题。
  - fix: 微信支付能够正确根据环境条件动态启用了，修复了不配置微信支付时，无法启用支付宝的问题。
    1. 当配置中存在`wechat.pay.v3`配置时，微信支付启用；否则微信支付不启用，不会再影响支付宝的运行。
  - refactor: 先享卡优化
  
## 1.0.2.RELEASE
- 微信支付
  - feat: 接入微信支付分
  - feat: 接入微信支付先享卡
  - fix: 支付回调参数不全的问题

## 1.0.1.RELEASE

- 微信支付
  - 基础支付-商户直连 bugfix
  - 合单支付

## 1.0.0.RELEASE

- 微信支付
  - 基础支付-商户直连
  - 代金券
  - 多租户  
- 支付宝
  - 仅仅引入支付宝SDK，后续维护以支付宝SDK变动为准
  - 支付宝暂时不支持多租户