package com.enation.app.groupbuy.core.action.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.enation.app.groupbuy.core.service.IGroupBuyManager;
import com.enation.app.shop.core.goods.service.IGoodsTagManager;
import com.enation.framework.action.GridController;
import com.enation.framework.action.GridJsonResult;
import com.enation.framework.util.JsonResultUtil;
/**
 * 
 * @ClassName: GroupBuyActTagAction 
 * @Description: 团购活动标签Action 
 * @author TALON 
 * @date 2015-7-31 上午1:24:20 
 *
 */

@Controller
@RequestMapping("/shop/admin/group-buy-act-tag")
public class GroupBuyActTagController extends GridController{

	@Autowired
	private IGroupBuyManager groupBuyManager;

	@Autowired
	private IGoodsTagManager goodsTagManager;

	/**
	 * 
	 * @Title: list
	 * @Description: 跳转至团购活动标签列表
	 * @return String    团购活动标签列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Integer actid,Integer tagid){
		ModelAndView view =this.getGridModelAndView();
		view.addObject("actid", actid);
		view.addObject("tagid", tagid);
		view.setViewName("/groupbuy/tag/tag_goods_list");
		return view;
	}
	/**
	 * 
	 * @Title: listJson
	 * @Description: 商品标签    商品列表json
	 * @param catid 商品分类Id,Integer
	 * @param tagid 标签Id,Integer
	 * @return 标签商品列表JSON
	 */
	@ResponseBody
	@RequestMapping(value="/list-json")
	public GridJsonResult listJson(Integer catid,Integer tagid){
		if (catid == null || catid.intValue() == 0) {
			this.webpage = goodsTagManager.getGoodsList(tagid, this.getPage(), this.getPageSize());
		} else {
			this.webpage = goodsTagManager.getGoodsList(tagid, catid.intValue(), this.getPage(), this.getPageSize());
		}
		return JsonResultUtil.getGridJson(webpage);
	}
	/**
	 * 跳转至标签商品选择列表
	 * @return
	 */
	@RequestMapping(value="/search")
	public ModelAndView search(Integer actid,Integer tagid){
		ModelAndView view =this.getGridModelAndView();
		view.addObject("actid", actid);
		view.addObject("tagid", tagid);
		view.setViewName("/groupbuy/tag/search_list");
		return view;
	}
	/**
	 * 
	 * @Title: goodsListJson
	 * @Description: 获取团购标签商品列表JSON
	 * @param actid 团购活动ID
	 * @param webpage 团购标签商品分页列表
	 * @return 团购标签商品列表JSON
	 */
	@ResponseBody
	@RequestMapping(value="/goods-list-json")
	public GridJsonResult goodsListJson(Integer actid) {

		this.webpage=this.groupBuyManager.listByActId(this.getPage(), this.getPageSize(), actid, 1);
		return JsonResultUtil.getGridJson(this.webpage);
	}

}
