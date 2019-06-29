package com.enation.app.groupbuy.core.model;

import com.enation.app.shop.core.goods.model.Tag;

/**
 * 团购商品标签
 * @author LiFenLong
 *
 */
public class GroupBuyTag extends Tag {

	private Integer is_groupbuy;	//是否为团购商品标签

	public Integer getIs_groupbuy() {
		return is_groupbuy;
	}

	public void setIs_groupbuy(Integer is_groupbuy) {
		this.is_groupbuy = is_groupbuy;
	}
	
}
