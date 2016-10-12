/**
 * Created by Jim on 2016/9/18
 */
define(function() {
	/*//js获取项目根路径，如： http://localhost:8083/uimcardprj
	function getRootPath(){
	    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	    var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    var pathName=window.document.location.pathname;
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    var localhostPaht=curWwwPath.substring(0,pos);
	    //获取带"/"的项目名，如：/uimcardprj
	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    return(localhostPaht+projectName);
	}*/
	function doit() {
		var data;
		//console.log(window.location.href);
		$("#login").on("click",function(){
			console.log($("#name").val() + $("#password").val());
			data = {
					name : $("#name").val(),
					password : $("#password").val()
			}
			$.ajax({
				type : "post",
				url : "views/login",
				data : JSON.stringify(data),
				dataType : "json",
				contentType : "application/json",
				success : function(data) {
					console.log(data);
					if(data){
						alert("success");
						self.location="views/login"
					}
					else{
						alert("请重新登录");
					}
				},
				error : function() {
					alert("error");
					//window.location.href=window.location.href+"views/login";
				}
			});
		});	
		$("#register").on("click",function(){
			self.location="views/register"
		});
	}
	return {
		doit : doit
	}
});