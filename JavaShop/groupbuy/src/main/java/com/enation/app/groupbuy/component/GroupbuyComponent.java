package com.enation.app.groupbuy.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enation.app.base.core.model.SiteMenu;
import com.enation.app.base.core.service.ISiteMenuManager;
import com.enation.app.base.core.service.auth.IAuthActionManager;
import com.enation.app.base.core.service.auth.IPermissionManager;
import com.enation.app.base.core.service.auth.impl.PermissionConfig;
import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
import com.enation.eop.SystemSetting;
import com.enation.eop.resource.IMenuManager;
import com.enation.eop.resource.model.Menu;
import com.enation.framework.component.IComponent;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.database.IDaoSupport;
import com.enation.framework.database.data.IDataOperation;
/**
 * 
 * @ClassName: GroupbuyComponent 
 * @Description: 团购组件 
 * @author TALON 
 * @date 2015-7-31 上午12:06:08 
 *
 */
@Component
public class GroupbuyComponent implements IComponent {
	private static final String parentMenuName="促销";
	
	
	@Autowired
	private IDataOperation dataOperation;
	
	
	@Autowired
	private IMenuManager menuManager;
	
	@Autowired
	private IDaoSupport daoSupport;
	
	@Autowired
	private IAuthActionManager authActionManager;
	
	@Autowired
	private ISiteMenuManager siteMenuManager;
	
	@Autowired
	private IPermissionManager permissionManager;
	/**
	 * 启动组件
	 * 1.执行组件更改数据库（添加团购相关表、修改商品、标签表结构）。
	 * 2.执行sql修改演示数据。
	 * 3.添加团购菜单（促销）。
	 * 4.修改菜单内容，修改商品标签设置url指向地址。
	 */
	@Override
	public void install() {
		this.dataOperation.imported("file:com/enation/app/groupbuy/component/groupbuy_install.xml");
		this.daoSupport.execute("update es_goods set is_groupbuy=0");
		this.addMenu();
		this.editMenu();
	}
	/**
	 * 卸载组件
	 * 1.删除后台菜单
	 * 2.因怕影响已有数据已经添加如团购所以这里并没有去删除数据库字段以及表。
	 * 3.修改菜单将商品标签设置url还原。
	 */
	@Override
	public void unInstall() {
		delete_menu();
		back();
		
		//删除团购商品表
		this.daoSupport.execute("DROP TABLE es_groupbuy_goods");
		
		//删除团购活动
		this.daoSupport.execute("DROP TABLE es_groupbuy_active");
		
		//删除团购分类表
		this.daoSupport.execute("DROP TABLE es_groupbuy_cat");
		
		//删除团购地区表
		this.daoSupport.execute("DROP TABLE es_groupbuy_area");

		//还原商品字段
		this.daoSupport.execute("ALTER TABLE es_goods DROP  column  is_groupbuy");
		
		//还原标签表
		this.daoSupport.execute("ALTER TABLE es_tags DROP column  is_groupbuy");
		
		 
	}
	/**
	 * 为组件添加菜单
	 * 1.判断是否为超级管理员，如果不是超级管理员不进行操作
	 * 2.添加菜单：团购管理、团购活动、团购地区、团购分类
	 * 3.添加前台导航栏：团购
	 */
	private void addMenu(){
		//判断是否为超级管理员
		boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));//超级管理员权限
		if(!isSuperAdmin)
			return;
		//添加主目录:团购管理
		Menu parentMenu = menuManager.get(parentMenuName);
		Menu menu = new Menu();
		menu.setTitle("团购管理");
		menu.setPid(parentMenu.getId());
		menu.setUrl("");
		menu.setSorder(50);
		menu.setMenutype(Menu.MENU_TYPE_APP);
		this.menuManager.add(menu);
		Integer menu_id=this.daoSupport.getLastId("es_menu");
		
		//添加子目录:团购活动
		menu.setTitle("团购活动");
		menu.setPid(menu_id);
		menu.setUrl("/shop/admin/group-buy-act/list.do");
		this.menuManager.add(menu);
		
		//添加子目录:团购地区
		menu.setTitle("团购地区");
		menu.setUrl("/shop/admin/group-buy-area/list.do");
		this.menuManager.add(menu);
		
		//添加子目录:团购分类
		menu.setTitle("团购分类");
		menu.setUrl("/shop/admin/group-buy-cat/list.do");
		this.menuManager.add(menu);
		
		//添加导航栏：团购
		SiteMenu siteMenu=new SiteMenu();
		siteMenu.setName("团购");
		siteMenu.setParentid(0);
		siteMenu.setUrl("groupbuy/groupbuy.html");
		siteMenu.setSort(3);
		siteMenu.setTarget("");
		siteMenuManager.add(siteMenu);
		//将session中的菜单列表数据移除
		ThreadContextHolder.getSession().removeAttribute(SystemSetting.menuListKey.toString());
	}
	
	/**
	 * 为组件删除菜单
	 * 1.判断是否为超级管理员，如不是不进行操作
	 * 2.删除菜单：团购管理、团购活动、团购地区、团购分类
	 * 3.删除导航：团购
	 */
	private void delete_menu(){
		//判断是否为超级管理员
		boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));//超级管理员权限
		if(!isSuperAdmin)
			return;
		int superAdminAuthId = PermissionConfig.getAuthId("super_admin"); // 超级管理员权限id
		
		//删除主目录：团购管理
		Menu menu =  menuManager.get("团购管理");
		if(menu!=null){
			int addmenuid =menu.getId();
			this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] {addmenuid });
			this.menuManager.delete("团购管理");
		}
		
		//删除子目录：团购活动
		 menu =  menuManager.get("团购活动");
		if(menu!=null){
			int addmenuid =menu.getId();
			this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] {addmenuid });
			this.menuManager.delete("团购活动");
		}
		
		//删除子目录：团购地区
		 menu =  menuManager.get("团购地区");
		if(menu!=null){
			int addmenuid =menu.getId();
			this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] {addmenuid });
			this.menuManager.delete("团购地区");
		}
		
		//删除子目录：团购分类
		 menu =  menuManager.get("团购分类");
		if(menu!=null){
			int addmenuid =menu.getId();
			this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] {addmenuid });
			this.menuManager.delete("团购分类");
		}
		
		//删除导航栏：团购
		SiteMenu siteMenu= siteMenuManager.get("团购");
		siteMenuManager.delete(siteMenu.getMenuid());
		
		//将session中的菜单列表数据移除
		ThreadContextHolder.getSession().removeAttribute(SystemSetting.menuListKey.toString());
	}
	/**
	 * 修改商品标签设置url指向action
	 */
	public void editMenu(){
		Menu menu=menuManager.get("标签商品设置");
		menu.setUrl("/shop/admin/group-buy-tag/list.do");
		this.menuManager.edit(menu);
		//将session中的菜单列表数据移除
		ThreadContextHolder.getSession().removeAttribute(SystemSetting.menuListKey.toString());
	}
	/**
	 * 还原菜单:商品标签设置
	 */
	public void back(){
		Menu menu=menuManager.get("标签商品设置");
		menu.setUrl("/shop/admin/goods-show/tag-list.do");
		this.menuManager.edit(menu);
		//将session中的菜单列表数据移除
		ThreadContextHolder.getSession().removeAttribute(SystemSetting.menuListKey.toString());
	}
}
