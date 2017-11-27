$(document).ready(function() {
//	getAllLogs(server, serialNumber);
});

var count = 0;
function getAllLogs(server, serialNumber) {
	$.ajax({
		url: "../servlet/log/GetDeviceLogs",
		type: "POST",
		data: "server=" + server + "&serialNumber=" + serialNumber + "&startTime=0&endTime=0",
		dataType: "JSON",
		success: function(data) {
			data = eval("(" + data + ")");
			if(data.length != 0) {
				for(var i = 0; i < data.length; i++) {
					$("#logTable tbody").append(addTr(data[i].time, data[i].operation, data[i].username));
					count++;
				}
			} else {
				$("#logTable").hide();
				$("#logTips").show();
			}
		}
	});
	
	var count_temp = count;
	var isChangeTimer = window.setInterval(function() {
		if(count != count_temp) {
			count_temp = count;
			dispDataTable("logTable");
		} else {window.clearInterval(isChangeTimer);}
	}, 1600);
}
function addTr(time, operation, username) {
	var tr = 
		"<tr>"
		+	"<td>" + username + "</td>"
		+	"<td>" + parseOperation(operation) + "</td>"
		+ 	"<td>" + parseTime(time) + "</td>"
		+"</tr>"
	return tr;
}