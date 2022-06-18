package cn.felord.payment.wechat.v3.model.smartguide;

import lombok.Data;

/**
 * 服务人员注册API参数
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
@Data
public class SmartGuidesParams {

    private String corpid;
    private Integer storeId;
    private String userid;
    private String name;
    private String mobile;
    private String qrCode;
    private String avatar;
    private String groupQrcode;
}