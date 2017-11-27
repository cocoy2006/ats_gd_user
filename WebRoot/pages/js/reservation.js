$(document).ready(function() {
	dispWelcome(username);
	getAllReservations(username, 0, 0);
});

function getAllReservations(username, startTime, endTime) {
	var count = 1;
	$.ajax({
		url: "../servlet/reservation/GetUserReservations",
		type: "POST",
		data: "username=" + username + "&startTime=" + startTime + "&endTime=" + endTime,
		dataType: "json",
		async: false,
		success: function(data) {
//			data = eval("(" + data + ")");
			if(data.length != 0) {
				for(var i = 0; i < data.length; i++) {
					$("#reservationTable tbody").append(addTr(count++, 
						data[i].id, data[i].startTime, data[i].endTime, data[i].state));
//					count++;
				}
			} 
//			else {
//				$("#reservationTable").hide();
//				$("#reservationTips").show();
//			}
		}
	});
	dispDataTable("reservationTable");
	
//	var count_temp = count;
//	var isChangeTimer = window.setInterval(function() {
//		if(count != count_temp) {
//			count_temp = count;
//			dispDataTable("reservationTable");
//		} else {window.clearInterval(isChangeTimer);}
//	}, 1600);
}
function addTr(count, id, startTime, endTime, state) {
	var tr = 
		"<tr>"
		+ 	"<td>" + count + "</td>"
		+ 	"<td>" + parseTime(startTime) + "</td>"
		+ 	"<td>" + parseTime(endTime) + "</td>"
		+ 	"<td>" + parseDiffTime(startTime, endTime) + "</td>";
	if(state == 0 || state == "0") {
		tr += "<td>无</td>";
	} else {
		tr += "<td>" + addForm(id) + "</td>";
	}
	tr += "</tr>";
	return tr;
}
function addForm(id) {
	var form = 
		"<form action='../servlet/reservation/CancelReservation' style='padding: 0; margin: 3px;'>"
		+	"<input type='submit' class='submit' value='删 除'/>"
		+	"<input type='hidden' name='id' value='" + id + "'/>"
		+	"<input type='hidden' name='fromUrl' value='" + window.location.href + "'/>"
		+"</form>";
	return form;
}
function cancelReservationResult(result) {
//	alert("ok")
	if(result == 0 || result == "0") {
		window.location.reload();
	} else {
		alert("删除失败，请稍候重试");
	}
}