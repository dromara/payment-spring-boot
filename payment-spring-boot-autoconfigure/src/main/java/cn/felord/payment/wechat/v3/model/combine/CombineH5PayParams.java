
package cn.felord.payment.wechat.v3.model.combine;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 合单支付 H5支付参数.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombineH5PayParams {

    /**
     * 合单商户appid，必填
     */
    private String combineAppid;
    /**
     * 合单商户号，必填
     */
    private String combineMchid;
    /**
     * 合单商户订单号，必填，商户侧需要保证同一商户下唯一
     */
    private String combineOutTradeNo;
    /**
     * 合单支付者信息，选填
     */
    private CombinePayerInfo combinePayerInfo;
    /**
     * 通知地址，必填，接收微信支付异步通知回调地址，通知url必须为直接可访问的URL，不能携带参数。
     * <p>
     * <strong>合单支付需要独立的通知地址。</strong>
     */
    private String notifyUrl;
    /**
     * 合单支付场景信息描述，选填
     */
    private CombineH5SceneInfo sceneInfo;
    /**
     * 子单信息，必填，最多50单
     */
    private List<SubOrder> subOrders;
    /**
     * 交易起始时间，选填
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+8")
    private OffsetDateTime timeStart;
    /**
     * 交易结束时间，选填
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+8")
    private OffsetDateTime timeExpire;

}
