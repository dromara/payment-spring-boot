# 移动支付 Spring Boot 组件
提供聚合支付能力。

## 支持类型

- [x] **微信支付V2** 只提供V3不支持的部分，后续全面切向V3。
- [x] **微信支付V3** 全量支持。
- [ ] **支付宝**  施工中…… 
- [ ] **银联支付**  施工中…… 

## 采用技术
- Jackson
- Okhttp
- Ali-pay-sdk
## 使用方法
### 集成
以**Spring Boot Starter**的形式集成到项目中。

```xml
      <dependency>
            <groupId>com.enongm.dianji</groupId>
            <artifactId>payment-spring-boot-autoconfigure</artifactId>
            <version>1.0.0.RELEASE</version>
      </dependency>
```
### 配置
#### 微信支付
在Spring Boot项目中的`application.yaml`中配置`wechat.pay`相关参数。
```yaml
wechat:
  pay:
    v3:
#  沙盒模式 默认不开启 用于开发测试
      sandbox-mode: true
#  服务商模式 默认不开启
      partner-mode: false
#  应用appId 必填
      app-id: wx55a89ae92r5d3b98
#  api 密钥 必填
      app-secret: 13194913301
#  api v3 密钥 必填
      app-v3-secret: 0e5f4fbd1333a3b44927e4355703ec
#  微信支付商户号 必填
      mch-id: 1602325555
#  合作商 选填
      partner-key:
#  商户服务器域名 用于回调  需要放开回调接口的安全策略 必填
      domain: https://127.0.0.1
#  商户 api 证书 必填
      cert-path: apiclient_cert.p12
```
然后启用`@EnableWechatPay`注解：
```java
@EnableMobilePay
@Configuration
public class PayConfig {
}
```
### API使用 
微信支付 V2、V3开放接口引入：
```java
    @Autowired
    WechatPayV3Service wechatPayV3Service;
    @Autowired
    WechatPayV2Service wechatPayV2Service;
```
#### V2
例如V2转账到微信零钱：
```java
        PayToWechatModel model = new PayToWechatModel();
           model.setDesc("test");
           model.setAmount("10000");
           model.setDeviceInfo("IOS");
           model.setOpenid("wx1232131231256655");
           model.setPartnerTradeNo("X323994343345");
           model.setSpbillCreateIp("127.0.0.1");
        WechatResponseBody responseBody = wechatPayV2Service.model(model)
                .payType(V2PayType.PAY_TO_WECHAT)
                .request();

        System.out.println("responseBody = " + responseBody);
```
打印返回：
```

```


#### V3
例如V3 APP 支付

```java
    /**
     * App pay test.
     */
    @Test
    public void appPayTest() {
        wechatPayV3Service
                .request(baseModel -> new V3PayTypeBodyAndConsumer(V3PayType.APP,
                        baseModel.jsonBody()
                        ,System.out::println))
                .withModel(appPayModel());
    }



    /**
     * 构造参数
     */ 
    private static AppPayModel appPayModel() {
        AppPayModel appPayModel = new AppPayModel();

        appPayModel.setGoodsTag("WXG");
        appPayModel.setNotifyUrl("/callback/app");
        appPayModel.setDescription("中国移动-手机充值");
        appPayModel.setOutTradeNo("100862342355223");

        Amount amount = new Amount();
        amount.setTotal(1000);
        appPayModel.setAmount(amount);

        SceneInfo sceneInfo = new SceneInfo();

        sceneInfo.setDeviceId("12312");
        sceneInfo.setPayerClientIp("129.122.0.1");

        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setAddress("cn");
        storeInfo.setAreaCode("471800");
        storeInfo.setId("232313213");
        storeInfo.setName("jack");

        sceneInfo.setStoreInfo(storeInfo);

        H5Info h5Info = new H5Info();

        h5Info.setAppName("APP");
        h5Info.setAppUrl("https://felord");
        h5Info.setBundleId("1313213");
        h5Info.setPackageName("com.dj");
        h5Info.setType("IOS");

//        sceneInfo.setH5Info(h5Info);

        appPayModel.setSceneInfo(sceneInfo);
        return appPayModel;
    }
```
## CHANGELOG
