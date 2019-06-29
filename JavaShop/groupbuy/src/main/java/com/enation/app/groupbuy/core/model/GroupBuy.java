package com.enation.app.groupbuy.core.model;

import com.enation.app.shop.core.goods.model.Goods;
import com.enation.eop.sdk.utils.StaticResourcesUtil;
import com.enation.framework.database.NotDbField;
import com.enation.framework.database.PrimaryKeyField;

/**
 * 团购
 *  @author kingapex
 *2015-1-2下午7:44:58
 * @author fenlongli
 *2015-3-29
 */
public class GroupBuy {

	private Integer gb_id;		//团购商品Id
	private Integer act_id;		//活动Id
	private Integer area_id;	//地区Id
	private String gb_name;	//团购名称
	private String gb_title;	//副标题
	private String goods_name;	//商品名称
	private Integer goods_id;	//商品Id
	private Integer product_id;	//货品Id
	private double price;	//团购价格
	private String img_url;	//图片地址
	private Integer goods_num;	//商品总数
	private Integer cat_id;		//团购分类Id
	private Integer visual_num;	//虚拟数量
	private Integer limit_num;	//限购数量
	private Integer buy_num;	//已团购数量
	private String remark;	//介绍
	private Integer gb_status;	//状态  
	private Integer view_num;	//浏览数量
	private long add_time;	//添加时间
	private double original_price;	//原始价格
	
	private String thumbnail;	//pc缩略图
	private String wap_thumbnail; //wap缩略图
	
 
	
	/**
	 * 团购对应的商品
	 * 非数据库字段
	 */
	private Goods goods;
	
	@PrimaryKeyField
	public Integer getGb_id() {
		return gb_id;
	}
	
	
	@NotDbField
	public Goods getGoods() {
		return goods;
	}
	
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	@NotDbField
	public String getThumbnail_src() {
		return StaticResourcesUtil.convertToUrl(thumbnail);
	}
 
	@NotDbField
	public String getWap_thumbnail_src() {
		return StaticResourcesUtil.convertToUrl(wap_thumbnail);
	}


	public Integer getAct_id() {
		return act_id;
	}


	public void setAct_id(Integer act_id) {
		this.act_id = act_id;
	}


	public Integer getArea_id() {
		return area_id;
	}


	public void setArea_id(Integer area_id) {
		this.area_id = area_id;
	}


	public String getGb_name() {
		return gb_name;
	}


	public void setGb_name(String gb_name) {
		this.gb_name = gb_name;
	}


	public String getGb_title() {
		return gb_title;
	}


	public void setGb_title(String gb_title) {
		this.gb_title = gb_title;
	}


	public String getGoods_name() {
		return goods_name;
	}


	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}


	public Integer getGoods_id() {
		return goods_id;
	}


	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}


	public Integer getProduct_id() {
		return product_id;
	}


	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getImg_url() {
		return img_url;
	}


	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}


	public Integer getGoods_num() {
		return goods_num;
	}


	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}


	public Integer getCat_id() {
		return cat_id;
	}


	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
	}


	public Integer getVisual_num() {
		return visual_num;
	}


	public void setVisual_num(Integer visual_num) {
		this.visual_num = visual_num;
	}


	public Integer getLimit_num() {
		return limit_num;
	}


	public void setLimit_num(Integer limit_num) {
		this.limit_num = limit_num;
	}


	public Integer getBuy_num() {
		return buy_num;
	}


	public void setBuy_num(Integer buy_num) {
		this.buy_num = buy_num;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getGb_status() {
		return gb_status;
	}


	public void setGb_status(Integer gb_status) {
		this.gb_status = gb_status;
	}


	public Integer getView_num() {
		return view_num;
	}


	public void setView_num(Integer view_num) {
		this.view_num = view_num;
	}


	public long getAdd_time() {
		return add_time;
	}


	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}


	public double getOriginal_price() {
		return original_price;
	}


	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}


	public String getThumbnail() {
		return thumbnail;
	}


	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}


	public String getWap_thumbnail() {
		return wap_thumbnail;
	}


	public void setWap_thumbnail(String wap_thumbnail) {
		this.wap_thumbnail = wap_thumbnail;
	}


	public void setGb_id(Integer gb_id) {
		this.gb_id = gb_id;
	}
 
	
}
