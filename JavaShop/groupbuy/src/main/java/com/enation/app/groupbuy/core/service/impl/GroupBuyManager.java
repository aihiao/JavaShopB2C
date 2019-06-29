package com.enation.app.groupbuy.core.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.groupbuy.core.model.GroupBuy;
import com.enation.app.groupbuy.core.model.GroupBuyActive;
import com.enation.app.groupbuy.core.service.IGroupBuyActiveManager;
import com.enation.app.groupbuy.core.service.IGroupBuyManager;
import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
import com.enation.app.shop.core.goods.model.Goods;
import com.enation.app.shop.core.goods.plugin.GoodsPluginBundle;
import com.enation.app.shop.core.goods.service.IGoodsCatManager;
import com.enation.app.shop.core.goods.service.IGoodsManager;
import com.enation.eop.SystemSetting;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.eop.sdk.utils.StaticResourcesUtil;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;

/**
 * 团购管理类
 * @author Kanon
 *
 */
@Service("groupBuyManager")
public class GroupBuyManager implements IGroupBuyManager{
	
	@Autowired
	private IDaoSupport daoSupport;
	
	@Autowired
	private IGoodsGalleryManager goodsGalleryManager;
	
	@Autowired
	private IGroupBuyActiveManager groupBuyActiveManager;
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#add(com.enation.app.shop.component.groupbuy.model.GroupBuy)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int add(GroupBuy groupBuy) {
		String filepath=StaticResourcesUtil.convertToUrl(groupBuy.getImg_url());
		
		String thumbnail = getThumbPath(filepath, "_thumbnail");
		String wap_thumbnail = getThumbPath(filepath, "_wap");
		
		//生成缩略图
		this.process(groupBuy, thumbnail, wap_thumbnail);
		
		groupBuy.setAdd_time(DateUtil.getDateline());
		this.daoSupport.insert("es_groupbuy_goods", groupBuy);
		return this.daoSupport.getLastId("es_groupbuy_goods");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#update(com.enation.app.shop.component.groupbuy.model.GroupBuy)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(GroupBuy groupBuy) {
		
		String filepath=StaticResourcesUtil.convertToUrl(groupBuy.getImg_url());
		
		String thumbnail = getThumbPath(filepath, "_thumbnail");
		String wap_thumbnail = getThumbPath(filepath, "_wap");
		
		//生成缩略图
		this.process(groupBuy, thumbnail, wap_thumbnail);
		
		this.daoSupport.update("es_groupbuy_goods", groupBuy, "gb_id="+groupBuy.getGb_id());	
		//this.daoSupport.execute("update es_groupbuy_goods set gb_status=0 where gb_id="+groupBuy.getGb_id());
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#delete(int)
	 */
	@Override
	public void delete(int gbid) {
		String sql="delete from  es_groupbuy_goods where gb_id=?";
		this.daoSupport.execute(sql, gbid);
		this.daoSupport.execute("update es_groupbuy_active set goods_num=goods_num-1 where act_id=(select act_id from es_groupbuy_goods where gb_id=? and gb_status=1)", gbid);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#auth(int, int)
	 */
	@Override
	public void auth(int gbid, int status) {
		String sql="update es_groupbuy_goods set gb_status=? where gb_id=?";
		this.daoSupport.execute(sql, status,gbid);
		//获取审核的团购
		//更改商品为团购商品
		GroupBuy groupBuy=(GroupBuy)this.daoSupport.queryForObject("select * from es_groupbuy_goods where gb_id=?", GroupBuy.class, gbid);
		
		if(status==1){
			//修改团购活动添加已经参与团购商品
			this.daoSupport.execute("update es_groupbuy_active set goods_num=goods_num+1 where act_id=?", groupBuy.getAct_id());	
			//修改商品为已团购
			GroupBuyActive active=groupBuyActiveManager.get(groupBuy.getAct_id());
			if(active.getAct_status()==1){
				sql="update es_goods set is_groupbuy=1 where goods_id=?";
				this.daoSupport.execute(sql, groupBuy.getGoods_id());
			}
			//向标签中添加团购
			sql="insert into es_tag_rel (tag_id,rel_id,ordernum) values(?,?,?)";
			this.daoSupport.execute(sql, active.getAct_tag_id(),groupBuy.getGoods_id(),0);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#listByActId(int, int, int, java.lang.Integer)
	 */
	@Override
	public Page listByActId(int page, int pageSize, int actid, Integer status) {
		StringBuffer sql = new StringBuffer();
		sql.append("select g.*,a.act_name,a.start_time,a.end_time from es_groupbuy_goods g ,es_groupbuy_active a ");
		sql.append(" where g.act_id= ? and  g.act_id= a.act_id");
		if(status!=null ){
			sql.append(" and g.gb_status="+status);
		}
		sql.append(" order by g.add_time ");
		return this.daoSupport.queryForPage(sql.toString(),page,pageSize, actid);
	}
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#search(int, int, java.lang.Integer, java.lang.Double, java.lang.Double, int, int, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page search(int page, int pageSize, Integer catid, Double minprice,
			Double maxprice, Integer sort_key, Integer sort_type, Integer search_type,Integer area_id) {
		StringBuffer sql= new StringBuffer("select gg.*,g.thumbnail AS g_thumbnail from es_groupbuy_goods gg inner join es_goods g ON g.goods_id=gg.goods_id  where gg.gb_status=1 and g.disabled=0 and g.market_enable=1");
		if(catid!=0){
		sql.append(" and gg.cat_id="+catid);
		}
		
		if(minprice!=null){
			sql.append(" and gg.price>="+minprice);
		}
		
		
		if(maxprice!=null&&maxprice!=0){
			sql.append(" and gg.price<="+maxprice);
		}
		
		if(sort_type==0){
			sql.append(" and gg.act_id in (select act_id from es_groupbuy_active where end_time <"+DateUtil.getDateline()+")");
		}
	
		//
		GroupBuyActive active = groupBuyActiveManager.get();
		int actid=0;
		if(active!=null){
			actid = active.getAct_id();
		}
		if(sort_type==1&&catid==0){
			sql.append(" and gg.act_id="+actid);
		}
		
		if(sort_type==1&&catid!=0){
			sql.append(" and gg.act_id="+actid);
		}
		if(sort_type==2&&catid!=0){
			sql.append(" and gg.act_id in (select act_id from es_groupbuy_active where start_time >"+DateUtil.getDateline()+")");
		}
		
		if(sort_type==2&&catid==0){
			sql.append(" and gg.act_id in (select act_id from es_groupbuy_active where start_time >"+DateUtil.getDateline()+")");
		}
		if(area_id!=0){
			sql.append(" and gg.area_id="+area_id);
		}
		if(sort_key==0){
			sql.append(" order by gg.add_time ");
		}
		
		if(sort_key==1){
			sql.append(" order by gg.price ");
		}
		
		if(sort_key==2){
			sql.append(" order by gg.price/gg.original_price ");
		}
		
		if(sort_key==3){
			sql.append(" order by gg.buy_num ");
		}
		
		return this.daoSupport.queryForPage(sql.toString(), page, pageSize);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#get(int)
	 */
	@Override
	public GroupBuy get(int gbid) {
		String sql ="select * from es_groupbuy_goods where gb_id=?";
		GroupBuy groupBuy = (GroupBuy)this.daoSupport.queryForObject(sql, GroupBuy.class, gbid);
		sql="select * from es_goods where goods_id=?";
		
		Goods goods  = (Goods)this.daoSupport.queryForObject(sql, Goods.class, groupBuy.getGoods_id());
		groupBuy.setGoods(goods);
		
		return groupBuy;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.component.groupbuy.service.IGroupBuyManager#getBuyGoodsId(int)
	 */
	@Override
	public GroupBuy getBuyGoodsId(int goodsId) {
		String sql="SELECT * from es_groupbuy_goods WHERE goods_id=? and act_id=?";
		GroupBuy groupBuy= (GroupBuy)this.daoSupport.queryForObject(sql, GroupBuy.class, goodsId,groupBuyActiveManager.get().getAct_id());
		groupBuy.setGoods((Goods)this.daoSupport.queryForObject("select * from es_goods where goods_id=?", Goods.class, groupBuy.getGoods_id()));
		return  groupBuy;
	}
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.groupbuy.core.service.IGroupBuyManager#getBuyGoodsList(int)
	 */
	@Override
	public List<GroupBuy> getBuyGoodsList(int goodsId) {
		String sql="SELECT * from es_groupbuy_goods WHERE goods_id=? and act_id=?";
		List<GroupBuy> queryForList = this.daoSupport.queryForList(sql, GroupBuy.class, goodsId,groupBuyActiveManager.get().getAct_id());
		//groupBuy.setGoods((Goods)this.daoSupport.queryForObject("select * from es_goods where goods_id=?", Goods.class, groupBuy.getGoods_id()));
		return  queryForList;
	}
	/**
	 * 进行压缩
	 * @param filepath
	 */
	private void process(GroupBuy groupbuy,String thumbnail,String wap_thumbnail){
		int width = 800;
		int height = 800;
		
		int wap_width=400;
		int wap_height=400;
		String filepath=StaticResourcesUtil.convertToUrl(groupbuy.getImg_url());
		//生成团购缩略图
		createThumb(filepath, thumbnail, width, height);
		thumbnail = transformPath(thumbnail);
		groupbuy.setThumbnail(thumbnail);
		
		//生成wap团购缩略图
		createThumb(filepath, wap_thumbnail, wap_width, wap_height);
		wap_thumbnail = transformPath(wap_thumbnail);
		groupbuy.setWap_thumbnail(wap_thumbnail);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.groupbuy.core.service.IGroupBuyManager#storeGoodsList(int, java.util.Map)
	 */
	@Override
	public List<Map> storeGoodsList(int storeid, Map map) {
		StringBuffer sql=new StringBuffer("SELECT g.* from es_goods g where g.store_id="+storeid +" and  g.disabled=0");
		String store_catid=String.valueOf(map.get("store_catid"));
		String keyword=String.valueOf(map.get("keyword"));
		String act_id=String.valueOf(map.get("act_id"));
		if(!StringUtil.isEmpty(store_catid) && !"0".equals(store_catid)){ //按店铺分类搜索
			sql.append(" and g.store_cat_id="+store_catid);
		}
		
		if(!StringUtil.isEmpty(keyword) ){
			sql.append(" and ((g.name like '%"+keyword+"%') or (g.sn like '%"+keyword+"%') )");
		}
		if(!StringUtil.isEmpty(act_id)){
			sql.append(" AND g.goods_id NOT IN(select goods_id from es_groupbuy_goods where act_id="+act_id+")");
		}
		return this.daoSupport.queryForList(sql.toString());
	}
	/**
	 * 获取缩略图地址
	 * @param filePath 图片原生地址
	 * @param shortName 替换名称
	 * @return
	 */
	private String getThumbPath(String filePath, String shortName) {
		return StaticResourcesUtil.getThumbPath(filePath, shortName);
	}
	/**
	 * 生成商品的缩略图
	 * @param filepath 图片原生地址
	 * @param targetpath 图片缩略图地址
	 * @param pic_width 缩略图宽度
	 * @param pic_height 缩略图高度
	 */
	private void createThumb(String filepath, String targetpath, int pic_width, int pic_height) {
		try {
			this.goodsGalleryManager.createThumb(filepath, targetpath, pic_width, pic_height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 页面中传递过来的图片地址为:http://<staticserver>/<image path>如:
	 * http://static.enationsoft.com/attachment/goods/1.jpg
	 * 存在库中的为fs:/attachment/goods/1.jpg 生成fs式路径
	 * 
	 * @param path
	 * @return
	 */
	private String transformPath(String path) {
		String static_server_domain= SystemSetting.getStatic_server_domain();

		String regx =static_server_domain;
		path = path.replace(regx, EopSetting.FILE_STORE_PREFIX);
		return path;
	}

	
}
