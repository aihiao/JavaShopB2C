package com.enation.app.groupbuy.component.plugin.act;

/**
 * 
 * @ClassName: IGroupBuyActDeleteEvent 
 * @Description: 团购活动删除事件 
 * @author TALON 
 * @date 2015-7-31 上午10:40:26 
 *
 */
public interface IGroupBuyActDeleteEvent {
	/**
	 * 
	 * @Title: onDeleteGroupBuyAct
	 * @Description: 删除团购活动
	 * @param @param act_id 团购活动Id
	 * @return void 
	 */
	public void onDeleteGroupBuyAct(Integer act_id);
	
}
