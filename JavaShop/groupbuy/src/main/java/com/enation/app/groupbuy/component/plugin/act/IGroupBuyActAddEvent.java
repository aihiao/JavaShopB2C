package com.enation.app.groupbuy.component.plugin.act;

import com.enation.app.groupbuy.core.model.GroupBuyActive;


/**
 * 
 * @ClassName: IGroupBuyActAddEvent 
 * @Description: 添加团购活动事件
 * @author TALON 
 * @date 2015-7-31 上午10:38:36 
 *
 */
public interface IGroupBuyActAddEvent {
	/**
	 * 
	 * @Title: onAddGroupBuyAct
	 * @Description: 添加团购活动
	 * @param @param groupBuyActive 团购活动
	 * @return void    
	 */
	public void onAddGroupBuyAct(GroupBuyActive groupBuyActive);
}
