package com.enation.app.shop.front.tag.goods;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.component.gallery.model.GoodsGallery;
import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
import com.enation.app.shop.core.goods.model.Goods;
import com.enation.app.shop.core.goods.model.GoodsVo;
import com.enation.app.shop.core.goods.service.IGoodsManager;
import com.enation.app.shop.core.member.model.Footprint;
import com.enation.app.shop.core.member.service.IFootprintManager;
import com.enation.eop.SystemSetting;
import com.enation.eop.sdk.context.UserConext;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.RequestUtil;
import com.enation.framework.util.StringUtil;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
public class GoodsGalleryNewTag extends BaseFreeMarkerTag {
	
	@Autowired
	private IGoodsGalleryManager goodsGalleryManager;

	@Autowired
	private IGoodsManager goodsManager;

	@Autowired
	private IFootprintManager footprintManager;
	
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
		List<GoodsGallery> galleryList = this.goodsGalleryManager.list(goods_id);
		//查询商品信息
		Goods  goods = this.goodsManager.getGoods(goods_id);

		//会员足迹
		Member member = UserConext.getCurrentMember();
		if(member!=null){
            //如果此商品从在则只更新时间
			Footprint footprintNew = this.footprintManager.get(goods.getGoods_id(),member.getMember_id());
			if(footprintNew==null){
				Footprint footprint = new Footprint();
				footprint.setGoods_id(goods.getGoods_id());
				footprint.setMember_id(member.getMember_id());
				long timeNew = DateUtil.getDateline();
				footprint.setIs_default("0");
				footprint.setFootprint_time(timeNew);
				this.footprintManager.add(footprint);
			}else{
				this.footprintManager.update(footprintNew.getFootprint_id());
			}
		}

		GoodsVo goodsList = new  GoodsVo(goods);
		Map map = new HashMap();
		map.put("goodsList",goodsList);
		map.put("galleryList",galleryList);
		return map;
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
