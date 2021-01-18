package cn.felord.payment.wechat.v3.model.busifavor;

import cn.felord.payment.wechat.enumeration.CouponBgColor;
import cn.felord.payment.wechat.v3.WechatMarketingFavorApi;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商家券样式信息.
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class DisplayPatternInfo {

    /**
     * 背景颜色
     */
    private CouponBgColor backgroundColor;
    /**
     * 商户logo
     * <ol>
     *     <li>商户logo大小需为120像素*120像素</li>
     *     <li>支持JPG、JPEG、PNG格式，且图片小于1M</li>
     * </ol>
     * 仅支持通过 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/marketing/chapter3_1.shtml">图片上传API</a> 接口获取的图片URL地址。
     *
     * @see WechatMarketingFavorApi#marketingImageUpload(MultipartFile)
     */
    private String merchantLogoUrl;
    /**
     * 券详情图片
     * <ol>
     *     <li>需为850像素*350像素</li>
     *     <li>图片大小不超过2M</li>
     *     <li>支持JPG/PNG格式</li>
     * </ol>
     * 仅支持通过 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/marketing/chapter3_1.shtml">图片上传API</a> 接口获取的图片URL地址。
     *
     * @see WechatMarketingFavorApi#marketingImageUpload(MultipartFile)
     */
    private String couponImageUrl;
    /**
     * 使用须知
     * <p>
     * 用于说明详细的活动规则，会展示在代金券详情页。
     * <p>
     * 示例值：xxx门店可用
     */
    private String description;
    /**
     * 商户名称,字数上限为16个
     */
    private String merchantName;
}