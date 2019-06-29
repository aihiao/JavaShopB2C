package com.enation.app.groupbuy.core.action.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.enation.app.groupbuy.core.service.IGroupGoodsTagManager;
import com.enation.framework.action.GridController;
import com.enation.framework.action.GridJsonResult;
import com.enation.framework.util.JsonResultUtil;

/**
 * @ClassName: GroupbuyTagAction 
 * @Description: 团购标签Action 
 * @author kingapex 
 * @date 2015-7-31 上午1:37:43 
 *
 */
@Controller
@RequestMapping("/shop/admin/group-buy-tag")
public class GroupbuyTagController extends GridController{
	
	@Autowired
	private IGroupGoodsTagManager groupGoodsTagManager;
	
	/**
	 * 商品标签列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView view=this.getGridModelAndView();
		view.setViewName("/groupbuy/tags/taglist");
		return view;
	}
	/**
	 * 商品标签列表json
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list-json")
	public GridJsonResult listJson(){
		this.webpage=groupGoodsTagManager.list(this.getPage(), this.getPageSize());
		return JsonResultUtil.getGridJson(this.webpage);
	}
}
