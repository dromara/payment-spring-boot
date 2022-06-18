/*
 *  Copyright 2019-2021 felord.cn
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

package cn.felord.payment.wechat.enumeration;

/**
 * 营业执照/登记证书、经营者/法人的证件的主体类型
 *
 * @author felord.cn
 * @see <a href="https://kf.qq.com/faq/180910IBZVnQ180910naQ77b.html">主体类型指引</a>
 * @since 1.0.14.RELEASE
 */
public enum SubjectType {
    /**
     * 个体户
     * <p>
     * 营业执照上的主体类型一般为个体户、个体工商户、个体经营；
     */
    SUBJECT_TYPE_INDIVIDUAL,
    /**
     * 企业
     * <p>
     * 营业执照上的主体类型一般为有限公司、有限责任公司；
     */
    SUBJECT_TYPE_ENTERPRISE,
    /**
     * 政府机关
     * <p>
     * 包括各级、各类政府机关，如机关党委、税务、民政、人社、工商、商务、市监等；
     */
    SUBJECT_TYPE_GOVERNMENT,
    /**
     * 事业单位
     * <p>
     * 包括国内各类事业单位，如：医疗、教育、学校等单位；
     */
    SUBJECT_TYPE_INSTITUTIONS,
    /**
     * 社会组织
     * <p>
     * 包括社会团体、民办非企业、基金会、基层群众性自治组织、农村集体经济组织等组织
     */
    SUBJECT_TYPE_OTHERS
}
