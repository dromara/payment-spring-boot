/*
 *
 *  Copyright 2019-2020 felord.cn
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
 *
 */
package cn.felord.payment.wechat.enumeration;


/**
 * The enum We chat server domain.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum WeChatServer {
    /**
     * 中国
     *
     * @since 1.0.0.RELEASE
     */
    CHINA("https://api.mch.weixin.qq.com"),
    /**
     * 中国国内(备用域名)
     *
     * @since 1.0.0.RELEASE
     */
    CHINA2("https://api2.mch.weixin.qq.com"),
    /**
     * 香港
     *
     * @since 1.0.0.RELEASE
     */
    HK("https://apihk.mch.weixin.qq.com"),
    /**
     * 美国
     *
     * @since 1.0.0.RELEASE
     */
    US("https://apius.mch.weixin.qq.com"),
    /**
     * 获取公钥
     *
     * @since 1.0.0.RELEASE
     */
    FRAUD("https://fraud.mch.weixin.qq.com"),
    /**
     * 活动
     *
     * @since 1.0.0.RELEASE
     */
    ACTION("https://action.weixin.qq.com");


    /**
     * 域名
     *
     * @since 1.0.0.RELEASE
     */
    private final String domain;

    WeChatServer(String domain) {
        this.domain = domain;
    }

    /**
     * Gets type.
     *
     * @return the type
     * @since 1.0.0.RELEASE
     */
    public String domain() {
        return domain;
    }

    @Override
    public String toString() {
        return domain;
    }
}
