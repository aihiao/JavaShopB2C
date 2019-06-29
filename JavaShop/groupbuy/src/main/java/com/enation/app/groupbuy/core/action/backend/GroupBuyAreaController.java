package com.enation.app.groupbuy.core.action.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.enation.app.groupbuy.core.model.GroupBuyArea;
import com.enation.app.groupbuy.core.service.IGroupBuyAreaManager;
import com.enation.framework.action.GridController;
import com.enation.framework.action.GridJsonResult;
import com.enation.framework.action.JsonResult;
import com.enation.framework.util.JsonResultUtil;

/**
 * 
 * @ClassName: GroupBuyAreaAction 
 * @Description: 团购地区管理action 
 * @author TALON 
 * @date 2015-7-31 上午1:30:18 
 *
 */
@Controller
@RequestMapping("/shop/admin/group-buy-area")
public class GroupBuyAreaController extends GridController {
	
	@Autowired
	private IGroupBuyAreaManager groupBuyAreaManager;
	
	/**
	 * 跳转至团购地区列表	
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView view=this.getGridModelAndView();
		view.setViewName("/groupbuy/area/area_list");
		return view;
	}
	
	/**
	 * 团购地区分页列表json
	 * @param webpage 团购地区分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list-json")
	public GridJsonResult listJson(){
		this.webpage=this.groupBuyAreaManager.list(this.getPage(),this.getPageSize());
		return JsonResultUtil.getGridJson(this.webpage);
	}
	/**
	 * 跳转至团购地区
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(){
		return "/groupbuy/area/area_add";
	}
	
	/**
	 * 
	 * @Title: saveAdd
	 * @Description: 添加团购地区
	 * @param groupBuyArea 团购地区
	 * @param area_name 地区名称
	 * @param area_order 排序
	 * @return json 1为成功.0为失败
	 */
	@ResponseBody
	@RequestMapping(value="/save-add")
	public JsonResult saveAdd(String  area_name,Integer  area_order){
		try {
			GroupBuyArea groupBuyArea = new GroupBuyArea();
			groupBuyArea.setArea_name(area_name);
			groupBuyArea.setArea_order(area_order);
			this.groupBuyAreaManager.add(groupBuyArea);
			return JsonResultUtil.getSuccessJson("添加成功");
		} catch (Exception e) {
			return JsonResultUtil.getErrorJson("添加失败"+e.getMessage());
		}
		
	}
	
	/**
	 * 跳转至修改团购地区
	 * @param groupBuyArea 团购地区
	 * @param area_id 团购地区Id数组
	 * @return
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(Integer[] area_id){
		ModelAndView view=new ModelAndView();
		view.addObject("groupBuyArea", groupBuyAreaManager.get(area_id[0]));
		view.setViewName("/groupbuy/area/area_edit");
		return view;
	}
	/**
	 * 
	 * @Title: saveEdit
	 * @Description: 保存修改团购地区
	 * @param groupBuyArea 团购地区
	 * @param area_id 团购地区Id数组
	 * @param area_name 地区名称
	 * @param area_order 团购地区排序
	 * @return json 1为成功.0为失败
	 */
	@ResponseBody
	@RequestMapping(value="/save-edit")
	public JsonResult saveEdit(String  area_name,Integer  area_order,Integer[] area_id){
		try {
			GroupBuyArea groupBuyArea = new GroupBuyArea();
			groupBuyArea.setArea_id(area_id[0]);
			groupBuyArea.setArea_name(area_name);
			groupBuyArea.setArea_order(area_order);
			this.groupBuyAreaManager.update(groupBuyArea);
			return JsonResultUtil.getSuccessJson("修改成功");
		} catch (Exception e) {
			this.logger.error("修改失败", e);
			return JsonResultUtil.getErrorJson("修改失败"+e.getMessage());
		}
		
	}
	/**
	 * 批量删除团购地区 
	 * @param area_id 团购地区Id数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/batch-delete")
	public JsonResult batchDelete(Integer[] area_id){
		try {
			this.groupBuyAreaManager.delete(area_id);
			return JsonResultUtil.getSuccessJson("删除改成功");
		} catch (Exception e) {
			this.logger.error("删除失败", e);
			return JsonResultUtil.getErrorJson("删除失败"+e.getMessage());
		}
	}

}
