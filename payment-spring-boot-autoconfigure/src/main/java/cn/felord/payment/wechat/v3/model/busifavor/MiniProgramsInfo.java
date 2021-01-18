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
package cn.felord.payment.wechat.v3.model.busifavor;

import lombok.Data;

/**
 * 商家券核销规则-自定义入口-小程序入口
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class MiniProgramsInfo{

	/**
	 * 商家小程序appid
	 * <p>
	 * 商家小程序appid要与归属商户号有M-A or M-m-suba关系。
	 */
	private String miniProgramsAppid;
	/**
	 * 商家小程序path
	 */
	private String miniProgramsPath;
	/**
	 * 入口文案，字数上限为5个，一个中文汉字/英文字母/数字均占用一个字数。
	 */
	private String guidingWords;
	/**
	 * 小程序入口引导文案，用户自定义字段。字数上限为6个，一个中文汉字/英文字母/数字均占用一个字数。
	 */
	private String entranceWords;
}