package com.enation.app.shop.api;

import com.enation.app.base.core.service.IAdvManager;
import com.enation.app.shop.core.goods.model.Brand;
import com.enation.app.shop.core.goods.model.Cat;
import com.enation.app.shop.core.goods.model.Goods;
import com.enation.app.shop.core.goods.model.Tag;
import com.enation.app.shop.core.goods.service.*;
import com.enation.framework.action.JsonResult;
import com.enation.framework.database.Page;
import com.enation.framework.util.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Sylow
 * @Description 小程序首页API
 * @Date: Created in 22:25 2018/6/28
 */

@Controller
@RequestMapping("/api/shop/wx-index")
@Scope("prototype")
public class WxIndexController {

    @Autowired
    private IAdvManager iAdvManager;
    @Autowired
    private ITagManager tagManager;
    @Autowired
    private IGoodsCatManager goodsCatDbManager;
    @Autowired
    private IGoodsManager goodsManager;
    @Autowired
    private IGoodsTagManager goodsTagManager;
    @Autowired
    private IBrandManager brandManager;

    @ResponseBody
    @RequestMapping(value="/index",produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult index() {
        Map<String, Object> result = new HashMap<String, Object>();


        //获取广告列表
//        List advList = this.iAdvManager.listAdv(Long.parseLong("1"));
        List advList = this.iAdvManager.listKeywordAdv("home_index");
        //根据关键字获取标签下的品牌
        List brandList = this.brandManager.listBrandsByKeyword("eat-brand");
        //获取允许显示在首页的分类
        List<Cat> channel = this.goodsCatDbManager.queryAllList(1);
        //每个分类下按照销量从高到低获取10个商品
        List<Cat> floorGoodsList = this.goodsCatDbManager.queryField(1);
        for (Cat cat : floorGoodsList) {
            Map map = new HashMap();
            map.put("catid", cat.getCat_id());
            List list = this.goodsManager.searchGoods(map);
            cat.setGoodsList(list);
        }
        //根据关键字获取热门商品
        List hotGoodsList = this.goodsTagManager.getForKeyword("hot-goods");
        //根据关键字获取新增商品
        List newGoodsList = this.goodsTagManager.getForKeyword("new-goods");

        result.put("banner",advList);
        result.put("brandList", brandList);
        result.put("channel", channel);
        result.put("floorGoodsList", floorGoodsList);
        result.put("hotGoodsList", hotGoodsList);
        result.put("newGoodsList", newGoodsList);

        return JsonResultUtil.getObjectJson(result);
    }



}
