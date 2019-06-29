package com.enation.app.shop.core.member.model;

import com.enation.framework.database.PrimaryKeyField;

/**
 * 商品收藏
 * 
 * @author lzf<br/>
 *         2010-3-24 下午02:38:48<br/>
 *         version 1.0<br/>
 */
public class Footprint implements java.io.Serializable {
	private int footprint_id;
	private int member_id;
	private int goods_id;
	private String is_default;
	
	/**
	 * 浏览时间
	 * 添加人：DMRain 2015-12-16
	 */
	private long footprint_time;

	public int getFootprint_id() {
		return footprint_id;
	}

	public void setFootprint_id(int footprint_id) {
		this.footprint_id = footprint_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public long getFootprint_time() {
		return footprint_time;
	}

	public void setFootprint_time(long footprint_time) {
		this.footprint_time = footprint_time;
	}
}
