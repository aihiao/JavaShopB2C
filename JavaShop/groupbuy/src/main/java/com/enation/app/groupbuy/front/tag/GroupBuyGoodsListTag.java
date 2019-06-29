package com.enation.app.groupbuy.front.tag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.groupbuy.core.service.IGroupGoodsTagManager;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;
/**
 * 
 * @ClassName: GroupBuyGoodsListTag 
 * @Description: 团购商品列表标签 
 * @author TALON 
 * @date 2015-7-31 上午10:48:39 
 *
 */
@Component
public class GroupBuyGoodsListTag extends BaseFreeMarkerTag{
	
	@Autowired
	private IGroupGoodsTagManager groupGoodsTagManager;
	
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		String catid =(String) params.get("catid");
		String tagid = (String)params.get("tagid");
		String goodsnum = (String)params.get("goodsnum");
	 
		if(catid == null || catid.equals("")){
			String uri  = ThreadContextHolder.getHttpRequest().getServletPath();
			//catid = UrlUtils.getParamStringValue(uri,"cat");
		}
		
		List goodsList  = groupGoodsTagManager.listGoods(catid, tagid, goodsnum);
		 
		return goodsList;
	}

}
