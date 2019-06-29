package com.enation.app.shop.front.tag.goods.search;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.component.gallery.model.GoodsGallery;
import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
import com.enation.app.shop.core.goods.model.Goods;
import com.enation.app.shop.core.goods.model.GoodsVo;
import com.enation.app.shop.core.goods.service.IGoodsManager;
import com.enation.app.shop.core.goods.utils.UrlUtils;
import com.enation.app.shop.core.member.model.Favorite;
import com.enation.app.shop.core.member.service.IFavoriteManager;
import com.enation.eop.sdk.context.UserConext;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import com.enation.framework.util.RequestUtil;
import com.enation.framework.util.StringUtil;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 搜索url标签
 * 根据输入的名称参数，得到排除某参数后的url字串，不带.html	
 * @author yanpengcheng
 *2018-8-30下午2:35:03
 */
@Component
@Scope("prototype")
public class SeachCollectionTag extends BaseFreeMarkerTag {

	@Autowired
	private IFavoriteManager favoriteManager;

	/**
	 * 判断商品是否已经收藏
	 * @param
	 * @return
	 * {@link GoodsGallery}
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		HttpServletRequest request  = this.getRequest();
		Integer goods_id = StringUtil.toInt(params.get("goodsid"), false);
		Member member = UserConext.getCurrentMember();
		if(goods_id==null||goods_id==0){
			goods_id= this.getGoodsId();
		}
		if(member!=null){
			//查询商品信息
			Favorite galleryList = this.favoriteManager.get(goods_id,member.getMember_id());
			if(galleryList == null){
				return false;
			}
			return true;
		}
		return false;
	}

	private Integer getGoodsId(){
		HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
		String url = RequestUtil.getRequestUrl(httpRequest);
		String goods_id = this.paseGoodsId(url);

		return StringUtil.toInt(goods_id,false);
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
