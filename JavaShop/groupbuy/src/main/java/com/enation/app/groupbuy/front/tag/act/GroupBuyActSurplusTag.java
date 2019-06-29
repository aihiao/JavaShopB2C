package com.enation.app.groupbuy.front.tag.act;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.enation.framework.taglib.BaseFreeMarkerTag;
import com.enation.framework.util.DateUtil;

import freemarker.template.TemplateModelException;

/**
 * 
 * @ClassName: GroupBuyActSurplusTag 
 * @Description: 团购剩余时间标签 
 * @author TALON 
 * @date 2015-7-31 上午10:47:56 
 *
 */
@Component
public class GroupBuyActSurplusTag extends  BaseFreeMarkerTag{

	@Override
	protected Object exec(Map params) throws TemplateModelException {
						
		//获取日期
		long now  = DateUtil.getDateline();
		long end  =Long.parseLong(params.get("end_time").toString());
		
		//运算的程序开始
		long cha= (end-now);
		
//		SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
//		SimpleDateFormat hoursFormatter = new SimpleDateFormat("HH");
//		SimpleDateFormat minFormatter = new SimpleDateFormat("mm");
//		SimpleDateFormat secFormatter = new SimpleDateFormat("ss");
//
		Map result=new HashMap();
//		result.put("day", dayFormatter.format(cha));
//		result.put("hours", hoursFormatter.format(cha));
//		result.put("minute", minFormatter.format(cha));
//		result.put("seconds", secFormatter.format(cha));
		result.put("cha", cha);
		return result;
	}
	
}
