 

 INSERT INTO `SYRESOURCE` (`ID`, `PID`, `NAME`, `URL`, `COMMENTS`, `ONOFF`) VALUES
 

('1-1-0','0', '行为模块', '/behavior', '行为模块','0'),
('1-1-1', '1-1-0', '查询过的关键字', '/behavior/keyword', '查询过的关键字','0'),
('1-1-2', '1-1-0', '游览记录', '/behavior/visitinghistory', '游览记录','0'),
('1-1-3', '1-1-0', '猜Ta喜欢', '/behavior/itemrelation', '猜Ta喜欢','0'),
('1-1-4', '1-1-0', '曾经购买', '/behavior/boughtitem', '曾经购买','0'),
('1-1-5', '1-1-0', '咨询记录', '/behavior/consultation', '咨询记录','0'),
('1-1-6', '1-1-0', '未知', '/behavior/pageview', '未知','0'),
('1-2-0','0', 'CRM模块', '/crm', 'crm模块','0'),
('1-2-1', '1-2-0', '概述', '/crm/summary', '概述','0'),
('1-3-0','0', '商品模块', '/item', '商品模块','0'),
('1-3-1', '1-3-0', '跳转店铺商品信息查询页', '/item/toSearch', '跳转店铺商品信息查询页','0'),
('1-3-2', '1-3-0', '店铺商品信息查询', '/item/search', '店铺商品信息查询','0'),
('1-4-0','0', '营销模块', '/marketing', '营销模块','0'),
('1-4-1-0', '1-4-0', '优惠券子模块', '/marketing/coupon', '优惠子模块','0'),
('1-4-1-1', '1-4-1-0', '首页', '/marketing/coupon/index', '优惠券首页','0'),
('1-4-1-2', '1-4-1-0', '买家优惠券', '/marketing/coupon/buyer', '加载买家优惠券信息','0'),
('1-4-1-3', '1-4-1-0', '店铺优惠券', '/marketing/coupon/seller', '加载店铺优惠卷信息','0'),
('1-4-2-0', '1-4-0', '套餐子模块', '/marketing/meal', '套餐信息','0'),
('1-4-2-1', '1-4-2-0', '明细', '/marketing/meal/detail', '套餐明细','0'),
('1-4-3-0', '1-4-0', '限时打折子模块', '/marketing/limitdiscount', '限时打折信息','0'),
('1-4-3-1', '1-4-3-0', '明细', '/marketing/limitdiscount/detail', '限时打折明细','0'),
('1-5-0','0', '交易模块', '/trade', '交易模块','0'),
('1-5-1', '1-5-0', '物流跟踪', '/trade/trace', '物流跟踪','0'),
('1-5-2', '1-5-0', '收货地址信息', '/trade/shipping', '收货地址信息','0'),
('1-5-3', '1-5-0', '关闭交易', '/trade/close', '关闭交易','0'),
('1-5-4', '1-5-0', '备注更新', '/trade/memo/update', '备注更新','0'),
('1-5-5', '1-5-0', '显示备注', '/trade/memo/get', '显示备注','0'),
('1-5-6', '1-5-0', '修改邮费', '/trade/postage/update', '修改邮费','0'),
('1-6-0','0', '缺货/促销事件登记模块', '/leadsMemo', '缺货/促销事件登记模块','0'),
('1-6-1', '1-6-0', '跳转至缺货/减价事件登记首页', '/index', '跳转至缺货/减价事件登记首页','0'),
('1-6-2', '1-6-0', '跳转至添加页', '/leadsMemo/toSave', '跳转至添加页','0'),
('1-6-3', '1-6-0', '添加缺货/促销事件登记', '/leadsMemo/save', '添加缺货/促销事件登记','0'),
('1-6-4', '1-6-0', '跳转至跳转至缺货/促销事件修改页', '/leadsMemo/toUpdate', '跳转至跳转至缺货/促销事件修改页','0'),
('1-6-5', '1-6-0', '修改缺货/促销事件登记', '/leadsMemo/update', '修改缺货/促销事件登记','0'),
('1-6-6', '1-6-0', '载当前买家相关缺货等记信息', '/leadsMemo/promotionList', '载当前买家相关缺货等记信息','0'),
('1-6-7', '1-6-0', '加载当前买家的促销事件列表', '/leadsMemo/promotionList_detail', '加载当前买家的促销事件列表','0'),
('1-6-8', '1-6-0', '修改当前信息状态', '/leadsMemo/updateStatus', '修改当前信息状态','0'),
('1-6-9', '1-6-0', '加载信息状态个数', '/leadsMemo/loadStatusCount', '加载信息状态个数','0'),

('1-7-0','0', '售后模块', '/customerService', '售后模块','0'),
('1-7-1', '1-7-0', '售后首页', '/customerService/index', '售后首页','0'),
('1-7-2', '1-7-0', '售后问题添加页', '/customerService/open', '售后问题添加页','0'),
('1-7-3', '1-7-0', '判断当前TradeId是否存在售后问题', '/checkTradeIdExists', '判断当前TradeId是否存在售后问题','0'),
('1-7-4', '1-7-0', '售后问题详细页', '/customerService/detail', '售后问题详细页','0'),
('1-7-5', '1-7-0', '售后列表信息', '/customerService/find', '售后列表信息','0'),
('1-7-6', '1-7-0', '查询买家售后列表', '/customerService/findDetail', '查询买家售后列表','0'),
('1-7-7', '1-7-0', '保存售后问题', '/customerService/save', '保存售后问题','0'),
('1-7-8', '1-7-0', '图片上传', '/customerService/uploadImg', '图片上传','0'),
('1-7-9', '1-7-0', '售后问题处理', '/customerService/saveDisposal', '售后问题处理','0'),
('1-7-10', '1-7-0', '保存售后问题分配', '/customerService/saveAdmeasure', '保存售后问题分配','0'),
('1-7-11', '1-7-0', '图片下载', '/customerService/downloadFile', '图片下载','0'),
('1-7-12', '1-7-0', '删除附件', '/customerService/deleteFile', '删除附件','0'),
('1-8-0','0', '工作台', '/worktable', '工作台','0'),
('1-8-1', '1-8-0', '工作台', '/worktable/index', '工作台','0'),
('1-8-2', '1-8-0', '提建议', '/worktable/show_suggest', '提建议','0'),




('1-9-0','0', '客服备忘', '/reminder', '客服备忘','0'),
('1-9-1', '1-9-0', '首页', '/reminder/index', '首页','0'),
('1-9-2', '1-9-0', '跳转至添加', '/reminder/toSave', '跳转至添加','0'),
('1-9-3', '1-9-0', '添加', '/reminder/save ', '添加','0'),
('1-9-4', '1-9-0', '查询', '/reminder/list', '查询','0'),
('1-9-5', '1-9-0', '加载信息状态个数', '/reminder/loadStatusCount', '加载信息状态个数','0'),
('1-9-6', '1-9-0', '信息列表', '/reminder/listDetail', '信息列表','0'),
('1-9-7', '1-9-0', '修改信息状态', '/reminder/updateStatus', '修改信息状态','0'),
('1-9-8', '1-9-0', '跳转至修改', '/reminder/toUpdate', '跳转至修改','0'),
('1-9-9', '1-9-0', '修改', '/reminder/update', '修改','0'),



('1-10-0','0', '通知模块', '/notice', '通知模块','0'),
('1-10-1', '1-10-0', '首页', '/notice/index', '通知访问页面','0'),
('1-10-2', '1-10-0', '添加', '/notice/show', '新增通知','0'),
('1-11-0','0', '知识库模块', '/knowledge', '知识库模块','0'),
('1-11-1', '1-11-0', '首页', '/knowledge/index', '知识库访问页面','0'),
('1-11-2', '1-11-0', '搜索', '/knowledge/search', '知识库搜索','0'),
('1-11-3', '1-11-0', '管理', '/knowledge/admin/index', '知识库管理','0'),
('1-12-0','0', '建议管理模块', '/adviceservice/index', '建议管理','0');


 