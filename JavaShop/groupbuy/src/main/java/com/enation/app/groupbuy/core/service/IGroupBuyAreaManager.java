package com.enation.app.groupbuy.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.groupbuy.core.model.GroupBuyArea;
import com.enation.framework.database.Page;

/**
 * 
 * @ClassName: IGroupBuyAreaManager 
 * @Description: 团购地区管理 
 * @author TALON 
 * @date 2015-7-31 上午1:43:11 
 *
 */
public interface IGroupBuyAreaManager {

	/**
	 * 获取地区列表
	 * @param pageNo 列表分页页码
	 * @param pageSize 每页显示数量
	 * @return Page 团购地区分页列表
	 */
	public Page list(int pageNo,int pageSize);
	
	
	/**
	 * 获取所有地区列表，不分页
	 * @return List<GroupBuyArea> 团购地区列表
	 */
	public List<GroupBuyArea> listAll();
	
	
	/**
	 * 获取某个团购地区
	 * @param areaid 团购地区Id
	 * @return GroupBuyArea 团购地区
	 */
	public GroupBuyArea get(int areaid);
	
	
	
	/**
	 * 添加团购地区
	 * @param groupBuyArea 团购地区
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(GroupBuyArea groupBuyArea);
	
	/**
	 * 更新团购地区
	 * @param groupBuyArea 团购地区
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(GroupBuyArea groupBuyArea);

	
	/**
	 * 删除团购地区
	 * @param areaid 团购地区ID数组
	 */
	public void delete(Integer[] areaid);
}
