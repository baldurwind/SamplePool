/**
 * 知识管理JS
 */
var knowledgemgt = {

	pagesize : 10,
	knowpagecount : 0,
	itempagecount : 0,
	subjectParentId : "",
	editSubjectId : "",
	deleteSubjectId : null,
	curPanel : null,
	subjectList : null,
	init : function() {
		$("#itemid").val("0");
		$("#checkupload").attr("checked", false);

		// $("#dialog").wijdialog({
		// autoOpen: false,
		// captionButtons: {
		// refresh: { visible: false }
		// }
		// });
		$(":input[type='text'],textarea").wijtextbox();

		$("#cus-leftNav .cus-leftNav-list h3.navTit").live('click', function() {
			$(this).toggleClass('choose').siblings('ul').toggle();
		});
		$("table#knowledge_prolist tr td a").live(
				'click',
				function() {
					var itemid = $(this).attr("id");
					$("#itemid").val(itemid);
					$(this).addClass("sp_desbtn2").parent().parent().siblings(
							'tr').find('a').removeClass('sp_desbtn2');
				});

		// 全选
		$("#allChecked").click(
				function() {
					var checked = $("#allChecked").attr("checked");
					if (checked == "checked") {
						$("#dataContiner").find("input:checkbox").attr(
								'checked', '');
					} else {
						$("#dataContiner").find("input:checkbox").removeAttr(
								'checked');
					}

				});

		$("#deleteknowledge").click(function() {
			var ids = knowledgemgt.getSelectRows();
			if (ids.length <= 0) {
				knowledgemgt.showMessge('选择一条或多条建议');
				return;
			}
			knowledgemgt.deleteById(ids);
		});
		$("#translate").click(function() {
			var targetType = $("#translationsubjectlist").val();
			if (targetType == '-1') {
				knowledgemgt.showMessge('选择一个类型');
				return;
			}
			var ids = knowledgemgt.getSelectRows();
			if (ids.length <= 0) {
				knowledgemgt.showMessge('选择一条或多条建议');
				return;
			}
			knowledgemgt.translateByIds(targetType, ids);
		});

		$(".menu_delete").click(function() {
			knowledgemgt.deletekPanel();
		});

		$(".menu_edit").click(function() {
			knowledgemgt.editkPanel();
		});

		$("#addgensubject").click(function(event) {
			var buttonid = $(this).attr("id");
			knowledgemgt.addRolePanel(buttonid);
			event.stopPropagation();
		});

		$("#additemsubject").click(function(event) {
			var buttonid = $(this).attr("id");
			knowledgemgt.addRolePanel(buttonid);
			event.stopPropagation();
		});
		// 添加知识库
		$("#addknowledge_btn").click(function() {
			knowledgemgt.addKnowledgePanel();
		});

		// 修改知识库
		$(".editkcon_btn").click(function() {
			knowledgemgt.editKnowledgePanel();
		});

		$("#allknowledge").click(
				function() {
					// $("ul.menu_zl li").each(function(){
					// $(this).removeClass("curr");});
					var params = "seller=" + seller
							+ "&page=first&cpage=0&pagesize="
							+ knowledgemgt.pagesize;
					knowledgemgt.loadKnowledgeList(params, "0");
				});

		// 加载左边主题列表
		var params = "seller=" + seller + "&cpage=0&page=first&pagesize="
				+ knowledgemgt.pagesize;
		knowledgemgt.loadSubject();
		knowledgemgt.loadKnowledgeList(params, "0");

	},
	// 添加主题
	addSubject : function(parentid, subject) {
		subject = subject.replaceAll(" ", "");
		if (subject == '' || subject.length <= 0 || subject == undefined) {
			knowledgemgt.showMessge('主题不能为空  ');
			return false;
		}
		if (subject.length > 8) {
			knowledgemgt.showMessge('主题长度不能超过8');
			return false;
		}
		$.ajax({
			type : 'post',
			dataType : "json",
			url : contextPath + "knowledge/admin/addsubject",
			data : "seller=" + seller + "&creator=" + creator + "&parentid="
					+ parentid + "&subject=" + subject,
			error : function() {
			},
			success : function(json) {
				if (json.result == true || json.result == "true") {
					knowledgemgt.loadSubject();
					knowledgemgt.showMessge('添加主题成功!');
					$.closeMyPannel(curPanel);
					return true;
				} else if (json.result == "-1") {
					knowledgemgt.showMessge("该主题已经存在");
					return false;
				} else {
					knowledgemgt.showMessge('添加主题失败!');
					return false;
				}

			}
		});
	},
	// 添加主题
	editSubject : function(subjectid, subject) {
		subject = subject.replaceAll(" ", "");
		if (subject == '' || subject.length <= 0 || subject == undefined) {
			knowledgemgt.showMessge('主题不能为空  ');
			return false;
		}
		if (subject.length > 8) {
			knowledgemgt.showMessge('主题长度不能超过8');
			return false;
		}
		$.ajax({
			type : 'post',
			dataType : "json",
			url : contextPath + "knowledge/admin/updatesubject",
			data : "seller=" + seller + "&subjectid=" + subjectid + "&subject="
					+ subject,
			error : function() {
			},
			success : function(json) {
				if (json.result == true || json.result == "true") {
					knowledgemgt.loadSubject();
					knowledgemgt.showMessge('修改主题成功!');
					$.closeMyPannel(curPanel);
					return true;
				} else if (json.result == "-1") {
					knowledgemgt.showMessge("该主题已经存在");
					return false;
				} else {
					knowledgemgt.showMessge('修改主题失败!');
					return false;
				}

			}
		});
	},
	deleteSubjectById : function(subjectid) {
		var url = contextPath + "knowledge/admin/deletesubject";
		$.ajax({
			type : 'post',
			dataType : "json",
			url : url,
			data : 'id=' + subjectid,
			error : function() {
			},
			success : function(json) {
				if (json != null) {
					if (json.result == true || json.result == "true") {
						knowledgemgt.loadSubject();
						knowledgemgt.showMessge("删除成功");
						$.closeMyPannel(curPanel);
					} else {
						knowledgemgt.showMessge("系统错误，请重试!");
					}
				}
			}
		});
	},
	// 加载主题树
	loadSubject : function(selectsubjectid) {
		var url = contextPath + "knowledge/admin/subjectlist";
		$
				.ajax({
					type : 'post',
					dataType : "json",
					url : url,
					data : "seller=" + seller,
					error : function() {

					},
					success : function(json) {
						if (json != null) {
							knowledgemgt.subjectList = json;
							var genSub = json.gen;
							var itemSub = json.item;
							var selectadvicetypelist = "<option value='-1'>选择分类</option>";
							var rownum = 1;
							if (genSub.length > 0) {
								var gensublist = "";
								var imgArr = [];
								for ( var i = 0; i < genSub.length; i++) {
									var row = genSub[i];
									var subjectId = row.id + "_" + rownum
											+ "img";
									var imgId = rownum + "img";
									selectadvicetypelist += "<option value='"
											+ row.id + "'>" + row.name
											+ "</option>";
									var classCss = "";
									var md = "<img id='"
											+ imgId
											+ "_m' src='"
											+ staticResourcePath
											+ "/resource/images/Write.png' class='menu_edit' width='16' height='16' alt='修改' style='display: none'/><img id='"
											+ imgId
											+ "_d' src='"
											+ staticResourcePath
											+ "/resource/images/delete.png' width='16' height='16' class='menu_delete' style='display: none'/>";
									if (selectsubjectid == row.id) {
										classCss = "class='curr'";
									}
									if (selectsubjectid == i) {
										classCss = "class='curr'";
									}
									gensublist += "<li "
											+ classCss
											+ "><a href='javascript:void(0)' id='"
											+ subjectId
											+ "' onclick='knowledgemgt.findKnowledgeBySubject(this)'>"
											+ row.name + "</a>" + md + "</li>";
									rownum++;
									imgArr.push(imgId);
								}
								$("#genernalsubjectlist").html(gensublist);
								for ( var i = 0; i < imgArr.length; i++) {
									var imgDx = imgArr[i];
									$("#" + imgDx + "_m").click(
											function() {
												var subjectid = $(this).parent(
														'li').children('a')
														.attr('id');
												knowledgemgt
														.editkPanel(subjectid);
											});
									$("#" + imgDx + "_d")
											.click(
													function() {
														var subjectid = $(this)
																.parent('li')
																.children('a')
																.attr('id');
														knowledgemgt
																.deletekPanel(subjectid
																		.split("_")[0]);
													});
								}
							}
							$("#translationsubjectlist").html(
									selectadvicetypelist);
							if (itemSub.length > 0) {
								var itemsublist = "";
								var timgArr = [];
								for ( var i = 0; i < itemSub.length; i++) {
									var row = itemSub[i];
									var classCss = "";
									var subjectId = row.id + "_" + rownum
											+ "img";
									var imgId = rownum + "img";
									var md = "<img id='"
											+ imgId
											+ "_m' src='"
											+ staticResourcePath
											+ "/resource/images/Write.png' class='menu_edit' width='16' height='16' alt='修改' style='display: none'/>&nbsp;&nbsp;<img id='"
											+ imgId
											+ "_d' src='"
											+ staticResourcePath
											+ "/resource/images/delete.png' width='16' height='16' class='menu_delete' style='display: none'/>";
									if (selectsubjectid == row.id) {
										classCss = "class='curr'";
									}
									itemsublist += "<li "
											+ classCss
											+ "><a href='javascript:void(0)' id='"
											+ subjectId
											+ "' onclick='knowledgemgt.findKnowledgeBySubject(this)' >"
											+ row.name + "</a>" + md + "</li>";
									rownum++;
									timgArr.push(imgId);
								}
								$("#itemsubjectlist").html(itemsublist);
								for ( var i = 0; i < timgArr.length; i++) {
									var imgDx = timgArr[i];
									$("#" + imgDx + "_m").click(
											function() {
												var subjectid = $(this).parent(
														'li').children('a')
														.attr('id');
												knowledgemgt
														.editkPanel(subjectid);
											});
									$("#" + imgDx + "_d")
											.click(
													function() {
														var subjectid = $(this)
																.parent('li')
																.children('a')
																.attr('id');
														knowledgemgt
																.deletekPanel(subjectid
																		.split("_")[0]);
													});
								}

							}

							// knowledgemgt.initLeftMenu();

						}
					}
				});
	},
	loadAddKnowledgeSubject : function(subjectid) {
		if (knowledgemgt.subjectList != null) {
			var json = knowledgemgt.subjectList;
			var genSub = json.gen;
			var itemSub = json.item;
			var rownum = 1;
			var selectadvicetypelist = "<option value='-1'>请选择主题</option>";
			if (genSub.length > 0) {
				var gensublist = "";
				var imgArr = [];
				for ( var i = 0; i < genSub.length; i++) {
					var row = genSub[i];
					if (row.id == subjectid) {
						selectadvicetypelist += "<option selected='selected' value='"
								+ row.id + "'>通用知识---" + row.name + "</option>";
					} else {
						selectadvicetypelist += "<option value='" + row.id
								+ "'>通用知识---" + row.name + "</option>";
					}

				}
			}
			if (itemSub.length > 0) {
				for ( var i = 0; i < itemSub.length; i++) {
					var row = itemSub[i];
					if (row.id == subjectid) {
						selectadvicetypelist += "<option selected='selected' value='"
								+ row.id + "'>关联商品---" + row.name + "</option>";
					} else {
						selectadvicetypelist += "<option value='" + row.id
								+ "'>关联商品---" + row.name + "</option>";
					}

				}

			}
			$("#addknowledgesubjectlist").html(selectadvicetypelist);
		} else {
			var url = contextPath + "knowledge/admin/subjectlist";
			$
					.ajax({
						type : 'get',
						dataType : "json",
						url : url,
						data : "seller=" + seller,
						error : function() {

						},
						success : function(json) {
							if (json != null) {
								var genSub = json.gen;
								var itemSub = json.item;
								var rownum = 1;
								var selectadvicetypelist = "<option value='-1'>请选择主题</option>";
								if (genSub.length > 0) {
									var gensublist = "";
									var imgArr = [];
									for ( var i = 0; i < genSub.length; i++) {
										var row = genSub[i];
										if (row.id == subjectid) {
											selectadvicetypelist += "<option selected='selected' value='"
													+ row.id
													+ "'>通用知识---"
													+ row.name + "</option>";
										} else {
											selectadvicetypelist += "<option value='"
													+ row.id
													+ "'>通用知识---"
													+ row.name + "</option>";
										}

									}
								}
								if (itemSub.length > 0) {
									for ( var i = 0; i < itemSub.length; i++) {
										var row = itemSub[i];
										if (row.id == subjectid) {
											selectadvicetypelist += "<option selected='selected' value='"
													+ row.id
													+ "'>关联商品---"
													+ row.name + "</option>";
										} else {
											selectadvicetypelist += "<option value='"
													+ row.id
													+ "'>关联商品---"
													+ row.name + "</option>";
										}

									}

								}
								$("#addknowledgesubjectlist").html(
										selectadvicetypelist);

							}
						}
					});
		}

	},
	// 加载商品
	loadItem : function(cur_page, page, itemid) {
		var url = contextPath + "knowledge/admin/itemlist";
		$
				.ajax({
					type : 'post',
					dataType : "json",
					url : url,
					data : 'seller=' + seller + '&userid=' + userid
							+ '&itemid=' + itemid + '&cpage=' + cur_page
							+ '&page=' + page,
					error : function() {
					},
					success : function(json) {
						if (json != null) {
							var rows = json.row;
							var pagebar = knowledgemgt.getitempagehtml(
									json.page, json.pagecount);
							knowledgemgt.itempagecount = json.pagecount;
							var itemtrs = "<thead>" + "<tr>"
									+ "<th>序号</th><th>商品描述</th><th>操作</th>"
									+ "</tr>" + "</thead>";
							if (rows != null && rows.length > 0) {
								var curpage = parseInt(json.page);
								var rownum = curpage * 3 + 1;
								for ( var i = 1; i <= rows.length; i++) {
									var n = rows[i - 1];
									var selectedItem = "class='sp_desbtn'";
									if (n.id == itemid) {
										selectedItem = "class = 'sp_desbtn2'";
									}
									itemtrs += "<tr>"
											+ "<td>"
											+ rownum
											+ "</td>"
											+ "<td class='sp_des'><img src='"
											+ n.image
											+ "' />"
											+ "<p>"
											+ n.name
											+ "</p></td>"
											+ "<td><a href='javascript:void(0)' "
											+ selectedItem + " id='" + n.id
											+ "'>关联此商品</a></td>" + "</tr>";
									rownum++;
								}
								$("#relationitempagebar").html(pagebar);
							} else {
								itemtrs += "<tr align='center'><td colspan='3'>没有相关的商品</td><tr>";
								$("#relationitempagebar").html("");
							}
							$("#knowledge_prolist").html(itemtrs);

						}
					}
				});
	},
	getSelectRows : function() {
		var ids = "";
		$("table :checkbox").each(function(index, domEle) {
			var checked = $(domEle).attr("checked");
			if (checked == "checked") {
				var cname = $(domEle).attr("name");
				if (ids.length > 0) {
					ids += ",";
				}
				ids += $("#" + cname + "aid").val();
			}
		});
		return ids;

	},
	deleteById : function(knowledgeids) {
		$.tooltip({
			"type" : "confirm",
			"content" : "是否删除",
			"callback" : function(result) {
				if (result) {
					var url = contextPath + "knowledge/admin/deleteknowledge";
					$.ajax({
						type : 'post',
						dataType : "json",
						url : url,
						data : 'ids=' + knowledgeids,
						error : function() {
						},
						success : function(json) {
							if (json != null) {
								if (json.result == true
										|| json.result == "true") {
									knowledgemgt.reloadData(0, 'first');
									knowledgemgt.showMessge("删除成功");
								} else {
									knowledgemgt.showMessge("系统错误，请重试!");
								}
							}
						}
					});
				}
			}
		});
	},
	translateByIds : function(subjectid, knowledgeids) {
		var url = contextPath + "knowledge/admin/updateknowledge";
		$.ajax({
			type : 'post',
			dataType : "json",
			url : url,
			data : 'ids=' + knowledgeids + "&subjectid=" + subjectid,
			error : function() {
			},
			success : function(json) {
				if (json != null) {
					if (json.result == true || json.result == "true") {
						knowledgemgt.reloadData(0, 'first');
						knowledgemgt.showMessge("转移成功");
					} else {
						knowledgemgt.showMessge("转移失败");
					}
				}
			}
		});
	},
	getSelectSubjectID : function() {
		var subjectid = $(".curr").find('a').attr("id");
		if (subjectid == undefined) {
			subjectid = "0";
		} else {
			subjectid = subjectid.split("_")[0];
		}
		return subjectid;
	},
	reloadData : function(cpage, page) {
		var subjectid = knowledgemgt.getSelectSubjectID();
		var params = "seller=" + seller + "&page=" + page + "&cpage=" + cpage
				+ "&pagesize=" + knowledgemgt.pagesize;
		knowledgemgt.loadKnowledgeList(params, subjectid);
	},
	// 搜索商品
	searchItem : function(params) {

		var url = contextPath + "knowledge/admin/itemsearch";
		$
				.ajax({
					type : 'post',
					dataType : "json",
					url : url,
					data : params,
					error : function() {
					},
					success : function(json) {
						if (json != null) {
							var rows = json.row;
							var pagebar = knowledgemgt.getitempagehtml(
									json.page, json.pagecount);
							knowledgemgt.itempagecount = json.pagecount;
							var itemtrs = "<thead>" + "<tr>"
									+ "<th>序号</th><th>商品描述</th><th>操作</th>"
									+ "</tr>" + "</thead>";
							if (rows != null && rows.length > 0) {
								var curpage = parseInt(json.page);
								var rownum = curpage * 3 + 1;
								for ( var i = 1; i <= rows.length; i++) {
									var n = rows[i - 1];
									var selectedItem = "class='sp_desbtn'";
									if (n.id == knowledgeInfo.itemid) {
										selectedItem = "class = 'sp_desbtn2'";
									}
									itemtrs += "<tr>"
											+ "<td>"
											+ rownum
											+ "</td>"
											+ "<td class='sp_des'><img src='"
											+ n.image
											+ "' />"
											+ "<p>"
											+ n.name
											+ "</p></td>"
											+ "<td><a href='javascript:void(0)' "
											+ selectedItem + " id='" + n.id
											+ "'>关联此商品</a></td>" + "</tr>";
									rownum++;
								}
								$("#knowledge_prolist").html(itemtrs);
								// pagebar ="<span
								// class='left'>"+(curpage+1)+"/"+json.pagecount+"</span>"+pagebar;
								$("#relationitempagebar").html(pagebar);
							} else {
								itemtrs += "<tr align='center'><td colspan='3'>没有搜索到相关的商品</td><tr>";
								$("#knowledge_prolist").html(itemtrs);
								$("#relationitempagebar").html("");
							}

						}
					}
				});
	},
	findKnowledgeBySubject : function(dom) {
		var subjectid = $(dom).attr("id");
		var imgid = subjectid.split("_")[1];
		subjectid = subjectid.split("_")[0];
		$("#" + imgid + "_m").css("display", "");
		$("#" + imgid + "_d").css("display", "");
		$(dom).parent('li').addClass("curr").siblings("li").removeClass("curr");
		var subjectType = $(dom).parent('li').parent().attr("id");
		$(dom).parent('li').siblings('li').children('img').css('display',
				'none');
		var targetHide = "genernalsubjectlist";
		if (subjectType == "genernalsubjectlist") {
			targetHide = "itemsubjectlist";
		}
		$("#" + targetHide).children('li').removeClass('curr');
		$("#" + targetHide).children('li').children('img').css('display',
				'none');
		var params = "seller=" + seller + "&cpage=0&page=first&pagesize="
				+ knowledgemgt.pagesize;
		knowledgemgt.loadKnowledgeList(params, subjectid);
	},
	loadKnowledgeList : function(params, subjectid) {
		var url = contextPath + "knowledge/admin/subknowledge/" + subjectid;
		$("#knowledgelisttable tr.head").siblings('tr').remove();
		var before = '<div style=" width:200px; margin:150px auto;"><img style="vertical-align:middle" src="'+staticResourcePath+'/resource/images/beforeSuccess.gif"/><span>正在加载中。。</span></div>';
		$("#loadingdata").html(before);
		$("#pagebar").html("");
		$
				.ajax(
						{
							type : 'post',
							dataType : "json",
							url : url,
							data : params,
							error : function() {
							},
							success : function(json) {
								if (json != null) {
									var rows = json.row;
									// var pagebar =
									// getPagebar(json.page,json.pagecount);
									var pagebar = knowledgemgt.getpagehtml(
											json.page, json.pagecount);
									knowledgemgt.knowpagecount = json.pagecount;
									var th = "<tr class='head'>"
											+ "<td width='30'>&nbsp;</td>"
											+ "<td>ID</td>" + "<td>知识标题</td>"
											+ "<td>所属主题</td>" + "<td>提交时间</td>"
											+ "<td>操作</td>" + "</tr>";
									var trs = "";
									var curpage = parseInt(json.page);
									var rownum = curpage
											* parseInt(json.pagesize) + 1;
									if (rows.length > 0) {
										for ( var i = 0; i < rows.length; i++) {
											var n = rows[i];
											var sid = rownum + "aid";
											var proid = rownum + "_item";
											var advicestatus = n.id + "_"
													+ n.itemid;
											var optername = n.id + "_chakan";
											trs += "<tr class='list-head'>"
													+ "<td><input name='"
													+ rownum
													+ "' type='checkbox' value='' /><input type='hidden' value='"
													+ n.id
													+ "' id='"
													+ sid
													+ "'/></td>"
													+ "<td>"
													+ rownum
													+ "</td>"
													+ "<td>"
													+ n.title
													+ "</td>"
													+ "<td>"
													+ n.subject
													+ "</td>"
													+ "<td>"
													+ n.datetime
													+ "</td>"
													+ "<td class='dataContinerOperate'><a href='javascript:void(0)' id='"
													+ advicestatus
													+ "' name='"
													+ optername
													+ "' class='detailLink' onclick='knowledgemgt.readKnowledgeById(\""
													+ n.id
													+ "\",\""
													+ rownum
													+ "\",\""
													+ n.itemid
													+ "\")'>查看</a><a href='javascript:void(0)' onclick='knowledgemgt.deleteById(\""
													+ n.id
													+ "\")'>删除</a><a href='javascript:void(0)' class='editkcon_btn' onclick='knowledgemgt.modifyKnowledgeById(\""
													+ n.id
													+ "\",\""
													+ n.title
													+ "\",\""
													+ n.content
													+ "\",\""
													+ n.itemid
													+ "\",\""
													+ n.subjectid
													+ "\")'>修改</a></td>";
											trs += "</tr>";
											trs += "<tr class='knowledge_detail' style='display:none'>"
													+ "<td>&nbsp;</td>"
													+ "<td colspan='5' valign='top'>"
													+ "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"
													+ "<tr>"
													+ "<td><strong>知识内容：</strong>"
													+ n.content
													+ "</td>"
													+ "</tr>"
													+ "</table>"
													+ "<table width='100%' border='0' cellspacing='0' cellpadding='0' style='display:'>"
													+ "<tr>"
													+ "<td width='91%' align='left' valign='top'>"
													+ "<div class='knowledge_pro' id='"
													+ proid
													+ "'>"
													+ "</div>"
													+ "</td>"
													+ "</tr>"
													+ "</table>"
													+ "</td>"
													+ "</tr>";
											rownum++;
										}
										$("#knowledgelisttable").html(th + trs);
										$("#loadingdata").html('');
										inittable_tr();
										$("#pagebar").html(pagebar);
										$("#pagesize").val(json.pagesize);
										knowledgemgt.initListenerEvent();

									} else {
										$("#loadingdata").html('');
										var norecord = "<tr><td colspan='5' align='center'>当前暂无记录</td></tr>";
										$("#pagebar").html("");
										$("#knowledgelisttable").html(
												th + norecord);
									}

								}
							}
						})
				.then(
						function() {
							$("#dataContiner table tr.list-head a.detailLink")
									.toggle(
											function() {

												var oname = $(this)
														.attr("name");
												var aid = oname.split("_")[0];
												$(this).attr("name",
														aid + "_shouhui");
												$(this)
														.addClass(
																'dataContinerOperate_choose')
														.text('收回');
												var td = $(this).parent();
												td.parent("tr").removeClass(
														"unread").addClass(
														"read").next("tr")
														.show();

											},
											function() {
												var oname = $(this)
														.attr("name");
												var aid = oname.split("_")[0];
												$(this).attr("name",
														aid + "_chakan");
												$(this)
														.removeClass(
																'dataContinerOperate_choose')
														.text('查看');
												var td = $(this).parent();
												td.parent("tr").removeClass(
														"read").next("tr")
														.hide();
											});
						});
	},
	// 分页
	showLimitDiscountPage : function(cpage, page) {
		var data = 'seller=' + seller + '&cpage=' + cpage + '&page=' + page
				+ "&pagesize=" + knowledgemgt.pagesize;
		;
		var subjectid = knowledgemgt.getSelectSubjectID();
		knowledgemgt.loadKnowledgeList(data, subjectid);

	},
	// 分页
	showItemLimitDiscountPage : function(cpage, page) {

		var keyword = $("#itemsearch").val();
		var params = "userid=" + seller + "&cpage=" + cpage + "&page=" + page
				+ "&pagesize=" + knowledgemgt.pagesize;
		if (keyword != '' && keyword.length > 0) {
			params += "&title=" + keyword + "&type=aa&p=5&itemid="
					+ knowledgeInfo.itemid;
			knowledgemgt.searchItem(params);
		} else {
			knowledgemgt.loadItem(cpage, page, knowledgeInfo.itemid);
		}

		// knowledgemgt.loadKnowledgeList(data,subjectid);

	},
	getItemBaseInfo : function(elementid, itemid) {
		var url = contextPath + "knowledge/admin/itembaseinfo";
		$
				.ajax({
					type : 'get',
					dataType : "json",
					url : url,
					data : 'itemid=' + itemid,
					error : function() {
					},
					success : function(json) {
						if (json != null) {
							var itmeInfo = "<strong class='left'>关联商品：</strong>"
									+ "<p class='leftimg'><img src='"
									+ json.imgurl
									+ "'/></p>"
									+ "<div class='rightInfo'>"
									+ "<h4 class='cptit'>"
									+ json.title
									+ "</h4>"
									+ "<ul class='parameterList'>"
									+ "<li><span>价　　格：</span><strong class='ft-orange ft-14 left'>"
									+ json.price
									+ "</strong></li>"
									+ "</ul>"
									+ "</div>" + "<div class='clear'></div>";
							$("#" + elementid).html(itmeInfo);
						}

					}
				});
	},
	// 表单提交
	checkForm : function() {
		var title = $("#textbox").val();
		var cotitlentent = $("#area").val();
		var subname = $("#addknowledgesubjectlist").val();
		var selectItemId = $("#itemid").val();
		var submit = true;
		if (subname == "-1" || subname == ' ' || subname == undefined) {
			knowledgemgt.showMessge('请选择一个这主题!');
			submit = false;
			return false;
		}

		if (title == '' || title == ' ' || title == undefined) {
			knowledgemgt.showMessge('标题不能为空');
			submit = false;
			return false;
		}
		if (title.length > 32) {
			knowledgemgt.showMessge('标题长度不能超过32!');
			return false;
		}

		if (cotitlentent == '' || cotitlentent == ' '
				|| cotitlentent == undefined) {
			knowledgemgt.showMessge('内容不能为空');
			submit = false;
			return false;
		}
		if (cotitlentent.length > 512) {
			knowledgemgt.showMessge('内容长度不能超过512!');
			return false;
		}
		var subText = $("#addknowledgesubjectlist").find("option:selected")
				.text();
		if (subText.indexOf("通用") == -1) {
			if (selectItemId == "0") {
				knowledgemgt.showMessge('请选择关联的商品');
				submit = false;
				return false;
			}
		}

		$("#creatorid").val(creator);
		$("#sellerid").val(seller);
		var checkup = $("#checkup").val();
		if (checkup == "uploadfileName") {
			var filename = $("#uploadfile").val();
			if (filename == '' || filename == " " || filename == undefined) {
				knowledgemgt.showMessge('请选择要上传的附件!');
				return false;
			}
		}
		$("#MyForm").attr('action',
				contextPath + "knowledge/admin/addknowledge");
		// $("#MyForm").action = ;
		$("#MyForm").submit();

	},
	// 根据text获取节点
	getNode : function(text) {
		return $("#tree").wijtree("findNodeByText", text);
	},
	ckuploadFS : function() {
		var ck = $("#checkupload").attr("checked");
		if (ck == "checked") {
			$("#uploadtab").css("display", "");
			$("#checkup").val("uploadfileName");
		} else {
			$("#uploadtab").css("display", "none");
			$("#checkup").val("");
		}
	},
	showMessge : function(msg) {
		showMessage(msg);
	},
	getGoPage : function() {
		var cpage = $("#topage").val();
		if (cpage == '' || cpage == " ") {
			knowledgemgt.showMessge('请输入页码');
			return false;
		}
		if (parseInt(cpage) > parseInt(knowledgemgt.knowpagecount)
				|| parseInt(cpage) <= 0) {
			knowledgemgt.showMessge('页码不对');
			$("#topage").val("");
			return false;
		}
		var page = 'next';
		if (cpage == 1) {
			page = 'first';
		} else if (0 < parseInt(cpage)
				&& parseInt(cpage) < parseInt(knowledgemgt.knowpagecount)) {
			page = 'next';
		} else {
			page = 'last';
		}
		knowledgemgt.reloadData(parseInt(cpage) - 2, page);
	},
	initListenerEvent : function() {
		$("#pagesize").bind({
			change : function() {
				knowledgemgt.pagesize = $("#pagesize").val();
				knowledgemgt.reloadData(0, 'first');
			}

		});
		$("#gopage").bind({
			click : function() {
				knowledgemgt.getGoPage();
			}

		});
		$("#topage").bind({
			keyup : function() {
				this.value = this.value.replace(/\D/g, '');
			}

		});
	},
	getpagehtml : function(page, pagecount) {

		var pagebar = "";
		// var pagebar = "<li
		// class='ui-pager-step'>第"+(parseInt(page)+1)+"页</li><li
		// class='ui-pager-count'>共"+pagecount+"页</li>";
		if (pagecount > 0) {
			pagebar = '<li class="ui-pager2-step" id="pagesizepart">每页<select id="pagesize"><option value="10">10</option><option value="20">20</option><option value="30">30</option><option value="50">50</option></select>条</li>';
			pagebar += "<li class='ui-pager2-step'>第" + (parseInt(page) + 1)
					+ "页</li><li class='ui-pager2-count'>共" + pagecount
					+ "页</li>";
			if (page == 0 && pagecount > 1) {
				pagebar += "<li class='ui-pager2-firstPage-disable'>首页</li><li class='ui-pager2-prePage-disable'>上一页</li><li class='ui-pager2-nextPage' onclick=\"knowledgemgt.showLimitDiscountPage("
						+ page
						+ ",'next')\">下一页</li><li class='ui-pager2-lastPage' onclick=\"javascript:knowledgemgt.showLimitDiscountPage("
						+ page + ",'last')\">尾页</li>";
			} else if (page == 0 && pagecount == 1) {
				pagebar += "<li class='ui-pager2-firstPage-disable'>首页</li><li class='ui-pager2-prePage-disable'>上一页</li><li class='ui-pager2-nextPage-disable'>下一页</li><li class='ui-pager2-lastPage-disable'>尾页</li>";
			} else if (0 < page && page < parseInt(pagecount) - 1) {
				pagebar += "<li class='ui-pager2-firstPage' onclick=\"javascript:knowledgemgt.showLimitDiscountPage("
						+ page
						+ ",'first')\">首页</li><li class='ui-pager2-prePage' onclick=\"javascript:knowledgemgt.showLimitDiscountPage("
						+ page
						+ ",'previous')\">上一页</li><li class='ui-pager2-nextPage' onclick=\"javascript:knowledgemgt.showLimitDiscountPage("
						+ page
						+ ",'next')\">下一页</li><li class='ui-pager2-lastPage' onclick=\"javascript:knowledgemgt.showLimitDiscountPage("
						+ page + ",'last')\">尾页</li>";
			} else {
				pagebar += "<li class='ui-pager2-firstPage' onclick=\"javascript:knowledgemgt.showLimitDiscountPage("
						+ page
						+ ",'first')\">首页</li><li class='ui-pager2-prePage' onclick=\"javascript:knowledgemgt.showLimitDiscountPage("
						+ page
						+ ",'previous')\">上一页</li><li class='ui-pager2-nextPage-disable'>下一页</li><li class='ui-pager2-lastPage-disable'>尾页</li>";
			}

			pagebar += '<li class="ui-pager2-go"><input id="topage" class="pagenum"/><input type="button" class="go" id="gopage"/></li>';
			pagebar += "<div class='clear'></div>";
		}
		return pagebar;
	},
	getitempagehtml : function(page, pagecount) {
		var pagebar = "";

		if (pagecount > 0) {
			pagebar += "<li class='ui-pager2-count'>" + (parseInt(page) + 1)
					+ "/" + pagecount + "</li>";
			if (page == 0 && pagecount > 1) {
				pagebar += "<li class='ui-pager2-firstPage-disable'>首页</li><li class='ui-pager2-prePage-disable'>上一页</li><li class='ui-pager2-nextPage' onclick=\"knowledgemgt.showItemLimitDiscountPage("
						+ page
						+ ",'next')\">下一页</li><li class='ui-pager2-lastPage' onclick=\"javascript:knowledgemgt.showItemLimitDiscountPage("
						+ page + ",'last')\">尾页</li>";
			} else if (page == 0 && pagecount == 1) {
				pagebar += "<li class='ui-pager2-firstPage-disable'>首页</li><li class='ui-pager2-prePage-disable'>上一页</li><li class='ui-pager2-nextPage-disable'>下一页</li><li class='ui-pager2-lastPage-disable'>尾页</li>";
			} else if (0 < page && page < parseInt(pagecount) - 1) {
				pagebar += "<li class='ui-pager2-firstPage' onclick=\"javascript:knowledgemgt.showItemLimitDiscountPage("
						+ page
						+ ",'first')\">首页</li><li class='ui-pager2-prePage' onclick=\"javascript:knowledgemgt.showItemLimitDiscountPage("
						+ page
						+ ",'previous')\">上一页</li><li class='ui-pager2-nextPage' onclick=\"javascript:knowledgemgt.showItemLimitDiscountPage("
						+ page
						+ ",'next')\">下一页</li><li class='ui-pager2-lastPage' onclick=\"javascript:knowledgemgt.showItemLimitDiscountPage("
						+ page + ",'last')\">尾页</li>";
			} else {
				pagebar += "<li class='ui-pager2-firstPage' onclick=\"javascript:knowledgemgt.showItemLimitDiscountPage("
						+ page
						+ ",'first')\">首页</li><li class='ui-pager2-prePage' onclick=\"javascript:knowledgemgt.showItemLimitDiscountPage("
						+ page
						+ ",'previous')\">上一页</li><li class='ui-pager2-nextPage-disable'>下一页</li><li class='ui-pager2-lastPage-disable'>尾页</li>";
			}

			pagebar += "<div class='clear'></div>";
		}
		return pagebar;
	},
	cleanForm : function() {
		$("#textbox").val("");
		$("#area").val("");
		$("#subectname").val("");
		$("#subectid").val("");
		$("#itemid").val("");
		$("#checkupload").attr("checked", false);
		$("#uploadfile").val("");
	},
	addKnowledgePanel : function() {
		var newRole_panel = $.openMyPannel();
		curPanel = newRole_panel;
		newRole_panel.load(contextPath
				+ "resource/admin/knowledge_panel.html #addknowcon_panel",
				function() {

					$("#closeBtnTRBtn").click(function() {
						$.closeMyPannel(newRole_panel);
					});
					// 是否关联商品
					$("#addknowledgesubjectlist").bind(
							{
								change : function(data) {
									var subjectType = $(this).find(
											"option:selected").text();
									if (subjectType != "-1") {
										if (subjectType.indexOf("通用") == -1) {
											$("#kprolist").show();
										} else {
											$("#kprolist").hide();
										}
									}
								}
							});
					// 添加知识弹出窗口的关联商品列表效果

					$("table#knowledge_prolist0 tr td a").click(
							function() {
								$(this).addClass("sp_desbtn2").parent()
										.parent().siblings('tr').find('a')
										.removeClass('sp_desbtn2');
							});
					$("#itemsearch").bind(
							{
								keyup : function(events) {
									var keyword = $("#itemsearch").val();
									var params = 'userid=' + seller
											+ '&cpage=0&page=first';
									if (keyword != '' && keyword.length > 0) {
										params += "&title=" + keyword
												+ "&type=aa&p=5&itemid="
												+ knowledgeInfo.itemid;
										knowledgemgt.searchItem(params);
									} else {
										knowledgemgt.loadItem(0, 'first',
												knowledgeInfo.itemid);
									}
								}
							});
					$("#createknowledge").click(function() {
						knowledgemgt.checkForm();
					});
					$("#actionmemo").html("新增知识");
					var subjectid = knowledgemgt.getSelectSubjectID();
					knowledgemgt.loadAddKnowledgeSubject(subjectid);
					var seclectedid = $(".curr").find('a').attr("id");
					var subType = $("#" + seclectedid).parent().parent()
							.siblings("h3").text();
					if (subType.indexOf("商品") == -1) {
						$("#kprolist").hide();
					} else {
						$("#kprolist").show();
					}
					knowledgemgt.loadItem(0, 'first', knowledgeInfo.itemid);

				});
		$.pannelBgClose(newRole_panel);
	},
	editKnowledgePanel : function() {
		var newRole_panel = $.openMyPannel();
		curPanel = newRole_panel;
		newRole_panel.load(contextPath
				+ "resource/admin/knowledge_panel.html #addknowcon_panel",
				function() {
					$("#closeBtnTRBtn").click(function() {
						$.closeMyPannel(newRole_panel);
					});
					// 添加知识弹出窗口的关联商品列表效果
					$("table#knowledge_prolist1 tr td a").click(
							function() {
								$(this).addClass("sp_desbtn2").parent()
										.parent().siblings('tr').find('a')
										.removeClass('sp_desbtn2');
							});
					$("#addknowledgesubjectlist").bind(
							{
								change : function(data) {
									var subjectType = $(this).find(
											"option:selected").text();
									if (subjectType != "-1") {
										if (subjectType.indexOf("通用") == -1) {
											$("#kprolist").show();
										} else {
											$("#itemid").val("0");
											$("#kprolist").hide();
										}
									}
								}
							});
					$("#createknowledge").click(function() {
						knowledgemgt.checkForm();
					});
					$("#itemsearch").bind(
							{
								keyup : function(events) {
									var keyword = $("#itemsearch").val();
									var params = 'userid=' + seller
											+ '&cpage=0&page=first';
									if (keyword != '' && keyword.length > 0) {
										params += "&title=" + keyword
												+ "&type=aa&p=5&itemid="
												+ knowledgeInfo.itemid;
										knowledgemgt.searchItem(params);
									} else {
										knowledgemgt.loadItem(0, 'first',
												knowledgeInfo.itemid);
									}
								}
							});

					knowledgemgt
							.loadAddKnowledgeSubject(knowledgeInfo.subjectid);
					knowledgemgt.loadItem(0, 'first', knowledgeInfo.itemid);
					$("#actionmemo").html("修改知识");
					$("#knowledgeid").val(knowledgeInfo.id);
					$("#textbox").val(knowledgeInfo.title);
					$("#area").val(
							knowledgeInfo.content.replaceAll("<br>", "\r\n"));
					$("#itemid").val(knowledgeInfo.itemid);
					if (knowledgeInfo.itemid == "0") {
						$("#kprolist").hide();
					} else {
						$("#kprolist").show();
					}
					$("#uploadconfig").css("display", "none");
					$("#uploadtab").css("display", "none");

				});
		$.pannelBgClose(newRole_panel);
	},
	addRolePanel : function(subjectid) {
		subjectParentId = subjectid;
		var newRole_panel = $.openMyPannel();
		curPanel = newRole_panel;
		newRole_panel.load(contextPath
				+ "resource/admin/knowledge_panel.html #addknowledge_panel",
				function() {
					$("#closeBtnTRBtn").click(function() {
						$.closeMyPannel(newRole_panel);
					});
					$("#savesubject").click(function() {
						var parentId = "1";
						if ("additemsubject" == subjectParentId) {
							parentId = "2";
						}
						var name = $("#klsubjectname").val();
						if (knowledgemgt.addSubject(parentId, name)) {
							$.closeMyPannel(newRole_panel);
						}
					});
				});
		$.pannelBgClose(newRole_panel);
	},
	editkPanel : function(subjectid) {

		editSubjectId = subjectid.split("_")[0];
		var newRole_panel = $.openMyPannel();
		curPanel = newRole_panel;
		newRole_panel.load(contextPath
				+ "resource/admin/knowledge_panel.html #editknowledge_panel",
				function() {
					var subjectName = $("#" + subjectid).text();
					$("#editklsubjectname").val(subjectName);
					$("#closeBtnTRBtn").click(function() {
						$.closeMyPannel(newRole_panel);
					});
					$("#editsubject").click(function() {
						var name = $("#editklsubjectname").val();
						if (knowledgemgt.editSubject(editSubjectId, name)) {
							$.closeMyPannel(newRole_panel);
						}
					});
				});
		$.pannelBgClose(newRole_panel);
	},
	deletekPanel : function(subjectid) {
		deleteSubjectId = subjectid;
		var newRole_panel = $.openMyPannel();
		curPanel = newRole_panel;
		newRole_panel.load(contextPath
				+ "resource/admin/knowledge_panel.html #deleteknowledge_panel",
				function() {
					$("#closeBtnTRBtn").click(function() {
						$.closeMyPannel(newRole_panel);
					});
					$("#deletesubject").click(function() {
						if (knowledgemgt.deleteSubjectById(deleteSubjectId)) {
							$.closeMyPannel(newRole_panel);
						}
					});
					$("#canceldeletesubject").click(function() {
						$.closeMyPannel(newRole_panel);
					});

				});
		$.pannelBgClose(newRole_panel);
	},
	readKnowledgeById : function(knowledgeid, rownum, itemid) {
		var readButtonId = knowledgeid + "_" + itemid;
		var ctext = $("#" + readButtonId).attr("name");
		var css = ctext.split("_")[1];
		var elementid = rownum + "_item";
		if (css == "chakan" && itemid != "0") {
			if ($("#" + elementid).children('p').children('img').length <= 0) {
				knowledgemgt.getItemBaseInfo(elementid, itemid);
			}
		}
	},
	modifyKnowledgeById : function(knowledgeid, title, content, itemid,
			subjectid) {
		knowledgeInfo.id = knowledgeid;
		knowledgeInfo.title = title;
		knowledgeInfo.content = content;
		knowledgeInfo.itemid = itemid;
		knowledgeInfo.subjectid = subjectid;
		knowledgemgt.editKnowledgePanel();
	}

};

var knowledgeInfo = {
	id : null,
	title : null,
	content : null,
	subjectid : null,
	itemid : null
}
