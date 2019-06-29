package com.enation.app.shop.core.member.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.member.model.Favorite;
import com.enation.app.shop.core.member.model.Footprint;
import com.enation.app.shop.core.member.service.IFavoriteManager;
import com.enation.app.shop.core.member.service.IFootprintManager;
import com.enation.eop.sdk.context.UserConext;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.Page;
import com.enation.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的收藏
 * 
 * @author lzf<br/>
 *         2010-3-24 下午02:54:26<br/>
 *         version 1.0<br/>
 *         
 * @version v1.1 2015-09-14
 * @author wangxin 6.0升级改造
 */
@Service("footprintManager")
public class FootprintManager implements IFootprintManager {
	
	@Autowired
	private IDaoSupport  daoSupport;	
	

	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.core.member.service.IFavoriteManager#list(int, int, int)
	 */
	@Override
	public Page list(int memberid, int pageNo, int pageSize) {
		String sql = "select g.*, f.footprint_time from es_footprint"
				+ " f left join es_goods"
				+ " g on g.goods_id = f.goods_id";
		sql += " where f.member_id = ? order by f.footprint_time desc";	//改为根据收藏时间倒序排序，最新收藏的显示在前面	修改人：DMRain 2015-12-16
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize,memberid);
		return page;
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.core.member.service.IFavoriteManager#add(java.lang.Integer)
	 */
	@Override
	public void add(Footprint footprint) {

		
		this.daoSupport.insert("es_footprint", footprint);
	}


	public void update(int footprint_id){
		//获取当前时间
		long timeNew = DateUtil.getDateline();
		this.daoSupport.execute("update es_footprint a set a.footprint_time = ? where a.footprint_id = ?",timeNew,footprint_id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.app.shop.core.member.service.IFavoriteManager#get(int, int)
	 */
	@Override
    public Footprint get(int goodsid, int memberid){
        String sql = "SELECT * FROM es_footprint WHERE goods_id=? AND member_id=?";
        List<Footprint> favoriteList = daoSupport.queryForList(sql, Footprint.class, goodsid, memberid);
        if(favoriteList.size() > 0){
            return favoriteList.get(0);
        }
        return null;
    }

	
}
