package com.enation.app.groupbuy.core.action.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.enation.app.groupbuy.core.model.GroupBuyCat;
import com.enation.app.groupbuy.core.service.IGroupBuyCatManager;
import com.enation.framework.action.GridController;
import com.enation.framework.action.GridJsonResult;
import com.enation.framework.action.JsonResult;
import com.enation.framework.util.JsonResultUtil;

/**
 * 
 * @ClassName: GroupBuyCatAction 
 * @Description: 团购分类Action 
 * @author TALON 
 * @date 2015-7-31 上午1:33:16 
 *
 */
@Controller
@RequestMapping("/shop/admin/group-buy-cat")
public class GroupBuyCatController extends GridController {
	
	@Autowired
	private IGroupBuyCatManager groupBuyCatManager;
	/**
	 * 跳转至团购分类列表	
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView view=getGridModelAndView();
		view.setViewName("/groupbuy/cat/cat_list");
		return view;
	}
	
	/**
	 * 获取团购分类分页列表Json
	 * @param webpage 团购分页列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list-json")
	public GridJsonResult listJson(){
		this.webpage=this.groupBuyCatManager.list(this.getPage(),this.getPageSize());
		return JsonResultUtil.getGridJson(this.webpage);
	}
	
	/**
	 * 跳转至团购分类添加页
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(){
		return "/groupbuy/cat/cat_add";
	}
	
	/**
	 * 保存添加团购分类
	 * @param groupBuyCat 团购分类
	 * @param cat_name 分类名称
	 * @param cat_order 分类排序
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save-add")
	public JsonResult saveAdd(Integer cat_order,String  cat_name){
		try {
			GroupBuyCat groupBuyCat = new GroupBuyCat();
			groupBuyCat.setCat_name(cat_name);
			groupBuyCat.setCat_order(cat_order);
			this.groupBuyCatManager.add(groupBuyCat);
			return JsonResultUtil.getSuccessJson("添加成功");
		} catch (Exception e) {
			this.logger.error("添加失败", e);
			return JsonResultUtil.getErrorJson("添加失败"+e.getMessage());
		}
		
	}
	/**
	 * 跳转至团购分类修改页
	 * @param groupBuyCat 团购分类
	 * @param catid 团购分类数组
	 * @return
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(Integer[] catid){
		ModelAndView view =new ModelAndView();
		view.addObject("groupBuyCat", groupBuyCatManager.get(catid[0]));
		view.setViewName("/groupbuy/cat/cat_edit");
		return view;
	}
	
	/**
	 * @Title: saveEdit
	 * @Description:  保存修改团购分类
	 * @param catid 团购分类数组 
	 * @param cat_name 分类名称
	 * @param cat_order 分类排序
	 * @return json 1为成功.0为失败.
	 */
	@ResponseBody
	@RequestMapping(value="/save-edit")
	public JsonResult saveEdit(Integer cat_order,String  cat_name,Integer[] catid){

		try {
			GroupBuyCat groupBuyCat = new GroupBuyCat();
			groupBuyCat.setCatid(catid[0]);
			groupBuyCat.setCat_name(cat_name);
			groupBuyCat.setCat_order(cat_order);
			this.groupBuyCatManager.update(groupBuyCat);
			return JsonResultUtil.getSuccessJson("修改成功");
		} catch (Exception e) {
			this.logger.error("修改失败", e);
			return JsonResultUtil.getErrorJson("修改失败"+e.getMessage());
		}
		
	}
	/**
	 * 批量删除团购分类
	 * @param catid 团购分类数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/batch-delete")
	public JsonResult batchDelete(Integer[] catid){
		try {
			this.groupBuyCatManager.delete(catid);
			return JsonResultUtil.getSuccessJson("删除改成功");
		} catch (Exception e) {
			this.logger.error("删除失败", e);
			return JsonResultUtil.getErrorJson("删除失败"+e.getMessage());
		}
	}

}
