package com.enation.app.groupbuy.core.service;

import java.util.List;
import java.util.Map;

import com.enation.app.groupbuy.core.model.GroupBuyTag;
import com.enation.framework.database.Page;

/**
 * 
 * @ClassName: IGroupGoodsTagManager 
 * @Description: 团购标签管理类 
 * @author TALON 
 * @date 2015-7-31 上午1:51:53 
 *
 */
public interface IGroupGoodsTagManager {
	/**
	 * 标签商品分页列表
	 * @param pageNo 页码
	 * @param pageSize 分页每页显示数量
	 * @return Page  标签商品分页列表
	 */
	public Page list(int pageNo, int pageSize);
	/**
	 * 获取团购标签商品分页列表
	 * @param tagid 标签Id
	 * @param page 分页
	 * @param pageSize 每页显示数量
	 * @param order 顺序
	 * @param sort 排序条件
	 * @return Page 标签商品分页列表
	 */
	public Page groupBuyList(int tagid,Map goodsMap,int page,int pageSize,String sort,String order);
	
	/**
	 * 添加团购标签
	 * @param tag 团购标签
	 */
	public void add(GroupBuyTag tag);
	/**
	 * 根据团购商品标签查询 标签商品
	 * @param catid 分类Id
	 * @param tagid 标签Id
	 * @param goodsnum 显示数量
	 * @return List 商品列表
	 */
	public List listGoods(String catid,String tagid,String goodsnum);
}
