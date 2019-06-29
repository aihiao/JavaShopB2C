package com.enation.app.shop.core.member.service;

import com.enation.app.shop.core.member.model.Favorite;
import com.enation.app.shop.core.member.model.Footprint;
import com.enation.framework.database.Page;

import java.util.List;

/**
 * 商品收藏管理
 * 
 * @author lzf<br/>
 *         2010-3-24 下午02:39:25<br/>
 *         version 1.0<br/>
 */
public interface IFootprintManager {

	/**
	 * 读取某个会员的收足迹
	 * @param memberid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page list(int memberid, int pageNo, int pageSize);
	
	
	
	

	
	
	
	/**
	 * 添加一个足迹
	 * @param goodsid
	 * @param memberid
	 */
	public void add(Footprint footprint);


	/**
	 * 更新会员浏览足迹时间
	 * @param
	 * @param
	 */
	public void update(int footprint_id);


	/**
	 * 通过商品id 和 会员id 获得一个足迹
	 * @param goodsid
	 * @param memberid
	 * @return
	 */
	public Footprint get(int goodsid, int memberid);




	
}
