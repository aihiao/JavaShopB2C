package com.enation.app.groupbuy.front.api;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enation.app.groupbuy.core.model.GroupBuy;
import com.enation.app.groupbuy.core.service.IGroupBuyManager;
import com.enation.app.shop.core.goods.model.Product;
import com.enation.app.shop.core.goods.service.IProductManager;
import com.enation.app.shop.core.order.model.Cart;
import com.enation.app.shop.core.order.service.ICartManager;
import com.enation.framework.action.JsonResult;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.JsonMessageUtil;
import com.enation.framework.util.JsonResultUtil;
/**
 * 
 * @ClassName: GroupBuyCartApiAction 
 * @Description: 团购购物车API 
 * @author TALON 
 * @date 2015-7-31 上午12:48:32 
 *
 */
@Controller
@RequestMapping("/api/groupbuy/cart")
public class GroupBuyCartApiController {
	
	@Autowired
	private IProductManager productManager;
	
	@Autowired
	private IGroupBuyManager groupBuyManager;
	
	@Autowired
	private ICartManager cartManager;
	
	/**
	 * 将一个商品添加到购物车
	 * 需要传递goodsid 和num参数
	 * @param goodsid 商品id，int型
	 * @param num 数量，int型
	 * @return 返回json串
	 * result  为1表示调用成功0表示失败
	 * message 为提示信息
	 */
	@ResponseBody
	@RequestMapping(value="/add-goods",produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonResult addGoods(Integer goodsid,Integer num,Integer showCartData){
		Product product = productManager.getByGoodsId(goodsid);
		return this.addProductToCart(product,num,showCartData);
	}
	
	/**
	 * 获取购物车数据
	 * @param 无
	 * @return 返回json串
	 * result  为1表示调用成功0表示失败
	 * data.count：购物车的商品总数,int 型
	 * data.total:购物车总价，int型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/get-cart-data",produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCartData(){
		try{
			String sessionid =ThreadContextHolder.getHttpRequest().getSession().getId();

			Double goodsTotal  =cartManager.countGoodsTotal( sessionid );
			int count = this.cartManager.countItemNum(sessionid);
			
			java.util.Map<String, Object> data =new HashMap();
			data.put("count", count);//购物车中的商品数量
			data.put("total", goodsTotal);//总价
			
			return JsonMessageUtil.getObjectJson(data);
			
		}catch(Throwable e ){
			Logger logger = Logger.getLogger(getClass());
			logger.error("获取购物车数据出错",e);
			return JsonResultUtil.getErrorJson("获取购物车数据出错["+e.getMessage()+"]");
		}
		
	}
	
	/**
	 * 添加货品的购物车
	 * @param product 货品
	 * 1.判断购买的团购货品库存是否充足
	 * 2.判断用户购买的团购商品已经超过了商品的限购数量（此步需要去完善应该去库中去查询一下是否已经添加过团购商品，应该按库中为准）
	 * 3.将团购商品放置购物车中
	 * @return 成功或失败
	 */
	@SuppressWarnings("unused")
	private JsonResult addProductToCart(Product product,Integer num,Integer showCartData){
		String sessionid =ThreadContextHolder.getHttpRequest().getSession().getId();
		GroupBuy groupbuy= groupBuyManager.getBuyGoodsId(product.getGoods_id());
		if(product!=null){
			try{
				if(groupbuy.getGoods_num()<num){
					throw new RuntimeException("抱歉！您所选选择的货品库存不足。");
				}
				if(groupbuy.getLimit_num()<num){
					throw new RuntimeException("抱歉！您所选选择的货品数量超过了此商品的限购数量。");
				}
				Cart cart = new Cart();
				cart.setGoods_id(product.getGoods_id());
				cart.setProduct_id(product.getProduct_id());
				cart.setSession_id(sessionid);
				cart.setNum(num);
				cart.setItemtype(0); //0为product和产品 ，当初是为了考虑有赠品什么的，可能有别的类型。
				cart.setWeight(product.getWeight());
				cart.setPrice(groupbuy.getPrice());
				cart.setName(product.getName());
				
				this.cartManager.add(cart);
				
		
				//需要同时显示购物车信息
				if(showCartData!=null&&showCartData==1){
					this.getCartData();
				}
				
				return JsonResultUtil.getSuccessJson("添加货品成功");
			}catch(RuntimeException e){
				e.printStackTrace();
				Logger logger = Logger.getLogger(getClass());
				logger.error("将货品添加至购物车出错",e);
				return JsonResultUtil.getErrorJson("将货品添加至购物车出错["+e.getMessage()+"]");
			}
		}else{
			return JsonResultUtil.getErrorJson("该货品不存在，未能添加到购物车");
		}
	}
}
