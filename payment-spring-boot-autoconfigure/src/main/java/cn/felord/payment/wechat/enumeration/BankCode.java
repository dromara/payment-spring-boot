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
 * The enum Bank code.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum BankCode {
    /**
     * 工商银行
     */
    BK_1002("1002", "工商银行"),
    /**
     * 农业银行
     */
    BK_1005("1005", "农业银行"),
    /**
     * 建设银行
     */
    BK_1003("1003", "建设银行"),
    /**
     * 中国银行
     */
    BK_1026("1026", "中国银行"),
    /**
     * 交通银行
     */
    BK_1020("1020", "交通银行"),
    /**
     * 招商银行
     */
    BK_1001("1001", "招商银行"),
    /**
     * 邮储银行
     */
    BK_1066("1066", "邮储银行"),
    /**
     * 民生银行
     */
    BK_1006("1006", "民生银行"),
    /**
     * 平安银行
     */
    BK_1010("1010", "平安银行"),
    /**
     * 中信银行
     */
    BK_1021("1021", "中信银行"),
    /**
     * 浦发银行
     */
    BK_1004("1004", "浦发银行"),
    /**
     * 兴业银行
     */
    BK_1009("1009", "兴业银行"),
    /**
     * 光大银行
     */
    BK_1022("1022", "光大银行"),
    /**
     * 广发银行
     */
    BK_1027("1027", "广发银行"),
    /**
     * 华夏银行
     */
    BK_1025("1025", "华夏银行"),
    /**
     * 宁波银行
     */
    BK_1056("1056", "宁波银行"),
    /**
     * 北京银行
     */
    BK_4836("4836", "北京银行"),
    /**
     * 上海银行
     */
    BK_1024("1024", "上海银行"),
    /**
     * 南京银行
     */
    BK_1054("1054", "南京银行"),
    /**
     * 长子县融汇村镇银行
     */
    BK_4755("4755", "长子县融汇村镇银行"),
    /**
     * 长沙银行
     */
    BK_4216("4216", "长沙银行"),
    /**
     * 浙江泰隆商业银行
     */
    BK_4051("4051", "浙江泰隆商业银行"),
    /**
     * 中原银行
     */
    BK_4753("4753", "中原银行"),
    /**
     * 企业银行（中国）
     */
    BK_4761("4761", "企业银行（中国）"),
    /**
     * 顺德农商银行
     */
    BK_4036("4036", "顺德农商银行"),
    /**
     * 衡水银行
     */
    BK_4752("4752", "衡水银行"),
    /**
     * 长治银行
     */
    BK_4756("4756", "长治银行"),
    /**
     * 大同银行
     */
    BK_4767("4767", "大同银行"),
    /**
     * 河南省农村信用社
     */
    BK_4115("4115", "河南省农村信用社"),
    /**
     * 宁夏黄河农村商业银行
     */
    BK_4150("4150", "宁夏黄河农村商业银行"),
    /**
     * 山西省农村信用社
     */
    BK_4156("4156", "山西省农村信用社"),
    /**
     * 安徽省农村信用社
     */
    BK_4166("4166", "安徽省农村信用社"),
    /**
     * 甘肃省农村信用社
     */
    BK_4157("4157", "甘肃省农村信用社"),
    /**
     * 天津农村商业银行
     */
    BK_4153("4153", "天津农村商业银行"),
    /**
     * 广西壮族自治区农村信用社
     */
    BK_4113("4113", "广西壮族自治区农村信用社"),
    /**
     * 陕西省农村信用社
     */
    BK_4108("4108", "陕西省农村信用社"),
    /**
     * 深圳农村商业银行
     */
    BK_4076("4076", "深圳农村商业银行"),
    /**
     * 宁波鄞州农村商业银行
     */
    BK_4052("4052", "宁波鄞州农村商业银行"),
    /**
     * 浙江省农村信用社联合社
     */
    BK_4764("4764", "浙江省农村信用社联合社"),
    /**
     * 江苏省农村信用社联合社
     */
    BK_4217("4217", "江苏省农村信用社联合社"),
    /**
     * 江苏紫金农村商业银行股份有限公司
     */
    BK_4072("4072", "江苏紫金农村商业银行"),
    /**
     * 北京中关村银行股份有限公司
     */
    BK_4769("4769", "北京中关村银行"),
    /**
     * 星展银行（中国）有限公司
     */
    BK_4778("4778", "星展银行（中国）"),
    /**
     * 枣庄银行股份有限公司
     */
    BK_4766("4766", "枣庄银行"),
    /**
     * 海口联合农村商业银行股份有限公司
     */
    BK_4758("4758", "海口联合农村商业银行"),
    /**
     * 南洋商业银行（中国）有限公司
     */
    BK_4763("4763", "南洋商业银行（中国）");


    private final String code;
    private final String bankName;

    BankCode(String code, String bankName) {
        this.code = code;
        this.bankName = bankName;
    }

    public String code() {
        return this.code;
    }

    public String bankName() {
        return this.bankName;
    }
}
