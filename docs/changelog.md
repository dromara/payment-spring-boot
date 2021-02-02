## 1.0.5.RELEASE
- 微信支付
  - feat:增加V2 退款接口
  - feat:增加V2 企业付款到零钱接口
  - feat:增加V2 红包接口
  - refactor:优化了一些底层功能
  - refactor:整了支付配置Spring注入的顺序
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