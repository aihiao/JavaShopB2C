package com.enation.app.groupbuy.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enation.app.groupbuy.core.model.GroupBuyTag;
import com.enation.app.groupbuy.core.service.IGroupGoodsTagManager;
import com.enation.app.shop.core.goods.model.Cat;
import com.enation.app.shop.core.goods.plugin.search.GoodsDataFilterBundle;
import com.enation.app.shop.core.goods.service.IGoodsCatManager;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.StringUtil;

/**
 * 团购标签管理类
 * @author Kanon
 *
 */
@Service("groupGoodsTagManager")
public class GroupGoodsTagManager implements IGroupGoodsTagManager{
	
	@Autowired
	private IDaoSupport daoSupport;
	
	@Autowired
	private IGoodsCatManager goodsCatManager;
	
	@Autowired
	private GoodsDataFilterBundle goodsDataFilterBundle;
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.b2b2c.core.service.goods.IB2b2cGoodsTagManager#list(int, int)
	 */
	@Override
	public Page list(int pageNo, int pageSize) {
		if(EopSetting.PRODUCT.equals("b2b2c")){
			return this.daoSupport.queryForPage("select * from es_tags where store_id is NULL or is_groupbuy is not NULL order by tag_id", pageNo, pageSize);
		}else{
			return this.daoSupport.queryForPage("select * from es_tags  order by tag_id", pageNo, pageSize);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.b2b2c.core.service.goods.IB2b2cGoodsTagManager#groupBuyList(int, java.util.Map, int, int, java.lang.String, java.lang.String)
	 */
	@Override
	public Page groupBuyList(int tagid, Map goodsMap, int page, int pageSize,
			String sort, String order) {
		String sql = getListSql(tagid);
		Integer brandid = (Integer) goodsMap.get("brandid");
		Integer catid = (Integer) goodsMap.get("catid");
		String name = (String) goodsMap.get("name");
		String sn = (String) goodsMap.get("sn");
		Integer stype = (Integer) goodsMap.get("stype");
		String keyword = (String) goodsMap.get("keyword");
		
		if (brandid != null && brandid != 0) {
			sql += " and g.brand_id = " + brandid + " ";
		}
		
		if(stype!=null && keyword!=null){			
			if(stype==0){
				sql+=" and ( g.name like '%"+keyword+"%'";
				sql+=" or g.sn like '%"+keyword+"%')";
			}
		}
		
		if (name != null && !name.equals("")) {
			name = name.trim();
			String[] keys = name.split("\\s");
			for (String key : keys) {
				sql += (" and g.name like '%");
				sql += (key);
				sql += ("%'");
			}
		}

		if (sn != null && !sn.equals("")) {
			sql += "   and g.sn like '%" + sn + "%'";
		}


		if (catid != null && catid!=0) {
			Cat cat = this.goodsCatManager.getById(catid);
			sql += " and  g.cat_id in(";
			sql += "select c.cat_id from es_goods_cat"
					+ " c where c.cat_path like '" + cat.getCat_path()
					+ "%')  ";
		}
		//System.out.println(sql);
		return this.daoSupport.queryForPage(sql, page, pageSize );
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.groupbuy.core.service.IGroupGoodsTagManager#add(com.enation.app.groupbuy.core.model.GroupBuyTag)
	 */
	@Override
	public void add(GroupBuyTag tag) {
		this.daoSupport.insert("es_tags", tag);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.enation.app.groupbuy.core.service.IGroupGoodsTagManager#listGoods(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List listGoods(String catid, String tagid, String goodsnum) {
		int num = 10;
		if(!StringUtil.isEmpty(goodsnum)){
			num = Integer.valueOf(goodsnum);
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select g.*,gb.price as gb_price,gb.original_price as original_price,gb.gb_title as gb_title,gb.thumbnail as gb_thumbnail from es_tag_rel r LEFT JOIN es_goods g ON g.goods_id=r.rel_id INNER JOIN es_groupbuy_goods gb ON gb.goods_id=g.goods_id where gb.gb_status=1 and g.disabled=0 and g.market_enable=1");
		
		if(! StringUtil.isEmpty(catid) ){
			Cat cat  = this.goodsCatManager.getById(Integer.valueOf(catid));
			if(cat!=null){
				String cat_path  = cat.getCat_path();
				if (cat_path != null) {
					sql.append( " and  g.cat_id in(" ) ;
					sql.append("select c.cat_id from es_goods_cat");
					sql.append(" c where c.cat_path like '" + cat_path + "%')");
				}
			}
		}
		
		if(!StringUtil.isEmpty(tagid)){
			sql.append(" AND r.tag_id="+tagid+"");
		}
		
		sql.append(" order by r.ordernum desc");
		List list = this.daoSupport.queryForListPage(sql.toString(), 1,num);
		this.goodsDataFilterBundle.filterGoodsData(list);
		return list;
	}
	/**
	 * 获取列表Sql
	 * @param tag_id
	 * @return
	 */
	private String getListSql(int tag_id) {
		String 	sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name  from es_goods g left join es_goods_cat c on g.cat_id=c.cat_id left join es_brand  b on g.brand_id = b.brand_id and b.disabled=0 left join es_goods_type";
				sql+= " t on g.type_id =t.type_id  where g.goods_type = 'normal' and g.disabled=0 and goods_id in (select goods_id from es_groupbuy_goods where act_id=(select act_id from es_groupbuy_active where act_tag_id="+tag_id+"))";
		return sql;
	}
}
