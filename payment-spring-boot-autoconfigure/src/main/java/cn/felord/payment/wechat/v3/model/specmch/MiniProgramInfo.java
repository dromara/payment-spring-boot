package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class MiniProgramInfo{
	private String miniProgramAppid;
	private String miniProgramSubAppid;
	private List<String> miniProgramPics;
}