package com.enation.app.groupbuy.component.plugin.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActDeleteEvent;
import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActEndEvent;
import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActStartEvent;
import com.enation.app.groupbuy.core.service.IGroupBuyActiveManager;
import com.enation.app.shop.core.goods.plugin.IGoodsBeforeAddEvent;
import com.enation.app.shop.core.goods.plugin.IGoodsDeleteEvent;
import com.enation.app.shop.core.goods.plugin.IGoodsVisitEvent;
import com.enation.app.shop.core.order.model.Order;
import com.enation.app.shop.core.order.model.support.CartItem;
import com.enation.app.shop.core.order.plugin.order.IAfterOrderCreateEvent;
import com.enation.app.shop.core.order.plugin.order.IOrderCanelEvent;
import com.enation.eop.SystemSetting;
import com.enation.eop.sdk.utils.StaticResourcesUtil;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.context.webcontext.WebSessionContext;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.plugin.AutoRegisterPlugin;
import com.enation.framework.util.StringUtil;
/**
 * 
 * @ClassName: GroupBuyGoodsPlugin 
 * @Description: 团购商品插件 
 * @author TALON 
 * @date 2015-7-31 上午10:44:09 
 *
 */
@Component
public class GroupBuyGoodsPlugin extends AutoRegisterPlugin implements IAfterOrderCreateEvent,IGoodsVisitEvent,IGroupBuyActStartEvent,IGroupBuyActEndEvent,IGroupBuyActDeleteEvent,IGoodsBeforeAddEvent,IGoodsDeleteEvent,IOrderCanelEvent{

	@Autowired
	private IDaoSupport daoSupport;

	@Autowired
	private IGroupBuyActiveManager groupBuyActiveManager;
	/**
	 * 判断是否浏览过商品 如果没有浏览过商品则浏览次数加1
	 */
	@Override
	public void onVisit(Map goods) {
		if(groupBuyActiveManager.get() != null){
			HttpSession sessionContext = ThreadContextHolder.getSession();
			List<Map> visitedGoods = (List<Map>)sessionContext.getAttribute("visitedGroupBuyGoods");

			Integer goods_id=Integer.valueOf(goods.get("goods_id").toString());
			boolean visited = false;
			if(visitedGoods==null) visitedGoods = new ArrayList<Map>();
			for(Map map:visitedGoods){
				if(map.get("goods_id").toString().equals(StringUtil.toString(goods_id))){//说明当前session访问过此商品
					visitedGoods.remove(map);
					visited = true;
					break;
				}
			}
			String  thumbnail =(String) goods.get("thumbnail");
			if(StringUtil.isEmpty(thumbnail)){
				String default_img_url = SystemSetting.getDefault_img_url();
				thumbnail=default_img_url;
			}else{
				thumbnail=StaticResourcesUtil.convertToUrl(thumbnail);
			}
			Map newmap = new HashMap();
			newmap.put("goods_id", goods_id);
			newmap.put("thumbnail", thumbnail);
			newmap.put("name", goods.get("name"));
			newmap.put("price", goods.get("price"));
			visitedGoods.add(0, newmap);
			sessionContext.setAttribute("visitedGroupBuyGoods", visitedGoods);
			if(!visited){
				Integer act_id=groupBuyActiveManager.get().getAct_id();
				String sql="update es_groupbuy_goods set view_num=view_num+1 where goods_id=? and act_id=?";
				this.daoSupport.execute(sql, goods_id,act_id);
			}
		}
	}
	@Override
	public void onEndGroupBuyEnd(Integer act_id) {
		String sql="update es_goods  SET is_groupbuy=0 WHERE goods_id in(SELECT goods_id from es_groupbuy_goods WHERE act_id=?)";
		this.daoSupport.execute(sql, act_id);
		//修改购物车is_change通知前端价格或者其他需要变动
		sql="update es_cart set is_change = 1 WHERE goods_id in(SELECT goods_id from es_groupbuy_goods WHERE act_id=?)";
		this.daoSupport.execute(sql, act_id);
	}
	@Override
	public void onGroupBuyStart(Integer act_id) {
		//团购商品开启团购
		String sql="update es_goods  SET is_groupbuy=1 WHERE goods_id in(SELECT goods_id from es_groupbuy_goods WHERE act_id=?)";
		this.daoSupport.execute(sql, act_id);

	}
	@Override
	public void onDeleteGroupBuyAct(Integer act_id) {
		String sql="delete from es_groupbuy_goods WHERE act_id=?";
		this.daoSupport.execute(sql,act_id);
	}
	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
		goods.put("is_groupbuy", 0);  //是否为团购商品
	}
	@Override
	public void onGoodsDelete(Integer[] goodsid) {
		String id_str = StringUtil.arrayToString(goodsid, ",");
		String sql = "delete  from es_groupbuy_goods where goods_id in (" + id_str + ")";
		this.daoSupport.execute(sql);

	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void onAfterOrderCreate(Order order, List<CartItem> itemList,String sessionid) {
		String sql = "select oi.* from es_order_items oi inner join es_goods g on oi.goods_id=g.goods_id  where order_id = ? and g.is_groupbuy=1";
		List<Map > orderitemList = this.daoSupport.queryForList(sql,order.getOrder_id());
		sql="update es_groupbuy_goods set buy_num=buy_num+?,goods_num=goods_num-? where goods_id=? and act_id=?";
		for (Map orderItem : orderitemList) {
			this.daoSupport.execute(sql, orderItem.get("num"),orderItem.get("num"),orderItem.get("goods_id"),groupBuyActiveManager.get().getAct_id());
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void canel(Order order) {
		String sql = "select oi.* from es_order_items oi inner join es_goods g on oi.goods_id=g.goods_id  where order_id = ? and g.is_groupbuy=1";
		List<Map > orderitemList = this.daoSupport.queryForList(sql,order.getOrder_id());
		sql="update es_groupbuy_goods set buy_num=buy_num-?,goods_num=goods_num+? where goods_id=? and act_id=?";
		for (Map orderItem : orderitemList) {
			this.daoSupport.execute(sql, orderItem.get("num"),orderItem.get("num"),orderItem.get("goods_id"),groupBuyActiveManager.get().getAct_id());
		}
	}	
}
