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
启用`@EnableWechatPay`注解
### API使用 
施工中……

## CHANGELOG
