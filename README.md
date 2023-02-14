
   <div align="center" style="margin-bottom: 10px"><h1>最全最好用的微信支付V3 Spring Boot 组件</h1></div>

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


<p align="center">如果你感觉这个项目不错，请点击右上角的Star以鼓励作者，谢谢。</p>

## 简介

Java微信支付V3支付Spring Boot Starter，支持微信优惠券，代金券、商家券、智慧商圈、商家转账到零钱、公众号支付、微信小程序支付、分账、支付分、商家券、合单支付、先享卡、电商收付通等全部微信支付功能API，同时满足多个服务商、多个商户开发需求。一键集成，屏蔽了复杂度，API友好，上手快，欢迎star。

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

## 使用入门
### 集成配置
关于集成配置请详细阅读[payment-spring-boot GitHub文档](https://notfound403.github.io/payment-spring-boot)中[快速接入](https://notfound403.github.io/payment-spring-boot/#/quick_start)章节
### 调用示例
#### 开启支付
需要手动通过`@EnableMobilePay`注解开启支付
```java
import cn.felord.payment.autoconfigure.EnableMobilePay;
import org.springframework.context.annotation.Configuration;

@EnableMobilePay
@Configuration
public class PayConfig {
}
```

#### 支付接口调用
这里简单以小程序支付为例，写了一个Spring MVC 控制器，在实践中建议对`WechatApiProvider`进行二次封装作服务层调用
```java 
import cn.felord.payment.wechat.enumeration.TradeBillType;
import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatDirectPayApi;
import cn.felord.payment.wechat.v3.model.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;

/**
 * 支付接口开发样例，以小程序支付为例.
 */
@Profile({"wechat", "dev"})
@RestController
@RequestMapping("/marketing")
public class PayController {
    @Autowired
    private WechatApiProvider wechatApiProvider;
    String TENANT_ID = "mobile";
    /**
     * 总流程建议为 生成商品订单 -> 生成对应的支付订单 -> 支付操作 -> 支付结果回调更新 -> 结束
     * <p>
     * 此处建议在商品订单生成之后调用
     *
     * @param orderId 商品订单id
     * @return the object node
     */
    @PostMapping("/js")
    public ObjectNode js(@RequestParam String orderId) {

        //TODO
        // 查询该orderId下是否生成了支付订单
        // 如果没有
        // 新增支付订单存入数据库 并标明支付状态为【待支付】
        // 根据新生成的支付订单信息向微信支付发起支付 并根据返回结果进行处理
        // 如果有状态为待支付
        // 根据待支付订单信息向微信支付发起支付 并根据返回结果进行处理
        // 如果有状态为待支付之外的状态
        // 根据产品的业务设计自行实现
        // 支付状态更新逻辑在【回调接口 /wxpay/callbacks/transaction】中处理  需要幂等处理

        // 开发时需要指定使用的商户租户配置 这里为 mobile 请参考 application-wechat.yml


        PayParams payParams = new PayParams();

        payParams.setDescription("felord.cn");
        //
        // 商户侧唯一订单号 建议为商户侧支付订单号 订单表主键 或者唯一标识字段
        payParams.setOutTradeNo("X135423420201521613448");
        // 需要定义回调通知
        payParams.setNotifyUrl("/wxpay/callbacks/transaction");
        Amount amount = new Amount();
        amount.setTotal(100);
        payParams.setAmount(amount);
        // 此类支付  Payer 必传  且openid需要同appid有绑定关系 具体去看文档
        Payer payer = new Payer();
        payer.setOpenid("ooadI5kQYrrCqpgbisvC8bEw_oUc");
        payParams.setPayer(payer);

        return wechatApiProvider.directPayApi(TENANT_ID)
                .jsPay(payParams)
                .getBody();
    }


    /**
     * 下载对账单 如果要解析内容的话自行实现
     *
     * @return the response entity
     */
    @GetMapping("/tradebill")
    public ResponseEntity<Resource> download() {
        WechatDirectPayApi wechatDirectPayApi = wechatApiProvider.directPayApi(TENANT_ID);

        TradeBillParams tradeBillParams = new TradeBillParams();
        tradeBillParams.setBillDate(LocalDate.of(2021, Month.MAY, 20));
        tradeBillParams.setBillType(TradeBillType.ALL);
        return wechatDirectPayApi.downloadTradeBill(tradeBillParams);
    }

    /**
     * 下载申请资金账单  如果要解析内容的话自行实现
     *
     * @return the response entity
     */
    @GetMapping("/fundflowbill")
    public ResponseEntity<Resource> fundFlowBill() {
        WechatDirectPayApi wechatDirectPayApi = wechatApiProvider.directPayApi(TENANT_ID);

        FundFlowBillParams fundFlowBillParams = new FundFlowBillParams();
        fundFlowBillParams.setBillDate(LocalDate.of(2021, Month.MAY, 20));

        return wechatDirectPayApi.downloadFundFlowBill(fundFlowBillParams);
    }
}
```
#### 回调示例
回调可通过以下示例实现，多租户的回调可将租户ID`tenantId`作为路径参数来实现

```java
import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatMarketingFavorApi;
import cn.felord.payment.wechat.v3.WechatPayCallback;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注意为了演示该配置在使用微信配置application-wechat.yaml才生效
 * <p>
 * 务必保证回调接口的幂等性
 * <p>
 * 微信回调控制器，当支付成功、代金券核销成功后，微信支付服务器会通过回调进行通知商户侧。
 * 商户侧可以根据微信的回调通知进行支付的后续处理，例如支付状态的变更等等。
 * 需要注意的是回调接口需要白名单放行。
 * <p>
 * 开发者只需要编写对结果的{@link java.util.function.Consumer}即可。
 * <p>
 * 请注意：返回的格格式必须是{@link WechatPayCallback} 给出的格式，不能被包装和更改，切记！
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Profile({"wechat","dev"})
@RestController
@RequestMapping("/wxpay/callbacks")
public class CallbackController {
    private static final String TENANT_ID = "mobile";
    @Autowired
    private WechatApiProvider wechatApiProvider;


    /**
     * 代金券核销通知.
     * <p>
     * 需要手动调用{@link WechatMarketingFavorApi#setMarketingFavorCallback(String)} 设置，一次性操作!
     *
     * @param wechatpaySerial    the wechatpay serial
     * @param wechatpaySignature the wechatpay signature
     * @param wechatpayTimestamp the wechatpay timestamp
     * @param wechatpayNonce     the wechatpay nonce
     * @param request            the request
     * @return the map
     */
    @SneakyThrows
    @PostMapping("/coupon")
    public Map<String, ?> couponCallback(
            @RequestHeader("Wechatpay-Serial") String wechatpaySerial,
            @RequestHeader("Wechatpay-Signature") String wechatpaySignature,
            @RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
            @RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
            HttpServletRequest request) {
        String body = request.getReader().lines().collect(Collectors.joining());
        // 对请求头进行验签 以确保是微信服务器的调用
        ResponseSignVerifyParams params = new ResponseSignVerifyParams();
        params.setWechatpaySerial(wechatpaySerial);
        params.setWechatpaySignature(wechatpaySignature);
        params.setWechatpayTimestamp(wechatpayTimestamp);
        params.setWechatpayNonce(wechatpayNonce);
        params.setBody(body);
        return wechatApiProvider.callback(TENANT_ID).couponCallback(params, data -> {
            //TODO 对回调解析的结果进行消费  需要保证消费的幂等性 微信有可能多次调用此接口
        });
    }

    /**
     * 微信支付成功回调.
     * <p>
     * 无需开发者判断，只有扣款成功微信才会回调此接口
     *
     * @param wechatpaySerial    the wechatpay serial
     * @param wechatpaySignature the wechatpay signature
     * @param wechatpayTimestamp the wechatpay timestamp
     * @param wechatpayNonce     the wechatpay nonce
     * @param request            the request
     * @return the map
     */
    @SneakyThrows
    @PostMapping("/transaction")
    public Map<String, ?> transactionCallback(
            @RequestHeader("Wechatpay-Serial") String wechatpaySerial,
            @RequestHeader("Wechatpay-Signature") String wechatpaySignature,
            @RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
            @RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
            HttpServletRequest request) {
        String body = request.getReader().lines().collect(Collectors.joining());
        // 对请求头进行验签 以确保是微信服务器的调用
        ResponseSignVerifyParams params = new ResponseSignVerifyParams();
        params.setWechatpaySerial(wechatpaySerial);
        params.setWechatpaySignature(wechatpaySignature);
        params.setWechatpayTimestamp(wechatpayTimestamp);
        params.setWechatpayNonce(wechatpayNonce);
        params.setBody(body);
        return wechatApiProvider.callback(TENANT_ID).transactionCallback(params, data -> {
            //TODO 对回调解析的结果进行消费  需要保证消费的幂等性 微信有可能多次调用此接口
        });
    }

    /**
     * 微信合单支付成功回调.
     * <p>
     * 无需开发者判断，只有扣款成功微信才会回调此接口
     *
     * @param wechatpaySerial    the wechatpay serial
     * @param wechatpaySignature the wechatpay signature
     * @param wechatpayTimestamp the wechatpay timestamp
     * @param wechatpayNonce     the wechatpay nonce
     * @param request            the request
     * @return the map
     */
    @SneakyThrows
    @PostMapping("/combine_transaction")
    public Map<String, ?> combineTransactionCallback(
            @RequestHeader("Wechatpay-Serial") String wechatpaySerial,
            @RequestHeader("Wechatpay-Signature") String wechatpaySignature,
            @RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
            @RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
            HttpServletRequest request) {
        String body = request.getReader().lines().collect(Collectors.joining());
        // 对请求头进行验签 以确保是微信服务器的调用
        ResponseSignVerifyParams params = new ResponseSignVerifyParams();
        params.setWechatpaySerial(wechatpaySerial);
        params.setWechatpaySignature(wechatpaySignature);
        params.setWechatpayTimestamp(wechatpayTimestamp);
        params.setWechatpayNonce(wechatpayNonce);
        params.setBody(body);
        return wechatApiProvider.callback(TENANT_ID).combineTransactionCallback(params, data -> {
            //TODO 对回调解析的结果进行消费  需要保证消费的幂等性 微信有可能多次调用此接口
        });
    }
}
```



## 开源协议
**Apache 2.0**

## 仓库地址
- [GitHub](https://github.com/NotFound403/payment-spring-boot)
- [Gitee](https://gitee.com/felord/payment-spring-boot)

## QQ交流群
为了交流解惑，新建QQ群，可通过扫码进入。

![QQ交流群](./docs/img/qqun.png)
