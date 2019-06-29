package com.enation.app.groupbuy.component.plugin.act;

/**
 * 
 * @ClassName: IGroupBuyActEndEvent 
 * @Description: 团购活动关闭事件 
 * @author TALON 
 * @date 2015-7-31 上午10:40:43 
 *
 */
public interface IGroupBuyActEndEvent {

	/**
	 * 
	 * @Title: onEndGroupBuyEnd
	 * @Description: 关闭团购活动
	 * @param @param act_id 团购活动Id
	 * @return void 
	 */
	public void onEndGroupBuyEnd(Integer act_id);
}
