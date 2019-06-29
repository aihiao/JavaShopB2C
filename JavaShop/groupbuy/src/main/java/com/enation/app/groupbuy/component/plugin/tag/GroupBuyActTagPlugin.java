package com.enation.app.groupbuy.component.plugin.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActAddEvent;
import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActDeleteEvent;
import com.enation.app.groupbuy.core.model.GroupBuyActive;
import com.enation.app.groupbuy.core.model.GroupBuyTag;
import com.enation.app.groupbuy.core.service.IGroupBuyActiveManager;
import com.enation.app.groupbuy.core.service.IGroupGoodsTagManager;
import com.enation.app.shop.core.goods.model.Tag;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.plugin.AutoRegisterPlugin;
/**
 * 
 * @ClassName: GroupBuyActTagPlugin 
 * @Description: 团购活动标签 插件 
 * @author TALON 
 * @date 2015-7-31 上午10:44:27 
 *
 */
@Component
public class GroupBuyActTagPlugin extends AutoRegisterPlugin implements IGroupBuyActAddEvent,IGroupBuyActDeleteEvent{
	
	@Autowired
	private IDaoSupport daoSupport;
	
	@Autowired
	private IGroupGoodsTagManager groupGoodsTagManager;
	
	@Autowired
	private IGroupBuyActiveManager groupBuyActiveManager;
	/**
	 * 当团购活动开启添加团购商品标签
	 */
	@Override
	public void onAddGroupBuyAct(GroupBuyActive groupBuyActive) {
		Tag tag=new Tag();
		GroupBuyTag storeTag=new GroupBuyTag();
		storeTag.setIs_groupbuy(groupBuyActive.getAct_id());
		storeTag.setTag_name(groupBuyActive.getAct_name()+"  热门团购");
		
		groupGoodsTagManager.add(storeTag);
		//将标签id传递给团购活动
		this.daoSupport.execute("update es_groupbuy_active set act_tag_id=? where act_id=?", this.daoSupport.getLastId("es_groupbuy_active"),groupBuyActive.getAct_id());
		
	}
	/**
	 * 当删除团购活动删除团购商品标签
	 */
	@Override
	public void onDeleteGroupBuyAct(Integer act_id) {
		GroupBuyActive groupBuyActive= groupBuyActiveManager.get(act_id);
		this.daoSupport.execute("delete from es_tag_rel where tag_id=(select tag_id from es_tags where is_groupbuy=?)",act_id);
		this.daoSupport.execute("delete from es_tags where is_groupbuy=?",act_id);
	}
	
}
