package com.enation.app.shop.front.tag.goods;

import com.enation.app.shop.component.gallery.model.GoodsGallery;
import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
import com.enation.app.shop.core.goods.model.Goods;
import com.enation.app.shop.core.goods.model.GoodsVo;
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
public class GoodsGalleryBestSellerTag extends BaseFreeMarkerTag {
	
	@Autowired
	private IGoodsGalleryManager goodsGalleryManager;

	@Autowired
	private IGoodsManager goodsManager;
	
	/**
	 * 查询24小时热卖商品信息
	 * @param
	 * @return 商品相册 ，类型：List<Goods>
	 * {@link Goods}
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		//查询商品信息
		List<Goods>  goodsList = this.goodsManager.getBestSellerLis();

		return goodsList;
	}


}
