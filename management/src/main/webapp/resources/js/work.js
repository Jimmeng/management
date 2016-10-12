/**
 * 
 */
function clearInput(){
	$("#id").val("");
	$("#name").val("");
	$("#writer").val("");
	$("#price").val("");
	$("#number").val("");
}
function coverALineById(data){
	$("tr").each(function() {
		if ($(this).find("input").is(':checked')) {
			$(this).empty();
			$("#tbody").append(
					"<tr><td><input type='checkbox' value='" + data.id + "'></td><td>"
							+ data.name + "</td>" + "<td>" + data.writer
							+ "</td>" + "<td>" + data.price + "</td>" + "<td>"
							+ data.number + "</td></tr>");
		}
		return;
	});
}
function putsInfo(data) {
	$("#tbody").empty();
	for ( var i in data) {
		$("#tbody").append(
				"<tr><td><input type='checkbox' value='" + data[i].id + "'></td><td>"
						+ data[i].name + "</td>" + "<td>" + data[i].writer
						+ "</td>" + "<td>" + data[i].price + "</td>" + "<td>"
						+ data[i].number + "</td></tr>");
	}
};
$("#selectall").on("change",function(){
	var isselectall = $("#selectall").val()=="false";
	$("tr").each(function() {
		$(this).find("input").prop('checked',isselectall);
	});
	$("#selectall").val(isselectall?"true":"false");
});
$("#ensure").on("click",function(){
	console.log($("#id").val());
	var data = {
		id:$("#id").val(),
		name : $("#name").val(),
		writer:$("#writer").val(),
		price:$("#price").val(),
		number:$("#number").val()
	};
	console.log(data);
	$.ajax({
		type : "post",
		url : "addorupdate",
		data : JSON.stringify(data),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(backdata) {console.log(backdata);
			if(backdata.success){
				alert("success");
				clearInput();
				if(backdata.hasOwnProperty("id")){
					$("#tbody").append(
							"<tr><td><input type='checkbox' value='" + backdata.id + "'></td><td>"
									+ data.name + "</td>" + "<td>" + data.writer
									+ "</td>" + "<td>" + data.price + "</td>" + "<td>"
									+ data.number + "</td></tr>");
				}
				else{
					coverALineById(data);
				}
			}
			else{
				alert("fail to add or update");
			}
		},
		error : function() {
			alert("error");
		}
	});
});
$("#delete").on("click", function(){
	var ids = [];
	$("tr").each(function() {
		if ($(this).find("input").is(':checked')) {
			ids.push($(this).find("input").val());
		}
	});console.log(ids);
	if(ids.length==0){
		alert("请选择删除项");
		return;
	}
	if(confirm("确认删除？")){
		$.ajax({
			type : "post",
			url : "delete",
			data : JSON.stringify(
				ids
			),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data){
				if(data){
					alert("操作成功");
				}
				else{
					alert("删除失败或未完全删除");
				}
				$("#tbody").empty();
			},
			error : function(){
				alert("error");
			}
		});
	}
});
$("#update").on("click", function() {
	var checked = 0;
	$("tr").each(function() {
		if ($(this).find("input").is(':checked')) {
			checked++;
		}
	});
	if(checked!=1){
		alert("请选择一项");
		return;
	}
	$("tr").each(function() {
		if ($(this).find("input").is(':checked')) {
			var info = [];
			info[0]=$(this).find("input").val();
			for (var i = 1; i <= 4; i++) {
				info[i] = $(this).children().eq(i).text();
			}
			$("#selectblock").attr("class", "col-xs-6");
			$("#hidebar").show(function() {
				$("#id").val(info[0]);
				$("#name").val(info[1]);
				$("#writer").val(info[2]);
				$("#price").val(info[3]);
				$("#number").val(info[4]);
			});
			
			return false;
		}
	});
});
$("#uploadxls").on("click",function(){
	if(!$('#uploadform')[0])return;
	var data = new FormData($('#uploadform')[0]);
	data.append("test","test");
	console.log(data.get("file"));
	$.ajax({
		type : "post",
		url : "uploadxls",
		data : data,
		contentType : false,
		processData : false,
		success : function(data){
			if(data){
				alert("上傳成功");
			}
			else{
				alert("上傳失敗");
			}
		},
		error : function(){
			alert("error");
		}
	});
});
$("#select").on("click", function() {
	$.post("select", function(data) {
		putsInfo(data);
	})
});
$("#add").on("click", function() {
	$("#selectblock").attr("class", "col-xs-6");
	$("#hidebar").show(1000);
});
$("#cancel").on("click", function() {
	$("#hidebar").hide(1000, function() {
		$("#selectblock").attr("class", "col-xs-12")
		clearInput();
	});
});
$("#cancelupload").on("click", function() {
	$("#uploadbar").hide(1000, function() {
		$("#selectblock").attr("class", "col-xs-12")
	});
});
$("#upload").on("click", function() {
	$("#selectblock").attr("class", "col-xs-6");
	$("#uploadbar").show(1000);
});
$("#selectbywriter").on("click", function() {
	var writer = $("#info").val();
	$.ajax({
		type : "post",
		url : "selectbywriter",
		data : JSON.stringify({
			writer : writer,
			ischecked:$("#mohu").prop("checked")
		}),
		dataType : "json",
		contentType : "application/json",
		success : function(data) {
			putsInfo(data);
		},
		error : function() {
			alert("error");
		}
	});
});

$("#selectbyname").on("click", function() {
	var name = $("#info").val();
	var data = {
			name : name,
			ischecked:$("#mohu").prop("checked")
		};
	$.ajax({
		type : "post",
		url : "selectbyname",
		data : JSON.stringify(data),
		dataType : "json",
		contentType : "application/json",
		success : function(data) {
			putsInfo(data);
		},
		error : function() {
			alert("error");
		}
	});
});