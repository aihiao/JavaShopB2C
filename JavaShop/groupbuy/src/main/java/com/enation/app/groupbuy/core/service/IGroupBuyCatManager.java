package com.enation.app.groupbuy.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.groupbuy.core.model.GroupBuyCat;
import com.enation.framework.database.Page;

/**
 * 
 * @ClassName: IGroupBuyCatManager 
 * @Description: 团购分类管理 
 * @author TALON 
 * @date 2015-7-31 上午1:46:44 
 *
 */
public interface IGroupBuyCatManager {
	
	/**
	 * 获取团购分类列表
	 * @param pageNo 页码
	 * @param pageSize 每页显示数量
	 * @return Page 团购分类分页列表
	 */
	public Page list(int pageNo,int pageSize);
	
	
	/**
	 * 获取所有分类列表，不分页
	 * @return List<GroupBuyCat> 团购分页列表
	 */
	public List<GroupBuyCat> listAll();
	
	
	/**
	 * 获取某个团购
	 * @param catid 团购分类Id
	 * @return GroupBuyCat 团购分类
	 */
	public GroupBuyCat get(int catid);
	
	
	
	/**
	 * 添加团购分类
	 * @param groupBuyCat 团购分类
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(GroupBuyCat groupBuyCat);
	
	/**
	 * 更新团购分类
	 * @param groupBuyCat 团购分类
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(GroupBuyCat groupBuyCat);

	
	/**
	 * 删除团购分类
	 * @param catid 团购分类ID
	 */
	public void delete(Integer[] catid);
}
