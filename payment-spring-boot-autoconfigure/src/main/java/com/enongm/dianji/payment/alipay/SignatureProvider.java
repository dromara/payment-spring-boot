package com.enongm.dianji.payment.alipay;

import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * The type Ali pay core.
 */
public class SignatureProvider {

    /**
     * 生成签名结果
     *
     * @param params   要签名的数组
     * @param key      签名密钥
     * @param signType 签名类型
     * @return 签名结果字符串
     */
    public static String requestSign(Map<String, String> params, String key, String signType) {
        String preStr = createLinkString(params);
    /*    if (SignType.MD5.getType().equals(signType)) {
            return SecureUtil.md5(preStr.concat(key));
        }*/
        return null;
    }

    /**
     * 生成要请求给支付宝的参数数组
     *
     * @param params   请求前的参数数组
     * @param key      商户的私钥
     * @param signType 签名类型
     * @return 要请求的参数数组 map
     */
    public static Map<String, String> buildRequestPara(Map<String, String> params, String key, String signType) {
        // 除去数组中的空值和签名参数
        Map<String, String> tempMap = paraFilter(params);
        // 生成签名结果
        String mySign = requestSign(params, key, signType);

        // 签名结果与签名方式加入请求提交参数组中
        tempMap.put("sign", mySign);
        tempMap.put("sign_type", signType);

        return tempMap;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组 map
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>(sArray.size());
        if (CollectionUtils.isEmpty(sArray)) {
            return Collections.emptyMap();
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || "".equals(value) || "sign".equalsIgnoreCase(key)
                    || "sign_type".equalsIgnoreCase(key)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 参数自然排序拼接
     *
     * @param params {'k':'b','e':'f','c':'d'}  -> {'c':'d','e':'f','k':'b'}
     * @return 拼接后字符串  c=d&e=f&k=b
     */
    public static String createLinkString(Map<String, String> params) {
        TreeMap<String, String> treeMap = new TreeMap<>(params);

        Set<String> keySet = treeMap.keySet();
        StringBuilder content = new StringBuilder();
        for (String key : keySet) {
            content.append(key).append("=")
                    .append(treeMap.get(key))
                    .append("&");
        }
        return content.substring(0, content.length() - 1);
    }
}