package com.enation.app.shop.front.tag.goods;

import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
import com.enation.app.shop.core.goods.model.Goods;
import com.enation.app.shop.core.goods.service.IGoodsManager;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 商品相册标签
 * @author kingapex
 *2013-8-1上午10:59:02
 */
@Component
@Scope("prototype")
public class GoodsViewCountTag extends BaseFreeMarkerTag {
	
	@Autowired
	private IGoodsGalleryManager goodsGalleryManager;

	@Autowired
	private IGoodsManager goodsManager;
	
	/**
	 * 大家都在看商品信息
	 * @param
	 * @return 商品相册 ，类型：List<Goods>
	 * {@link Goods}
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		//查询商品信息
		List<Goods>  goodsList = this.goodsManager.getViewCountList();

		return goodsList;
	}


}
