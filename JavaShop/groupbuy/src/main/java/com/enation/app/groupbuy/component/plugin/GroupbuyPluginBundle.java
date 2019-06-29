package com.enation.app.groupbuy.component.plugin;

import java.util.List;

import org.springframework.stereotype.Component;

import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActAddEvent;
import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActDeleteEvent;
import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActEndEvent;
import com.enation.app.groupbuy.component.plugin.act.IGroupBuyActStartEvent;
import com.enation.app.groupbuy.core.model.GroupBuyActive;
import com.enation.framework.plugin.AutoRegisterPluginsBundle;
import com.enation.framework.plugin.IPlugin;
/**
 * 团购插件桩
 * @author fenlongli
 *
 */
@Component
public class GroupbuyPluginBundle extends AutoRegisterPluginsBundle{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "团购插件桩";
	}
	/**
	 * 激发团购活动删除事件
	 * @param GroupBuyActive
	 */
	public void onGroupBuyDelete(Integer act_id) {
		List<IPlugin> plugins = this.getPlugins();

		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGroupBuyActDeleteEvent) {
					IGroupBuyActDeleteEvent event = (IGroupBuyActDeleteEvent) plugin;
					event.onDeleteGroupBuyAct(act_id);
				}
			}
		}
	}
	/**
	 * 激发团购活动启动事件
	 * @param GroupBuyActive
	 */
	public void onGroupBuyStart(Integer act_id) {
		List<IPlugin> plugins = this.getPlugins();

		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGroupBuyActStartEvent) {
					IGroupBuyActStartEvent event = (IGroupBuyActStartEvent) plugin;
					event.onGroupBuyStart(act_id);
				}
			}
		}
	}
	/**
	 * 激发团购活动关闭事件
	 * @param GroupBuyActive
	 */
	public void onGroupBuyEnd(Integer act_id) {
		List<IPlugin> plugins = this.getPlugins();

		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGroupBuyActEndEvent) {
					IGroupBuyActEndEvent event = (IGroupBuyActEndEvent) plugin;
					event.onEndGroupBuyEnd(act_id);
				}
			}
		}
	}
	/**
	 * 激发团购活动添加事件
	 * @param GroupBuyActive
	 */
	public void onGroupBuyAdd(GroupBuyActive groupBuyActive) {
		List<IPlugin> plugins = this.getPlugins();

		if (plugins != null) {
			for (IPlugin plugin : plugins) {
				if (plugin instanceof IGroupBuyActAddEvent) {
					IGroupBuyActAddEvent event = (IGroupBuyActAddEvent) plugin;
					event.onAddGroupBuyAct(groupBuyActive);
				}
			}
		}
	}
}
