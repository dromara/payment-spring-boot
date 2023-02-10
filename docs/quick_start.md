## Maven 中央仓库坐标

```xml
<dependency>
    <groupId>cn.felord</groupId>
    <artifactId>payment-spring-boot-starter</artifactId>
    <version>1.0.16.RELEASE</version>
</dependency>
```
> 基于 **Spring Boot 2.x** 

## Spring Boot 版本适配

需要自行克隆项目，修改根目录下的`pom.xml`以下配置项：
```xml
    <properties>
        <!--  修改为你对应的Spring Boot版本号 -->
        <spring-boot.version>2.4.0</spring-boot.version>
    </properties>
```
然后安装使用

## 自行安装

以**Spring Boot Starter**的形式集成到项目中。从[GitHub项目地址](https://github.com/NotFound403/payment-spring-boot)拉取`release`分支到本地后使用以下两种方式之一进行环境集成：

- `mvn install` 安装到本地
- `mvn deploy`部署到**Maven**仓库



然后引用**Maven**坐标，请注意`${payment-spring-boot-starter.version}`要同你安装的一致。

```xml
      <dependency>
            <groupId>cn.felord</groupId>
            <artifactId>payment-spring-boot-starter</artifactId>
            <version>${payment-spring-boot-starter.version}</version>
      </dependency>
```
## 配置

### 微信支付
在Spring Boot项目中的`application.yaml`中配置`wechat.pay.v3`相关参数。
```yaml
wechat:
  pay:
    v3:
#    租户id  
     <tentantID>: 
#  应用appId  服务商模式下为服务商的appid 必填  
      app-id: xxxxxxxx
#  v2 api 密钥  1.0.5版本以后如果用到V2的接口时必填
      app-secret: xxxxxxxxxxx
#  api v3 密钥 必填
      app-v3-secret: xxxxxxxx
#  微信支付商户号 服务商模式下为服务商的mchid 必填
      mch-id: xxxxxxx
#  商户服务器域名 用于回调  需要放开回调接口的安全策略 必填
      domain: https://felord.cn
#  商户 api 证书路径 必填  填写classpath路径 位于 maven项目的resources文件下
      cert-path: apiclient_cert.p12
 
```
#### <span style="color:red;">微信支付 tentantID 场景说明</span>

当项目中需要同时支持微信App支付、微信公众号支付、微信小程序支付时，上述配置中的`app-id`可能为多个，因此我们需要进行路由标识以区分它们，所以这里需要你定义一个`tentanID`来进行标识。假如我们同时有App支付和微信小程序支付，我们应该这样配置：

```yaml
wechat:
  pay:
    v3:
    # App租户租户标识为 mobile
     mobile:
      app-id: wx524534x423234
      app-secret: felord
      app-v3-secret: 0e5b123323h12234927e455703ec
      mch-id: 1603337223
      domain: https://felord.cn/mobile
      cert-path: mobile/apiclient_cert.p12
    # 微信小程序租户标识为 miniapp  
     miniapp:
      app-id: wx344123x3541223
      app-secret: felord
      app-v3-secret: 0e5b123323h12234927e455703ec
      mch-id: 1603337223
      domain: https://felord.cn/miniapp
      cert-path: miniapp/apiclient_cert.p12
```

>  ❗注意：在一套系统中需要开发者保证`tentanID`唯一。

### 支付宝

在Spring Boot项目中的`application.yaml`中配置`ali.pay.v1`相关参数。证书细节参见【日常踩坑】
```yaml
ali:
  pay:
    v1:
# 可以替换为沙箱
      server-url: https://openapi.alipaydev.com/gateway.do
# 蚂蚁开放平台申请并认证的应用appId
      app-id: 2016223700762343
      app-private-key-path: META-INF/app_rsa
      alipay-public-cert-path: META-INF/alipayCertPublicKey_RSA2.crt
      alipay-root-cert-path: META-INF/alipayRootCert.crt
      app-cert-public-key-path: META-INF/appCertPublicKey_2016102700769563.crt
      charset: utf-8
      format: json
      sign-type: RSA2
```
> ❗注意：只有`ali.pay.v1.app-id`设置了有效值才能启用支付宝API。

## 启用配置

配置完毕后需要启用`@EnableMobilePay`注解来开启支付功能：

```java
@EnableMobilePay
@Configuration
public class PayConfig {
}
```
## 使用API

### 微信支付

微信支付V3开放接口都是通过`WechatApiProvider`引入的，当[启用配置](/quick_start?id=启用配置)步骤完成后会被初始化并注入了**Spring IoC**，可通过如下形式引入：
```java
    @Autowired
    WechatApiProvider wechatApiProvider;
```
`WechatApiProvider`需要开发者明确租户ID[tenantID](quick_start?id=微信支付-tentantid-场景说明)以初始化具体场景的**Api**。例如查询终端用户在租户`mobile`下的优惠券：

```java
     // 查询商户下的优惠券
     @Test
     public void v3MchStocks() {
        // 配置文件中对应的tenantID:
        String tenantId =“mobile”;
        StocksQueryParams params = new StocksQueryParams();
        params.setOffset(0);
        params.setLimit(10);
        WechatResponseEntity<ObjectNode> objectNodeWechatResponseEntity = wechatApiProvider.favorApi(tenantId).queryStocksByMch(params);
        System.out.println("objectNodeWechatResponseEntity = " + objectNodeWechatResponseEntity);
     }
```


### 支付宝
当[启用配置](/quick_start?id=启用配置)步骤完成后会初始化支付宝支付客户端接口`AlipayClient`并注入**Spring IoC**，可通过以下形式引入：
```java
    @Autowired
    AlipayClient alipayClient;
```
调用，以现金红包为例：
```java
    @SneakyThrows
    public void campaignCash() {
        AlipayMarketingCampaignCashCreateRequest request = new AlipayMarketingCampaignCashCreateRequest();
        request.setBizContent("{" +
                "\"coupon_name\":\"XXX周年庆红包\"," +
                "\"prize_type\":\"random\"," +
                "\"total_money\":\"10000.00\"," +
                "\"total_num\":\"1000\"," +
                "\"prize_msg\":\"XXX送您大红包\"," +
                "\"start_time\":\"2020-11-02 22:48:30\"," +
                "\"end_time\":\"2020-12-01 22:48:30\"," +
                "\"merchant_link\":\"http://www.weibo.com\"," +
                "\"send_freqency\":\"D3|L10\"" +
                "  }");

        AlipayMarketingCampaignCashCreateResponse execute = alipayClient.certificateExecute(request);

        System.out.println("execute = " + execute.getBody());
    }
```