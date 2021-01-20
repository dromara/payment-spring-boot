package cn.felord.payment.wechat.v2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author felord.cn
 * @since 1.0.5.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RedpackModel extends  BaseModel {

   private String wxappid;
   private String mchId;
   private String mchBillno;
   private String sendName;
   private String reOpenid;
   private String totalAmount;
   private String totalNum;
   private String wishing;
   private String clientIp;
   private String actName;
   private String remark;
   private String sceneId;
   private String riskInfo;

}
