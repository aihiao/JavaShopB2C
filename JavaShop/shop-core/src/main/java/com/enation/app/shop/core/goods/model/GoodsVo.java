package com.enation.app.shop.core.goods.model;


import com.enation.app.shop.core.goods.model.support.GoodsParamList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 商品实体
 *
 * @author kingapex 2010-4-25下午09:40:24
 */
public class GoodsVo implements java.io.Serializable {

	public GoodsVo(Goods goods){
		this.goods_id = goods.getGoods_id();
	    this.name  = goods.getName();
	    this.sn = goods.getSn();
	    this.brand_id = goods.getBrand_id();
		this.cat_id = goods.getCat_id();
		this.type_id = goods.getType_id();
		this.goods_type = goods.getGoods_type();
		this.unit = goods.getUnit();
		this.weight = goods.getWeight();
		this.market_enable = goods.getMarket_enable();
		this.thumbnail = goods.getThumbnail();
		this.big = goods.getBig();
		this.small = goods.getSmall();
		this.original = goods.getOriginal();
		this.brief = goods.getBrief();
		this.intro = goods.getIntro();
		this.price = goods.getPrice();
		this.mktprice = goods.getMktprice();
		this.store = goods.getStore();
		this.adjuncts = goods.getAdjuncts();
		this.create_time = goods.getCreate_time();
		this.specs = goods.getSpecs();
		this.last_modify = goods.getLast_modify();
		this.view_count = goods.getView_count();
		this.buy_count = goods.getBuy_count();
		this.disabled = goods.getDisabled();
		this.page_title = goods.getPage_title();
		this.meta_keywords = goods.getMeta_keywords();
		this.meta_description = goods.getMeta_description();
		this.point = goods.getPoint();
		this.sord = goods.getSord();
		Gson gson = new Gson();
		this.goodsParamList = gson.fromJson(goods.getParams(), new TypeToken<List<GoodsParamList>>() {
		}.getType());
	}
	private Integer goods_id;
	private String name;
	private String sn;
	private Integer brand_id;
	private Integer cat_id;
	private Integer type_id;
	private String goods_type; // enum('normal', 'bind') default 'normal', options 'service'
	private String unit;
	private Double weight;
	private Integer market_enable; //上下架	上架：1	 下架：0   预览：2
	private String thumbnail;
	private String big;
	private String small;
	private String original;
	private String brief;
	private String intro;
	private Double price;
	private Double mktprice;
	private Integer store;
	private String adjuncts;
	private List<GoodsParamList> goodsParamList;
	private String specs;
	private Long create_time;
	private Long last_modify;
	private Integer view_count;
	private Integer buy_count;
	private Integer disabled;	//删除： 回收站：1	 正常 ：0       失效：2
	private String page_title;
	private String meta_keywords;
	private String meta_description;
	private Integer point; // 积分
	private Integer sord;

	private Double 	commission;	//商品佣金比例

	public Integer getBrand_id() {
		if (brand_id == null)
			brand_id = 0;
		return brand_id;
	}

	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Integer getBuy_count() {
		return buy_count;
	}

	public void setBuy_count(Integer buy_count) {
		this.buy_count = buy_count;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getBig() {
		return big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public List<GoodsParamList> getGoodsParamList() {
		return goodsParamList;
	}

	public void setGoodsParamList(List<GoodsParamList> goodsParamList) {
		this.goodsParamList = goodsParamList;
	}

	public Integer getMarket_enable() {
		return market_enable;
	}

	public void setMarket_enable(Integer market_enable) {
		this.market_enable = market_enable;
	}

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	public String getMeta_keywords() {
		return meta_keywords;
	}

	public void setMeta_keywords(String meta_keywords) {
		this.meta_keywords = meta_keywords;
	}

	public Double getMktprice() {
		return mktprice;
	}

	public void setMktprice(Double mktprice) {
		this.mktprice = mktprice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPage_title() {
		return page_title;
	}

	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goodsType) {
		goods_type = goodsType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public Double getWeight() {
		weight = weight == null ? weight = 0D : weight;
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getCat_id() {
		return cat_id;
	}

	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	public Long getLast_modify() {
		return last_modify;
	}

	public void setLast_modify(Long last_modify) {
		this.last_modify = last_modify;
	}

	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public String getAdjuncts() {
		return adjuncts;
	}

	public void setAdjuncts(String adjuncts) {
		this.adjuncts = adjuncts;
	}

	public Integer getPoint() {
		point = point == null ? 0 : point;
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getSord() {
		return sord;
	}

	public void setSord(Integer sord) {
		this.sord = sord;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}


}