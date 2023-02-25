/*
 *  Copyright 2019-2022 felord.cn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 其它能力-媒体上传
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class WechatMediaApi extends AbstractApi {

    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatMediaApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 图片上传API
     *
     * @param file the file
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> mediaImageUpload(MultipartFile file) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MERCHANT_MEDIA_IMG, file)
                .function(this::uploadFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 视频上传API
     *
     * @param file the file
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> mediaVideoUpload(MultipartFile file) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MERCHANT_MEDIA_VIDEO, file)
                .function(this::uploadFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    @SneakyThrows
    private RequestEntity<?> uploadFunction(WechatPayV3Type type, MultipartFile file) {

        Map<String, Object> meta = new LinkedHashMap<>(2);

        String originalFilename = file.getOriginalFilename();
        String filename = StringUtils.hasText(originalFilename) ? originalFilename : file.getName();
        meta.put("filename", filename);

        byte[] digest = SHA256.Digest.getInstance("SHA-256").digest(file.getBytes());
        meta.put("sha256", Hex.toHexString(digest));
        MultiValueMap<Object, Object> body = new LinkedMultiValueMap<>();
        body.add("meta", meta);
        body.add("file", file.getResource());
        // 签名
        String metaStr = this.getMapper().writeValueAsString(meta);

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return RequestEntity.post(uri)
                .header("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Meta-Info", metaStr)
                .header("Pay-TenantId", tenantId())
                .body(body);
    }

    /**
     * 营销图片上传API
     * <p>
     * 媒体图片只支持JPG、BMP、PNG格式，文件大小不能超过2M。
     * <p>
     * 通过本接口上传图片后可获得图片url地址。图片url可在微信支付营销相关的API使用，包括商家券、代金券、支付有礼等。
     *
     * @param file the file
     * @return the wechat response entity
     * @since 1.0.17.RELEASE
     */
    public WechatResponseEntity<ObjectNode> marketingImageUpload(MultipartFile file) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_IMAGE_UPLOAD, file)
                .function(this::uploadFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
