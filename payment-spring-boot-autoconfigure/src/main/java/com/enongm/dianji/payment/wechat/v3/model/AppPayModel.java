package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Dax
 * @since 17:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppPayModel extends BaseModel {
    private Amount amount;
    private Detail detail;
    private SceneInfo sceneInfo;

}
