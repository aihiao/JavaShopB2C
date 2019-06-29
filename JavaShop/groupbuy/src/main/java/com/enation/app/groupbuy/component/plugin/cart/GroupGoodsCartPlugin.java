package com.enation.app.groupbuy.component.plugin.cart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.groupbuy.core.model.GroupBuy;
import com.enation.app.groupbuy.core.model.GroupBuyActive;
import com.enation.app.groupbuy.core.service.IGroupBuyActiveManager;
import com.enation.app.groupbuy.core.service.IGroupBuyManager;
import com.enation.app.shop.core.goods.model.Product;
import com.enation.app.shop.core.goods.service.IGoodsManager;
import com.enation.app.shop.core.order.model.Cart;
import com.enation.app.shop.core.order.plugin.cart.ICartAddEvent;
import com.enation.app.shop.core.order.plugin.cart.ICartUpdateEvent;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.plugin.AutoRegisterPlugin;
/**
 * 
 * @ClassName: GroupGoodsCartPlugin 
 * @Description: 团购商品购物车标签 
 * @author TALON 
 * @date 2015-7-31 上午10:43:51 
 *
 */
@Component
public class GroupGoodsCartPlugin extends AutoRegisterPlugin implements ICartAddEvent,ICartUpdateEvent{

	@Autowired
	private IGoodsManager goodsManager;

	@Autowired
	private IGroupBuyManager groupBuyManager;

	@Autowired
	private IGroupBuyActiveManager groupBuyActiveManager;

	@Autowired
	private IDaoSupport daoSupport;
	@Override
	public void add(Cart cart) {
		Map goods=goodsManager.get(cart.getGoods_id());
		if(goods.get("is_groupbuy")!=null&&Integer.parseInt(goods.get("is_groupbuy").toString())==1){
			GroupBuy groupbuy=groupBuyManager.getBuyGoodsId(cart.getGoods_id());
			//判断是否不限购
			if(groupbuy.getLimit_num()!=0){
				//判断购买数量超过限购数量
				if(cart.getNum()>groupbuy.getLimit_num()){
					throw new RuntimeException("超过限购数量");
				}else{
					cart.setPrice(groupbuy.getPrice());
				}
			}else{
				cart.setPrice(groupbuy.getPrice());
			}

		}
	}
	/**
	 * 判别团购商品数量
	 * @param sessionId
	 * @param cartId
	 * @param num
	 */
	private void judgeGroupBuy(Integer cartId, Integer num){
		//获取当前的团购活动
		GroupBuyActive groupbuyact=groupBuyActiveManager.get();

		//判断当前是否有团购活动
		if(groupbuyact!=null){

			//获取判断当前购物车商品是否为团购商品
			String sql="select goods_id from es_cart where cart_id=?";
			Integer goods_id=daoSupport.queryForInt(sql, cartId);
			Map goods=goodsManager.get(goods_id);

			//判断是否为团购商品如果是就判断超出限购数量
			if(goods.get("is_groupbuy")!=null&&Integer.parseInt(goods.get("is_groupbuy").toString())==1){
				GroupBuy groupbuy=groupBuyManager.getBuyGoodsId(goods_id);
				if(groupbuy.getLimit_num()!=0){
					if(num>groupbuy.getLimit_num()){
						throw new RuntimeException("团购商品超出限购数量");
					}
				}
				//判断团购数量是否超出此商品的可团购数量
				if(groupbuy.getGoods_num() != 0){
					//获取当前购物数量+已购数量+虚拟数量之和，用来判断是否超出可团购数量
//					Integer gnum=num+groupbuy.getBuy_num();
					if(num>groupbuy.getGoods_num()){
						throw new RuntimeException("团购商品超出可团购数量");
					}
				}else{
					throw new RuntimeException("团购商品超出可团购数量");
				}
			}
		}
	}
	@Override
	public void onBeforeUpdate(String sessionId, Integer cartId, Integer num) {
		this.judgeGroupBuy(cartId, num);

	}
	@Override
	public void onUpdate(String sessionId, Integer cartId) {
		// TODO Auto-generated method stub

	}
	@Override
	public void afterAdd(Cart cart){
		Cart cat=this.daoSupport.queryForObject("select * from es_cart where product_id=? and itemtype=?  and ( session_id=? or member_id=? )", Cart.class, cart.getProduct_id(),cart.getItemtype(),cart.getSession_id(),cart.getMember_id());
		this.judgeGroupBuy(cat.getCart_id(), cat.getNum());

	}
	@Override
	public void onAddProductToCart(Product product, Integer num) {
		// TODO Auto-generated method stub

	}

}
