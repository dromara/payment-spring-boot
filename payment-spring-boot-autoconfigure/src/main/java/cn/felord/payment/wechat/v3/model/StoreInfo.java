package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 17:06
 */
@Data
public class StoreInfo {
    /**
     * 门店编号
     */
    private String id;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 地区编码
     */
    private String areaCode;
    /**
     * 详细地址
     */
    private String address;
}
