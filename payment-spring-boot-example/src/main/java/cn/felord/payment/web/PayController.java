package cn.felord.payment.web;

import cn.felord.payment.wechat.enumeration.TradeBillType;
import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatDirectPayApi;
import cn.felord.payment.wechat.v3.model.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;

/**
 * 支付接口开发样例，以小程序支付为例.
 *
 * @author felord.cn
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/marketing")
public class PayController {
  private final WechatApiProvider wechatApiProvider;
  private static final String TENANT_ID = "100000000";

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
