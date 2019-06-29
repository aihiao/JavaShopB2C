--  添加字段
ALTER TABLE `es_goods_cat` ADD COLUMN `index_show` smallint(1) DEFAULT NULL COMMENT '是否显示在首页';
ALTER TABLE `es_tags` ADD COLUMN `goods_keyword` varchar(255) DEFAULT NULL COMMENT '判断商品类型，英文字段';
ALTER TABLE `es_tag_rel` ADD COLUMN `goods_keyword` varchar(255) DEFAULT NULL;
ALTER TABLE `es_tag_relb` ADD COLUMN `goods_keyword` varchar(255) DEFAULT NULL;
ALTER TABLE `es_adcolumn` ADD COLUMN `keyword` varchar(255) DEFAULT NULL COMMENT '首页最上方广告搜索关键字';
ALTER TABLE `es_adv` ADD COLUMN `keyword` varchar(255) DEFAULT NULL;

--  添加数据
update es_tag_rel set goods_keyword='hot-goods' where tag_id=1;

--  添加字段（es_goods_cat）字段reveal   xiongchengcheng 2018-8-24
ALTER TABLE `es_goods_cat` ADD  `reveal` int(8) DEFAULT 0 COMMENT '是否显示在app首页';

-- 添加示例数据  es_adcolumn xiongchangcheng 2018-08-27
INSERT INTO es_adcolumn (acid,cname,width,height,description,anumber,atype,arule,disabled,keyword) VALUES (50,'app首页最上方广告','1440px','178px',NULL,NULL,0,NULL,'false','show');
INSERT INTO es_adcolumn (acid,cname,width,height,description,anumber,atype,arule,disabled,keyword) VALUES (51,'app品牌','760px','484px',NULL,NULL,0,NULL,'false','brand');

-- 添加示例数据  es_adv xiongchangcheng 2018-08-27
INSERT INTO es_adv (aid,acid,atype,begintime,endtime,isclose,attachment,atturl,url,aname,clickcount,linkman,company,contact,disabled,keyword) VALUES (50,50,NULL,NULL,NULL,0,NULL,'https://i.imgur.com/e7Q6ZmB.jpg','index.html','app上方轮播图',0,NULL,NULL,NULL,'false','show');
INSERT INTO es_adv (aid,acid,atype,begintime,endtime,isclose,attachment,atturl,url,aname,clickcount,linkman,company,contact,disabled,keyword) VALUES (51,50,NULL,NULL,NULL,0,NULL,'https://i.imgur.com/g3ttdHt.jpg','index.html','app上方轮播图',0,NULL,NULL,NULL,'false','show');
INSERT INTO es_adv (aid,acid,atype,begintime,endtime,isclose,attachment,atturl,url,aname,clickcount,linkman,company,contact,disabled,keyword) VALUES (52,50,NULL,NULL,NULL,0,NULL,'https://i.imgur.com/pDxPQ1k.jpg','index.html','app上方轮播图',0,NULL,NULL,NULL,'false','show');
INSERT INTO es_adv (aid,acid,atype,begintime,endtime,isclose,attachment,atturl,url,aname,clickcount,linkman,company,contact,disabled,keyword) VALUES (53,51,NULL,NULL,NULL,0,NULL,'https://i.imgur.com/dCT8Htc.jpg','index.html','app品牌',0,NULL,NULL,NULL,'false','brand');
INSERT INTO es_adv (aid,acid,atype,begintime,endtime,isclose,attachment,atturl,url,aname,clickcount,linkman,company,contact,disabled,keyword) VALUES (54,51,NULL,NULL,NULL,0,NULL,'https://i.imgur.com/UPqfTJf.jpg','index.html','app品牌',0,NULL,NULL,NULL,'false','brand');
INSERT INTO es_adv (aid,acid,atype,begintime,endtime,isclose,attachment,atturl,url,aname,clickcount,linkman,company,contact,disabled,keyword) VALUES (55,51,NULL,NULL,NULL,0,NULL,'https://i.imgur.com/ejNe6rB.png','index.html','app品牌',0,NULL,NULL,NULL,'false','brand');
INSERT INTO es_adv (aid,acid,atype,begintime,endtime,isclose,attachment,atturl,url,aname,clickcount,linkman,company,contact,disabled,keyword) VALUES (56,51,NULL,NULL,NULL,0,NULL,'https://i.imgur.com/j6htkJJ.jpg','index.html','app品牌',0,NULL,NULL,NULL,'false','brand');

-- 添加示例数据  es_goods_cat xiongchangcheng 2018-08-27
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (200,'居家',0,'0|200|',0,0,1,'1','https://i.imgur.com/ltVYbI3.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (201,'餐厨',0,'0|201|',0,0,1,'1','https://i.imgur.com/AUPjjdm.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (202,'饮食',0,'0|202|',0,0,1,'1','https://i.imgur.com/Fr6VWfu.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (203,'配件',0,'0|203|',0,0,1,'1','https://i.imgur.com/XwgdYD3.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (204,'服装',0,'0|204|',0,0,1,'1','https://i.imgur.com/vvhCFTe.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (205,'婴童',0,'0|205|',0,0,1,'1','https://i.imgur.com/MihnaTY.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (206,'杂货',0,'0|206|',0,0,1,'1','https://i.imgur.com/h8WgS6w.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (207,'洗护',0,'0|207|',0,0,1,'1','https://i.imgur.com/N7sOaHw.png',0,1);
INSERT INTO es_goods_cat (cat_id,name,parent_id,cat_path,goods_count,cat_order,type_id,list_show,image,index_show,reveal) VALUES (208,'志趣',0,'0|208|',0,0,1,'1','https://i.imgur.com/HThgh79.png',0,1);