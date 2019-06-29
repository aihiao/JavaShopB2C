package com.enation.app.groupbuy.component.plugin.act;

/**
 * 
 * @ClassName: IGroupBuyActStartEvent 
 * @Description: 团购活动开启事件
 * @author TALON 
 * @date 2015-7-31 上午10:41:01 
 *
 */
public interface IGroupBuyActStartEvent {

	/**
	 * 
	 * @Title: onGroupBuyStart
	 * @Description: 开启团购活动
	 * @param @param act_id 团购活动Id
	 * @return void 
	 */
	public void onGroupBuyStart(Integer act_id);
}
