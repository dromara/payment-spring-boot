package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 17:03
 */
@Data
public class H5Info {
    /**
     * 场景类型
     */
    private String type;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 网站URL
     */
    private String appUrl;
    /**
     * IOS 平台 BundleID
     */
    private String bundleId;
    /**
     * Android 平台 PackageName
     */
    private String packageName;
}
