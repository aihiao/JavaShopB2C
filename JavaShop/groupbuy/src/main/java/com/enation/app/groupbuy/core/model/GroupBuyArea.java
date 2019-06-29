package com.enation.app.groupbuy.core.model;

import com.enation.framework.database.PrimaryKeyField;

/**
 * 团购地区
 * @author kingapex
 * 2015-1-2下午8:48:34
 * @author fenlongli
 * 2015-3-29
 *
 */
public class GroupBuyArea {
	
	private int  area_id;      //地区ID
	private int  parent_id;    
	private String  area_name;      
	private String  area_path;      
	private int  area_order;
	
	@PrimaryKeyField
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getArea_path() {
		return area_path;
	}
	public void setArea_path(String area_path) {
		this.area_path = area_path;
	}
	public int getArea_order() {
		return area_order;
	}
	public void setArea_order(int area_order) {
		this.area_order = area_order;
	}
	
}
