
/**
 * 建议管理类js
 */
var advicemgt = {
		init:function(){
			  //$("#advicetypelist").wijdropdown();
			$('#dialog').wijdialog({
		         autoOpen: false,
		         captionButtons: {
		             refresh: { visible: false }
		         }
		     });
			$('#show_suggest').click(function () {
				advicemgt.divShowHide(this.lang);
	        });
			$('#show_logout').click(function () {
				advicemgt.divShowHide(this.lang);
	        });
			 $("#saveadvice").click(function(){
	        	  advicemgt.checkForm();
	         });
	         $("#resetadvice").click(function(){
	        	  advicemgt.cleanForm();
	         });
	         $("#anonymity").attr("checked",false);
		},
		loadAdviceTypeList:function(){
			var url = "adviceservice/advicetypelist";
			$.ajax({
				type:'get',
		        dataType: "json",
		        url: url,
		        error: function(){
		            alert(arguments[1]);
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		if(json.length>0){
		        			var advicetypelist = "";
		        			for (var i = 0; i < json.length; i++) {
			                	var row = json[i];
			                	advicetypelist +="<option value='"+row.id+"'>"+row.name+"</option>";
			                }
		        			$("#advicetypelist").html(advicetypelist);
		        		}
		                
		        	}
		        }
		    });
		},
		checkForm:function(){
			var title = $("#textbox").val();
			title = title.replaceAll(" ","");
			if(title ==''||title ==' '||title == undefined){
				advicemgt.showMessge('标题不能为空');
				return false;
			}
			if(title.length>10){
				advicemgt.showMessge('标题不能超过10个字');
				return false;
			}
			
			var content = $("#area").val();
			content = content.replaceAll(" ","");
			if(content ==''||content==' '||content == undefined){
				advicemgt.showMessge('内容不能为空');
				return false;
			}
			if(content.length>100){
				advicemgt.showMessge('内容不能超过100个字');
				return false;
			}
			var isanony = $("#anonymity").attr("checked");
			if(isanony=="checked"){
				$("#creatorid").val(creator);
			   }
			alert(checked);
			
			$("#MyForm").submit();
		},
		divShowHide:function (name) {
			if ($("#" + name).css("display") == "none") {
				
				$("div[class='logout']").hide();
				$("div[class='suggest']").hide();
				$("#" + name).show(500);
			}
			else if ($("#" + name).css("display") == "block") {
				$("#" + name).hide(500);
			}
		},
		showMessge:function(msg){
			$("#smsg").html(msg);
			$('#dialog').wijdialog('open');
		},
		cleanForm:function(){
			 $("#textbox").val("");
			 $("#area").val("");
		}
}