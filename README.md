
   <p align="center"><h1>最全最好用的微信支付V3 Spring Boot 组件</h1></p>

<p align="center">
    <a target="_blank" href="https://github.com/NotFound403/payment-spring-boot/blob/release/LICENSE">
     	<img alt="" src="https://img.shields.io/github/license/NotFound403/payment-spring-boot"/>
    </a>
    <a target="_blank" href="https://felord.cn">
     	<img alt="" src="https://img.shields.io/badge/java-8-red"/>
    </a>   
    <a target="_blank" href="https://spring.io">
     	<img alt="" src="https://img.shields.io/badge/spring%20boot-2.4%2B-brightgreen"/>
    </a>   
    <a target="_blank" href="https://mvnrepository.com/artifact/cn.felord/payment-spring-boot">
     	<img alt="" src="https://img.shields.io/maven-central/v/cn.felord/payment-spring-boot.svg?style=flat-square"/>
    </a>   
    <a target="_blank" href="https://github.com/NotFound403/payment-spring-boot">
     	<img alt="" src="https://img.shields.io/github/stars/NotFound403/payment-spring-boot?style=social"/>
    </a>    
    <a target="_blank" href="https://gitee.com/felord/payment-spring-boot/stargazers">
     	<img alt="" src="https://gitee.com/felord/payment-spring-boot/badge/star.svg?theme=white"/>
    </a>    
    <a target="_blank" href="https://work.weixin.qq.com/kfid/kfc9d9d759f27f087e1">
     	<img alt="点击立即微信咨询" src="https://img.shields.io/badge/%E7%82%B9%E5%87%BB-%E5%BE%AE%E4%BF%A1%E5%92%A8%E8%AF%A2-brightgreen"/>
    </a>    
    <a target="_blank" href="https://jq.qq.com/?_wv=1027&k=qRTKHWY0">
     	<img alt="点击加入QQ交流群" src="https://img.shields.io/badge/QQ%E4%BA%A4%E6%B5%81%E7%BE%A4-945342113-ff69b4"/>
    </a>
</p>


<p align="center">如果你感觉这个项目不错，请点击右上角的Star以鼓励作责，谢谢。</p>

# 简介

微信支付V3支付，支持微信优惠券，代金券、商家券、智慧商圈、商家转账到零钱、公众号支付、微信小程序支付、分账、支付分、商家券、合单支付、先享卡、电商收付通等全部微信支付功能API，同时满足多个服务商、多个商户开发需求。一键集成，API友好，上手快，欢迎star。

## Maven 最新中央仓库坐标

```xml
<dependency>
    <groupId>cn.felord</groupId>
    <artifactId>payment-spring-boot-starter</artifactId>
    <version>1.0.16.RELEASE</version>
</dependency>
```
## JDK问题

**推荐使用Open JDK**，原因参见[FBI Warning](https://github.com/NotFound403/payment-spring-boot/issues/5)

## 文档地址
- [payment-spring-boot GitHub文档](https://notfound403.github.io/payment-spring-boot)
- [payment-spring-boot Gitee文档](https://felord.gitee.io/payment-spring-boot)


## 目前已经实现所有服务商和直连商户接口

- 实现微信支付多商户
- 集成支付宝SDK、快速接入Spring Boot
- 实现微信支付V3 基础支付
- 实现微信支付V3 合单支付
- 实现微信支付V3 代金券
- 实现微信支付V3 微信支付分
- 实现微信支付V3 先享卡
- 实现微信支付V3 商家券
- 实现微信支付V3 批量转账到零钱

更新日志参考[changelog](https://notfound403.github.io/payment-spring-boot/#/changelog)

## 核心API结构
![](https://asset.felord.cn/blog/20220613092244.png)

- `WechatPartnerProfitsharingApi`  微信支付服务商V3分账
- `WechatBrandProfitsharingApi` 微信支付服务商V3连锁品牌分账
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
- `WechatEcommerceApi` 电商收付通
- `WechatSmartGuideApi` 服务商或者直连商户-经营能力-支付即服务
- `WechatGoldPlanApi` 服务商-经营能力-点金计划

> 随着版本迭代功能会增加，可通过API注册表类`WechatPayV3Type`进行API接口检索。

## 开源协议
**Apache 2.0**

## 仓库地址
- [GitHub](https://github.com/NotFound403/payment-spring-boot)
- [Gitee](https://gitee.com/felord/payment-spring-boot)

## QQ交流群
为了交流解惑，新建QQ群，可通过扫码进入。

![QQ交流群](./docs/img/qqun.png)
