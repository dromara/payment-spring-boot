package cn.felord.payment.web;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatMarketingFavorApi;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * callback
 *
 * @author felord.cn
 * @since 2023/12/29 23:08
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wxpay/callbacks")
public class CallbackController {
  private static final String TENANT_ID = "mobile";
  private final WechatApiProvider wechatApiProvider;


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
  @PostMapping("/coupon")
  public Map<String, ?> couponCallback(
      @RequestHeader("Wechatpay-Serial") String wechatpaySerial,
      @RequestHeader("Wechatpay-Signature") String wechatpaySignature,
      @RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
      @RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
      HttpServletRequest request) throws IOException {
    String body = request.getReader().lines().collect(Collectors.joining());
    // 对请求头进行验签 以确保是微信服务器的调用
    ResponseSignVerifyParams params = new ResponseSignVerifyParams();
    params.setWechatpaySerial(wechatpaySerial);
    params.setWechatpaySignature(wechatpaySignature);
    params.setWechatpayTimestamp(wechatpayTimestamp);
    params.setWechatpayNonce(wechatpayNonce);
    params.setBody(body);
    return wechatApiProvider.callback(TENANT_ID).couponCallback(params, data -> {
      //对回调解析的结果进行消费 需要保证消费的幂等性 微信有可能多次调用此接口
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
  @PostMapping("/transaction")
  public Map<String, ?> transactionCallback(
      @RequestHeader("Wechatpay-Serial") String wechatpaySerial,
      @RequestHeader("Wechatpay-Signature") String wechatpaySignature,
      @RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
      @RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
      HttpServletRequest request) throws IOException {
    String body = request.getReader().lines().collect(Collectors.joining());
    // 对请求头进行验签 以确保是微信服务器的调用
    ResponseSignVerifyParams params = new ResponseSignVerifyParams();
    params.setWechatpaySerial(wechatpaySerial);
    params.setWechatpaySignature(wechatpaySignature);
    params.setWechatpayTimestamp(wechatpayTimestamp);
    params.setWechatpayNonce(wechatpayNonce);
    params.setBody(body);
    return wechatApiProvider.callback(TENANT_ID).transactionCallback(params, data -> {
      // 对回调解析的结果进行消费  需要保证消费的幂等性 微信有可能多次调用此接口
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
  @PostMapping("/combine_transaction")
  public Map<String, ?> combineTransactionCallback(
      @RequestHeader("Wechatpay-Serial") String wechatpaySerial,
      @RequestHeader("Wechatpay-Signature") String wechatpaySignature,
      @RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
      @RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
      HttpServletRequest request) throws IOException {
    String body = request.getReader().lines().collect(Collectors.joining());
    // 对请求头进行验签 以确保是微信服务器的调用
    ResponseSignVerifyParams params = new ResponseSignVerifyParams();
    params.setWechatpaySerial(wechatpaySerial);
    params.setWechatpaySignature(wechatpaySignature);
    params.setWechatpayTimestamp(wechatpayTimestamp);
    params.setWechatpayNonce(wechatpayNonce);
    params.setBody(body);
    return wechatApiProvider.callback(TENANT_ID).combineTransactionCallback(params, data -> {
      // 对回调解析的结果进行消费  需要保证消费的幂等性 微信有可能多次调用此接口
    });
  }
}
