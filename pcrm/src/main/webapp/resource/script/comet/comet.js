


$(document).ready(function() {

//	var url = 'WebRecServlet';
//    $('#work_comet-frame')[0].src = url;
//	timer=setInterval("reload()",20000);
	
}
);
function reload(){
	var url = 'WebRecServlet';
	$('#work_comet-frame')[0].src = url;
}
function update(data) {
	$('#noreadnums').html(data);
}


/* $(document).ready(function(){
                $.ajax({
                    type:"POST",
                    url:"/AsyncServlet/WebLogServlet",
                    dataType:"json",
                    timeout:10000,
                    data:{ajax:"1",time:"10000"},
                    success:function(data,textStatus){
                            alert("ok!");
                    },
                    complete:function(XMLHttpRequest,textStatus){
                            if(XMLHttpRequest.readyState=="4"){
                                alert("");
                            }
                    }
                });
            });*/



