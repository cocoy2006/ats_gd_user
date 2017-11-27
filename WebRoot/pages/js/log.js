$(document).ready(function() {
	dispWelcome(username);
	getAllLogs(username);
});

//var count = 0;
function getAllLogs(username) {
	$.ajax({
		url: "../servlet/log/GetUserLogs",
		type: "POST",
		data: "username=" + username + "&startTime=0&endTime=0",
		dataType: "JSON",
		async: false,
		success: function(data) {
			data = eval("(" + data + ")");
			for(var i = 0; i < data.length; i++) {
				$("#logTable tbody").append(addTr(data[i].time, 
					data[i].operation, data[i].server, data[i].serialNumber));
//				count++;
			}
			dispDataTable("logTable");
		}
	});
	
//	var count_temp = count;
//	var isChangeTimer = window.setInterval(function() {
//		if(count != count_temp) {
//			count_temp = count;
//			dispDataTable("logTable");
//		} else {window.clearInterval(isChangeTimer);}
//	}, 1600);
}
function addTr(time, operation, server, serialNumber) {
	var deviceInfo;
	if(!server || !serialNumber) {
		deviceInfo = "空";
	} else {
		deviceInfo = server + serialNumber;
	}
	var tr = 
		"<tr>"
		+ 	"<td>" + parseTime(time) + "</td>"
		+	"<td>" + parseOperation(operation) + "</td>"
		+	"<td>" + deviceInfo + "</td>"
		+"</tr>"
	return tr;
}