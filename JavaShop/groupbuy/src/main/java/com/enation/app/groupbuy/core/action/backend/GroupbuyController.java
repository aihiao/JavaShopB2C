package com.enation.app.groupbuy.core.action.backend;


import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.enation.app.base.core.upload.IUploader;
import com.enation.app.base.core.upload.UploadFacatory;
import com.enation.app.groupbuy.core.model.GroupBuy;
import com.enation.app.groupbuy.core.service.IGroupBuyActiveManager;
import com.enation.app.groupbuy.core.service.IGroupBuyAreaManager;
import com.enation.app.groupbuy.core.service.IGroupBuyCatManager;
import com.enation.app.groupbuy.core.service.IGroupBuyManager;
import com.enation.app.shop.core.goods.service.IGoodsManager;
import com.enation.eop.sdk.utils.StaticResourcesUtil;
import com.enation.framework.action.GridController;
import com.enation.framework.action.JsonResult;
import com.enation.framework.util.FileUtil;
import com.enation.framework.util.JsonResultUtil;

/**
 * 
 * @ClassName: GroupbuyAction 
 * @Description: 团购Action 
 * @author TALON 
 * @date 2015-7-31 上午12:55:05 
 *
 */
@Controller
@RequestMapping("/shop/admin/group-buy")
public class GroupbuyController extends GridController{
	
	@Autowired
	private IGroupBuyManager groupBuyManager;
	
	@Autowired
	private IGroupBuyActiveManager groupBuyActiveManager;
	
	@Autowired
	private IGroupBuyCatManager groupBuyCatManager;
	
	@Autowired
	private IGroupBuyAreaManager groupBuyAreaManager;
	
	@Autowired
	private IGoodsManager goodsManager;
	
	
	/**
	 * 跳转至团购列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Integer actid){
		ModelAndView view =this.getGridModelAndView();
		view.addObject("actid", actid);
		view.setViewName("/groupbuy/groupbuy/groupbuy_list");
		return view;
	}
	
	 /**
	  * 按活动id显示团购json
	  * @return
	  */
	@ResponseBody
	@RequestMapping(value="/list-json")
	 public Object listJson(Integer actid,Integer status){
		 try {
			this.webpage = this.groupBuyManager.listByActId(this.getPage(), this.getPageSize(), actid, status);
			return JsonResultUtil.getGridJson(webpage);
		} catch (Exception e) {
			this.logger.error("查询出错",e);
			return JsonResultUtil.getErrorJson("查询出错");
		}
	 }
	
	 /**
	  * 审核团购
	  * @param gbid 团购Id
	  * @param status 审核状态
	  * @return
	  */
	@ResponseBody
	@RequestMapping(value="/auth")
	 public JsonResult auth(int gbid,Integer status){
		 try {
			 this.groupBuyManager.auth(gbid, status);
			 return JsonResultUtil.getSuccessJson("操作成功");
		} catch (Exception e) {
			this.logger.error("审核操作失败",e);
			return JsonResultUtil.getErrorJson("审核操作失败"+e.getMessage());
		}
	 }
	
	 /**
	  * 
	  * @Title: add
	  * @Description: 跳转至添加团购页面
	  * @param	groupBuyActive 团购活动
	  * @param	groupbuy_cat_list 团购分类列表
	  * @param	groupbuy_area_list 团购地区列表
	  * @return String	添加团购页面
	  */
	@RequestMapping(value="/add")
	 public ModelAndView add(Integer actid){
		 ModelAndView view =new ModelAndView();
		 view.addObject("actid", actid);
		 view.addObject("groupBuyActive", groupBuyActiveManager.get(actid));
		 view.addObject("groupbuy_cat_list", groupBuyCatManager.listAll());
		 view.addObject("groupbuy_area_list", groupBuyAreaManager.listAll());
		 view.setViewName("/groupbuy/groupbuy/groupbuy_add");
		 return view;
	 }
	 /**
	  * @Title: saveAdd
	  * @Description: 添加团购商品
	  * @param 	allowTYpe 判断上传的图片类型
	  * @param	imageFileName 图片名称
	  * @param 	groupBuy 团购
	  * 此功能有待扩展不应该将对象传输过来进行修改 应该传入的是字段然后新建对象存入进去。
	  * 不应该在这里去保存团购商品图片应该在添加团购的时候去调用统一的上传图片控件、并且应该支持多图上传。
	  * @return String	1为成功，0为失败	
	  */
	@ResponseBody
	@RequestMapping(value="/save-add")
	 public JsonResult saveAdd(GroupBuy groupBuy,@RequestParam(value = "image", required = false) MultipartFile image){
		 try {
			 if(image!=null){
				if (image!=null) {
					
					if(!FileUtil.isAllowUpImg(image.getOriginalFilename())){
						return JsonResultUtil.getErrorJson("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
					}else{
						//判断文件大小
						if(image.getSize() > 2000 * 1024){				
							return JsonResultUtil.getErrorJson("图片不能大于2MB");
						}
						InputStream stream=null;
						try {
							stream=image.getInputStream();
						} catch (Exception e) {
							e.printStackTrace();
						}
						IUploader uploader=UploadFacatory.getUploaer();
						//上传分类图片
						groupBuy.setImg_url(uploader.upload(stream, "brand",image.getOriginalFilename()));
					}
					
				}
			}
			groupBuyManager.add(groupBuy);
			return JsonResultUtil.getSuccessJson("添加成功");
		} catch (Exception e) {
			this.logger.error("团购添加失败："+e);
			return JsonResultUtil.getErrorJson("添加失败");
		}
		 
	 }
	 /**
	  * 
	  * @Title: edit
	  * @Description: 跳转至团购修改页面
	  * @param	groupBuy 团购商品
	  * @param	groupBuyActive 团购活动
	  * @param	goods 商品
	  * @param	groupbuy_cat_list 团购分类列表
	  * @param	groupbuy_area_list 团购地区列表
	  * @param	image_src 团购商品图片
	  * @return String 团购修改页面
	  */
	@RequestMapping(value="/edit")
	 public ModelAndView edit(Integer gbid){
		 GroupBuy groupBuy= groupBuyManager.get(gbid);

		 ModelAndView view =new ModelAndView();
		 view.addObject("groupBuy", groupBuy);
		 view.addObject("groupBuyActive", groupBuyActiveManager.get(groupBuy.getAct_id()));
		 view.addObject("goods", goodsManager.get(groupBuy.getGoods_id()));
		 view.addObject("groupbuy_cat_list", groupBuyCatManager.listAll());
		 view.addObject("groupbuy_area_list", groupBuyAreaManager.listAll());
		 view.addObject("image_src", StaticResourcesUtil.convertToUrl(groupBuy.getImg_url()));
		 view.setViewName("/groupbuy/groupbuy/groupbuy_edit");
		 return view;
	 }
	 /**
	  * 
	  * @Title: saveEdit
	  * @Description: 保存修改商品
	  * @param groupBuy 团购商品
	  * @return 1为成功。0为失败
	  */
	@ResponseBody
	@RequestMapping(value="/save-edit")
	 public JsonResult saveEdit(GroupBuy groupBuy){
		 try {
			 groupBuyManager.update(groupBuy);
			 return JsonResultUtil.getSuccessJson("修改团购成功");
		} catch (Exception e) {
			this.logger.error("修改团购失败",e);
			return JsonResultUtil.getErrorJson("修改团购失败");
		}
		 
	 }
}
