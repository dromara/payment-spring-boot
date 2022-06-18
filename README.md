# 最好用的微信支付V3 Spring Boot 组件 

为了满足业务中出现app支付、公众号支付、小程序支付等多appid并存的场景，对原有的进行了增强开发出了多租户版本。

请给[Payment Spring Boot](https://github.com/NotFound403/payment-spring-boot) **Star**以鼓励，谢谢。


## Maven 最新中央仓库坐标

```xml
<dependency>
    <groupId>cn.felord</groupId>
    <artifactId>payment-spring-boot-starter</artifactId>
    <version>1.0.14.RELEASE</version>
</dependency>
```

## 功能
- 实现微信支付多商户
- 集成支付宝SDK、快速接入Spring Boot
- 实现微信支付V3 基础支付
- 实现微信支付V3 合单支付
- 实现微信支付V3 代金券
- 实现微信支付V3 微信支付分
- 实现微信支付V3 先享卡
- 实现微信支付V3 商家券
- 实现微信支付V3 批量转账到零钱

更多参考[changelog](https://notfound403.github.io/payment-spring-boot/#/changelog)

## 核心API结构
![](https://asset.felord.cn/blog/20220613092244.png)

- `WechatPartnerProfitsharingApi`  微信支付服务商V3分账
- `WechatPayCallback`  微信支付V3回调通知工具封装
- `WechatAllocationApi` 微信支付V2分账（未来会移除）
- `WechatMarketingFavorApi` 微信支付代金券V3
- `WechatCombinePayApi` 微信支付合单支付V3
- `WechatPayScoreApi` 微信支付分V3
- `WechatPayRedpackApi` 微信支付V2现金红包
- `WechatDiscountCardApi` 微信支付V3先享卡
- `WechatProfitsharingApi` 微信支付直连商户V3分账
- `WechatPartnerPayApi` 微信支付服务商模式V3普通支付
- `WechatMarketingBusiFavorApi` 微信支付V3商家券
- `WechatPayTransfersApi` 微信支付V2企业付款到零钱，目前不包括到银行卡
- `WechatDirectPayApi` 微信支付直连模式V3普通支付
- `WechatPayScoreParkingApi` 微信支付分V3停车服务
- `WechatBatchTransferApi` 微信支付V3批量转账到零钱 
- `WechatPartnerSpecialMchApi` 微信支付V3服务商商户进件 
- `WechatMediaApi` 微信支付V3媒体上传 

> 随着版本迭代功能会增加。

## 开源协议
**Apache 2.0**

## 仓库地址
- [GitHub](https://github.com/NotFound403/payment-spring-boot)
- [Gitee](https://gitee.com/felord/payment-spring-boot)

## 文档地址
- [payment-spring-boot GitHub文档](https://notfound403.github.io/payment-spring-boot)
- [payment-spring-boot Gitee文档](https://felord.gitee.io/payment-spring-boot)
 
## QQ交流群
为了交流解惑，新建QQ群，可通过扫码进入。

![QQ交流群](./docs/img/qqun.png)
