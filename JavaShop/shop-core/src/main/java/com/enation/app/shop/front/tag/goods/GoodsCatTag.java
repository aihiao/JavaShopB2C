package com.enation.app.shop.front.tag.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.enation.app.base.core.model.Adv;
import com.enation.app.shop.core.goods.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.enation.app.shop.core.goods.model.Cat;
import com.enation.app.shop.core.goods.service.IGoodsCatManager;
import com.enation.eop.sdk.utils.StaticResourcesUtil;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import com.enation.framework.util.StringUtil;
import com.enation.app.shop.core.goods.model.Brand;

import freemarker.template.TemplateModelException;
/**
 * 商品分类标签
 * @author lina
 * 2013-12-19
 */
@Component
@Scope("prototype")
public class GoodsCatTag extends BaseFreeMarkerTag {
	
	@Autowired
	private IGoodsCatManager goodsCatManager;

	/**
	 * 商品分类数据
	 * @param parentid 上一级id
	 * @param catimage 是否显示类别图片 on:显示类别图片  off:不显示类别图片
	 * @return 分类数据信息，Map型，其中的key结构为:
	 * showimg:String型,是否显示分类图片
	 * cat_tree:List型,分类列表数据
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		Integer parentid=(Integer) params.get("parentid");
		if(parentid==null){
			parentid = 0;
		}
		List<Cat> cat_tree =  goodsCatManager.listAllChildren(parentid);
		String catimage = (String) params.get("catimage");
		boolean showimage  = catimage!= null &&catimage.equals("on") ?true:false;
		
		String imgPath="";
		if(!cat_tree.isEmpty()){
			for(Cat cat:cat_tree){
				
				if (cat.getImage() != null && !StringUtil.isEmpty(cat.getImage())) {
					imgPath = StaticResourcesUtil.convertToUrl(cat.getImage());
					cat.setImage(imgPath);
				}
				
			}
		}
		//查询一级分类(只获取前9个)
		List<Cat> menu = goodsCatManager.queryGoodsCat();
		//查询品牌制造商直供(只获取前4个)
		List<Brand> brands = goodsCatManager.queryBrand();
		//查询人气推荐
		List<Goods> goodss = goodsCatManager.queryGoods();
		//获取广告位
		List<Adv> advs = goodsCatManager.queryAdv();
		//查询二级菜单
		List<Cat> cats = goodsCatManager.queryCat();
		if(cats !=null){
			for (int i = 0; i < cats.size(); i++) {
				String str = cats.get(i).getCat_path(), des = "|";
				int cnt = 0;
				int offset = 0;
				while((offset = str.indexOf(des, offset)) != -1){
					offset = offset + des.length();
					cnt++;
				}
				if(cnt!=3||cnt>3||cnt<3){
					cats.remove(i);
					i--;
				}
			}
		}
		//查出商品
		for (int i = 0; i < cats.size(); i++) {
			String cat_path = cats.get(i).getCat_path().replace("|", ",");
			String[] sp = cat_path.split(",");
			String str = sp[sp.length-1];
			Integer parent = Integer.parseInt(str);
			List<Cat> cats1 = goodsCatManager.queryCatTypeId(parent);
			//把类型id查询出来
			Integer [] typeid = new Integer[cats1.size()];
			for (int j = 0; j < cats1.size(); j++) {
				typeid[j]=cats1.get(j).getType_id();
			}
			//把商品查出来并储存到cats里面；
			List<Goods> goodss1 = goodsCatManager.queryGoodsList(typeid);
			if(goodss1.size()==0||goodss1==null ){
				cats.remove(i);
				i--;
			}else{
				List<Goods> goodss2;
				if(goodss1.size()>5){
					goodss2 = goodss1.subList(0, 5);
				}else{
					goodss2=goodss1;
				}
				cats.get(i).setGoodsList(goodss2);
			}
		}


		Map<String, Object> data =new HashMap();
		data.put("showimg", showimage);//是否显示分类图片
		data.put("cat_tree", cat_tree);//分类列表数据
		data.put("menu", menu);//截取前9个分类列表数据
		data.put("advs", advs);//广告位
		data.put("goodss", goodss);//人气推荐前3个
		data.put("cats", cats);//二级菜单
		return data;
	}
	

	
}
