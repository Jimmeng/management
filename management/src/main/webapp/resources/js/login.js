/**
 * 
 */
function show(times){
	
	$("#time").html(3-times);
	console.log(times);
	if(times==3) {
		self.location="work";
	}
}
function count(){
	var times=1;
	setInterval(function(){show(times);times++;},1000);
	
}
$(document).ready(
		count()
		);