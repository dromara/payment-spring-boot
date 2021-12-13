## 1.0.12.RELEASE
- refector: 变更依赖管理方式，交由spring-boot-dependencies管理依赖([#40](https://github.com/NotFound403/payment-spring-boot/pull/40))
- 微信支付
  - fix: 修复多租户配置下，平台证书刷新错误的问题([#49](https://github.com/NotFound403/payment-spring-boot/issues/49)) 
  - fix: 分账API描述符错误([#48](https://github.com/NotFound403/payment-spring-boot/issues/48)) 。
  - refactor: 避免受jackson类库xml模块的影响
  - refactor: V2签名优化
  
## 1.0.11.RELEASE

- 微信支付
  - feat:  bcprov-jdk15to18算法库从1.66 升级到 1.67
  - feat:  微信支付 《支付通知API》新增优惠功能（promotion_detail）字段
  - feat:  微信支付基础支付《申请退款API》、《查询退款API》新增字段 from（退款出资账户及金额）
  - feat: 现在支持V3分账功能，目前只支持直连商户分账`WechatProfitsharingApi`和服务商分账`WechatPartnerProfitsharingApi`。
  - feat: 完善V3批量转账到零钱API，增加实现：转账明细电子回单受理API、查询转账明细电子回单受理结果API、查询账户实时余额API、查询账户日终余额API、商户银行来账查询API
  - refactor: 微信支付分分账标记默认改为不分账
  - refactor: 平台证书刷新逻辑优化 ([#I3NGSB](https://gitee.com/felord/payment-spring-boot/issues/I3NGSB))
  - refactor: 交易账单和资金账单现在能够正常的下载文件了，可以根据参数自动选择下载为gzip或者txt文件
  - fix: 批量转账到零钱:微信明细单号查询明细单API,商家明细单号查询明细单API 参数错误
  - fix: 修复查询代金券参数的错误
- 支付宝
  - feat: 支付宝增加字段`classpathUsed`来标识是否使用类路径，默认`true`。证书路径可依此来决定是使用绝对路径还是类路径

## 1.0.10.RELEASE

- 微信支付
  - feat: 微信支付V2分账接口实现，感谢**zacone**同学贡献的PR
  - factor: 优化证书加载方式
  - factor: 商家券修改API的请求方式变更为`Patch`
  - fix: 修复微信支付V3中native支付通知回调`successTime`字段无时区信息的问题([#I3ED43](https://gitee.com/felord/payment-spring-boot/issues/I3ED43)) 
- 支付宝    
  - fix: 修复支付宝Maven打包无法读取证书的问题([#24](https://github.com/NotFound403/payment-spring-boot/issues/24))

## 1.0.9.RELEASE

- 微信支付
  - refactor: `WechatPartnerPayApi` 加入**Spring IOC**
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