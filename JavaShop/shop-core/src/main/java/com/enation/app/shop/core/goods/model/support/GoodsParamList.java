package com.enation.app.shop.core.goods.model.support;

import com.enation.app.shop.core.goods.model.GoodsParam;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品参数
 * @author apexking
 *
 */
public class GoodsParamList {
	
	private String name; //参数名
	private List<GoodsParam> paramList; //属性值

	public List<GoodsParam> getParamList() {
		return paramList;
	}

	public void setParamList(List<GoodsParam> paramList) {
		this.paramList = paramList;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
