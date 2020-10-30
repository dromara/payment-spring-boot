package com.enongm.dianji.payment.wechat.v3.filter;


import com.enongm.dianji.payment.autoconfigure.WechatPayProperties;
import com.enongm.dianji.payment.wechat.v3.PayFilter;
import com.enongm.dianji.payment.wechat.v3.PayFilterChain;
import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;
import com.enongm.dianji.payment.wechat.v3.model.WechatMetaBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

/**
 * The type Body merge filter.
 * 2
 *
 * @author Dax
 * @since 11 :13
 */
public class BodyMergeFilter implements PayFilter {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final WechatMetaBean wechatMetaBean;

    static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public BodyMergeFilter(WechatMetaBean wechatMetaBean) {
        this.wechatMetaBean = wechatMetaBean;
    }

    @Override
    public void doFilter(WechatPayRequest request, PayFilterChain chain) {
        mergeAppIdAndMchIdIntoBody(request);
        chain.doChain(request);
    }


    /**
     * 将直连商户申请的公众号或移动应用appid 、商户号mchid 放入请求体
     */
    @SneakyThrows
    private void mergeAppIdAndMchIdIntoBody(WechatPayRequest request) {

        String requestBody = request.getBody();
        if (StringUtils.hasText(requestBody)) {
            WechatPayProperties wechatPayProperties = wechatMetaBean.getWechatPayProperties();
            String appId = wechatPayProperties.getV3().getAppId();
            String mchId = wechatPayProperties.getV3().getMchId();

            ObjectNode jsonNodes = MAPPER.readValue(requestBody, ObjectNode.class);
            JsonNode notify = jsonNodes.get("notify_url");
            String notifyUrl = wechatPayProperties.getV3().getDomain()
                    .concat(notify.asText());
            // ^https?://([^\\s/?#\\[\\]\\@]+\\@)?([^\\s/?#\\@:]+)(?::\\d{2,5})?([^\\s?#\\[\\]]*)$
            jsonNodes.put("notify_url", notifyUrl);
            jsonNodes.put("appid", appId);
            jsonNodes.put("mchid", mchId);

            request.body(MAPPER.writeValueAsString(jsonNodes));
        }
    }


}
