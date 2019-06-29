package com.enation.app.groupbuy.core.service;


import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.groupbuy.core.model.GroupBuy;
import com.enation.framework.database.Page;
/**
 * 
 * @ClassName: IGroupBuyManager 
 * @Description: 团购商品管理类 
 * @author TALON 
 * @date 2015-7-31 上午1:51:19 
 *
 */
public interface IGroupBuyManager {
	/**
	 * 创建团购
	 * @param groupBuy
	 * @return 创建成功返回创建的团购id
	 *         创建失败返回0
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int add(GroupBuy groupBuy);
	/**
	 * 修改团购信息
	 * @param groupBuy
	 * @return 创建成功返回创建的团购id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(GroupBuy groupBuy);
	
	/**
	 * 删除团购
	 * @param gbid 团购id
	 */
	public void delete(int gbid);

	/**
	 * 审核
	 * @param gbid 团购id
	 * @param status 状态
	 */
	public void auth(int gbid,int status);
	

	/**
	 * 查询某活动下的团购
	 * @param page 页码
	 * @param pageSize 分页每页显示数量
	 * @param actid 活动id
	 * @param status 状态
	 * @return 团购分页列表
	 */
	public Page listByActId(int page ,int pageSize,int actid,Integer status);
	
	/**
	 * 前台显示团购
	 * @param page 页数
	 * @param pageSize 每页显示数量
	 * @param catid 分类Id
	 * @param minprice 最小金额
	 * @param maxprice 最大金额
	 * @param sort_key 排序类型
	 * @param sort_type 正序还是倒叙
	 * @param search_type 搜索类型
	 * @param area_id 团购地区Id
	 * @return 团购分页列表
	 */
	public Page search(int page,int pageSize,Integer catid,Double minprice,Double maxprice,Integer sort_key,Integer sort_type,Integer search_type,Integer area_id);
	/**
	 * 获取某个团购信息
	 * @param gbid 团购id
	 * @return GroupBuy 团购商品
	 */
	public GroupBuy get(int gbid);
	
	/**
	 * 根据商品Id获取团购商品
	 * @param goodsId 商品ID
	 * @return GroupBuy 团购商品
	 */
	public GroupBuy getBuyGoodsId(int goodsId);
	/**
	 * 搜索某店铺的商品，不分页
	 * @author kingapex 
	 * add by 2015-01-07
	 * @param storeid 店铺id
	 * @param map 其它搜索参数:
	 *  store_catid:店铺分类 
	 *  keyword:关键字
	 * @return 商品列表
	 */
	public List<Map>  storeGoodsList(int storeid,Map map);
	/**
	 * 根据商品Id获取团购商品
	 * @param goodsId 商品ID
	 * @return GroupBuy 团购商品列表
	 */
	public List<GroupBuy> getBuyGoodsList(int goodsId);
	
}
