package com.enation.app.groupbuy.front.tag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.enation.app.groupbuy.core.service.IGroupBuyCatManager;
import com.enation.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 
 * @ClassName: GroupBuyCatListTag 
 * @Description: 团购分类列表标签 
 * @author TALON 
 * @date 2015-7-31 上午10:48:20 
 *
 */
@Component
@Scope("prototype")
public class GroupBuyCatListTag extends BaseFreeMarkerTag {
	
	@Autowired
	private IGroupBuyCatManager groupBuyCatManager;
	
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		return groupBuyCatManager.listAll();
	}
}

