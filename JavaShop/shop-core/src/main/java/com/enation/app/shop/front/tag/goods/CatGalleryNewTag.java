package com.enation.app.shop.front.tag.goods;

import com.enation.app.shop.component.gallery.model.GoodsGallery;
import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
import com.enation.app.shop.core.goods.model.Cat;
import com.enation.app.shop.core.goods.model.Goods;
import com.enation.app.shop.core.goods.model.GoodsVo;
import com.enation.app.shop.core.goods.service.IGoodsCatManager;
import com.enation.app.shop.core.goods.service.IGoodsManager;
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
 * 商品相册标签
 * @author kingapex
 *2013-8-1上午10:59:02
 */
@Component
@Scope("prototype")
public class CatGalleryNewTag extends BaseFreeMarkerTag {
	
	@Autowired
	private IGoodsCatManager goodsCatManager;

	@Autowired
	private IGoodsManager goodsManager;
	
	/**
	 * 商品相册标签
	 * 特殊说明：调用商品属性标签前，必须先调用商品基本信息标签
	 * @param
	 * @return 商品相册 ，类型：List<GoodsGallery>
	 * {@link GoodsGallery}
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		HttpServletRequest request  = this.getRequest();
		Integer goods_id = StringUtil.toInt(params.get("goodsid"), false);

		if(goods_id==null||goods_id==0){
			goods_id= this.getGoodsId();
		}
		List<Cat> galleryList = this.goodsCatManager.queryCatParent(goods_id);
		//查询商品信息
		if (galleryList!=null && galleryList.size()>0){
			for (Cat catNew : galleryList){
				List<Goods>  goodsList = goodsManager.getGoodsCatList(catNew.getCat_id());
				catNew.setGoodsList(goodsList);
			}
		}
		return galleryList;
	}

	private Integer getGoodsId(){
		HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
		String url = RequestUtil.getRequestUrl(httpRequest);
		String goods_id = this.paseGoodsId(url);

		return StringUtil.toInt(goods_id,false);
	}

	private  static String  paseGoodsId(String url){
		String pattern = "(=)(\\d+)";
		String value = null;
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);
		if (m.find()) {
			value=m.group(2);
		}
		return value;
	}
}
