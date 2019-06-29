package com.enation.app.groupbuy.component.plugin.act;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.base.core.plugin.job.IEveryDayExecuteEvent;
import com.enation.app.groupbuy.component.plugin.GroupbuyPluginBundle;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.plugin.AutoRegisterPlugin;
import com.enation.framework.util.DateUtil;
/**
 * 
 * @ClassName: GroupBuyActPlugin 
 * @Description: 团购活动插件 
 * @author TALON 
 * @date 2015-7-31 上午10:37:41 
 *
 */
@Component
public class GroupBuyActPlugin extends AutoRegisterPlugin implements IEveryDayExecuteEvent{

	@Autowired
	private IDaoSupport daoSupport;

	@Autowired
	private GroupbuyPluginBundle groupbuyPluginBundle;
	/**
	 * 当团购达到结束时间就关闭团购
	 * 当团购达到开始时间就开启团购
	 */
	@Override
	public void everyDay() {
		String sql="";
		Integer actId=0;
		//查询是否有需要开启的团购
		if(this.daoSupport.queryForInt("SELECT count(act_id) FROM es_groupbuy_active WHERE act_status=0 AND add_time<?", DateUtil.getDateline())>0){
			//开启团购
			sql="UPDATE es_groupbuy_active SET act_status=1  WHERE act_status=0 AND add_time<?";
			this.daoSupport.execute(sql, DateUtil.getDateline());
			actId=this.daoSupport.getLastId("es_groupbuy_active");
			groupbuyPluginBundle.onGroupBuyStart(actId);
		}
		//查询是否有需要关闭的团购
		if(this.daoSupport.queryForInt("SELECT count(act_id) FROM es_groupbuy_active WHERE act_status=1 AND end_time<?", DateUtil.getDateline())>0){
			List<Map<String, Object>> list=this.daoSupport.queryForList("SELECT act_id FROM es_groupbuy_active WHERE act_status=1 AND end_time<?", DateUtil.getDateline());
			if(list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					//关闭团购
					groupbuyPluginBundle.onGroupBuyEnd(Integer.parseInt(list.get(i).get("act_id").toString()));
				}
			}
			sql="UPDATE es_groupbuy_active SET act_status=2  WHERE act_status=1 AND end_time<?";
			this.daoSupport.execute(sql, DateUtil.getDateline());


		}

	}
}
