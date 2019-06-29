package com.enation.app.groupbuy.front.tag;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.enation.app.groupbuy.core.model.GroupBuy;
import com.enation.app.groupbuy.core.service.IGroupBuyManager;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import com.enation.framework.util.RequestUtil;

import freemarker.template.TemplateModelException;

/**
 * 
 * @ClassName: GroupBuyTag 
 * @Description: 团购标签 
 * @author TALON 
 * @date 2015-7-31 上午10:49:12 
 *
 */
@Component
@Scope("prototype")
public class GroupBuyTag extends BaseFreeMarkerTag {
	
	@Autowired
	private IGroupBuyManager groupBuyManager;
	
	/**
	 * @param gbid 团购Id
	 * @return 团购实体GroupBuy
	 * {@link GroupBuy}
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
	/*  whj 2015-05-26  暂时取消商品is_groupbuy（是否是团购商品的判断），因为商品参加团购is_groupbuy都是1，没有判断的必要吧。
	 * 否则商品详细在IF处报错。
		if(params.get("is_groupbuy").toString().equals("0")){
			return "";
		}else{
			Integer goodsid =(Integer) params.get("goodsid");
			GroupBuy  groupBuy=groupBuyManager.getBuyGoodsId(goodsid);
			return groupBuy;
		}
	*/	
		
		Integer goodsid =(Integer) params.get("goodsid");
		GroupBuy  groupBuy=groupBuyManager.getBuyGoodsId(goodsid);
		return groupBuy;
		
	}
	private Integer getGoodsId(){
		HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
		String url = RequestUtil.getRequestUrl(httpRequest);
		String goods_id = this.paseGoodsId(url);
		
		return Integer.valueOf(goods_id);
	}

	private  static String  paseGoodsId(String url){
		String pattern = "(-)(\\d+)";
		String value = null;
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);
		if (m.find()) {
			value=m.group(2);
		}
		return value;
	}
}
