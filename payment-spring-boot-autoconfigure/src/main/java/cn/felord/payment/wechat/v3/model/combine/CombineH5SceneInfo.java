package cn.felord.payment.wechat.v3.model.combine;

import cn.felord.payment.wechat.v3.model.H5Info;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CombineH5SceneInfo extends CombineSceneInfo {
  private H5Info h5Info;
}
