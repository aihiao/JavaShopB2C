package com.enation.app.groupbuy.core.model;

import com.enation.framework.database.PrimaryKeyField;
/**
 * 团购分类
 * @author kingapex
 *2015-1-2下午8:48:34
 *@author fenlongli
 *2015-3-29
 */
public class GroupBuyCat {
	private int  catid       ;      
	private int  parentid    ;      
	private String  cat_name    ;      
	private String  cat_path    ;      
	private int  cat_order   ;
	
	@PrimaryKeyField
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public String getCat_path() {
		return cat_path;
	}
	public void setCat_path(String cat_path) {
		this.cat_path = cat_path;
	}
	public int getCat_order() {
		return cat_order;
	}
	public void setCat_order(int cat_order) {
		this.cat_order = cat_order;
	}
}
