/**
 * 
 */
console.log("asd");
$("#submit").on("click",function(){
	var name=$("#name").val();
	var password=$("#password").val();
	var ensure=$("#ensure").val();
	if(password!=ensure){
		alert("两次密码输入不相同");
		$("#ensure").val("");
	}
	else{
		$.ajax({
			data: JSON.stringify({
					name:name,
					password:password
			}),
			type:"post",
			url:"register",
			dataType : "json",
			contentType : "application/json",
			success: function(data){
				if(data){
					alert("success");
					self.location="index"
				}
				else{
					alert("用户名已被注册");
					$("#name").val("");
					$("#password").val("");
					$("#ensure").val("");
				}
			},
			error: function(){
				alert("error");
			}
		});
	}
});