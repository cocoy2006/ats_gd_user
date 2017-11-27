$(document).ready(function() {
	if(username != "null") {
		dispWelcome(username);
		getLefttime(username);
	}
	$("a[rel*=facebox]").facebox();
	// 加载设备信息并用dataTables展现
	loadDevicesInfo1();
});

//var device_count = 0;
function loadDevicesInfo1() {
	$.ajax({
		url: "../servlet/device/GetAllDevices",
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.length != 0) {
				var count = 0;
				for(var i = 0; i < data.length; i++) {
					var id = data[i].id;
					var server = data[i].server;
					var serialNumber = data[i].serialNumber;
					var manufacturer = data[i].manufacturer;
					var model = data[i].model;
					var width = data[i].width;
					var height = data[i].height;
					var state = data[i].state; // 0:free; 1:busy; 2:reserved
//					var preview = data[i].server;
//					if(data[i].isEmulator == 1) { // 0:false; 1:true
//						preview += "image/x/preview.jpg";
//					} else {
//						preview += "image/" + manufacturer + "/" + model + "/preview.jpg";
//					}			
					var preview = server + "image/x/preview.jpg";
					var tr = 
						"<tr>"
						+ "<td><img src='" + preview + "'/></td>"
						+ "<td>" + server + "</td>"
						+ "<td>" + serialNumber + "</td>"
						+ "<td>" + manufacturer + "</td>"
						+ "<td>" + model + "</td>"
						+ "<td>" + width + "×" + height + "</td>";
					if(data[i].isEmulator == 1) {
						tr += "<td>是</td>";
					} else {
						tr += "<td>否</td>";
					}
					var action = server + "servlet/ApplyLock?roll=0.5";
					switch(state) {
						case 0:
							tr += "<td>空闲</td>";
							break;
						case 1:
							tr += "<td>忙碌</td>";
							break;
						case 2:
							tr += "<td>被预约</td>";
							break;
						case 3:
							tr += "<td>未激活</td>";
							break;
					}
					switch(state) {
						case 0: case 2:
//							tr += "<td>空闲</td>"
							tr +=
							"<td>" 
							+ "	<form name='deviceForm" + count++ + "' style='padding: 0; margin: 0 0 10px 0;'" 
							+ "		action='" + action + "' method='POST' onSubmit='return checkLefttime(this)'>"
							+ "		<input type='hidden' name='id' value='" + id + "'/>"
							+ "		<input type='hidden' name='serialNumber' value='" + serialNumber + "'/>"
							+ "		<input type='hidden' name='username' value='" + g_username + "'/>"
							+ "		<input type='hidden' name='lefttime' value='" + g_lefttime + "'/>"
							+ "		<input type='hidden' name='fromUrl' value='" + window.location.href + "'/>"
							+ "		<input type='hidden' name='server' value='" + server + "'/>"
							+ "		<input type='submit' class='submit' value='现在使用' />"
							+ "	</form>" 
							;
							break;
						case 1:
//							tr += "<td>忙碌</td>"
							tr += "<td><label>正在使用</label><br/><br/>";
							break;
//						case 2:
//							tr += "<td>被预约</td>"
//							+ "	<td>" 
//							+ "	<form name='deviceForm" + count++ + "' style='padding: 0; margin: 0 0 10px 0;'" 
//							+ "		action='" + action + "' method='POST' onSubmit='return checkLefttime(this)'>"
//							+ "		<input type='hidden' name='id' value='" + id + "'/>"
//							+ "		<input type='hidden' name='serialNumber' value='" + serialNumber + "'/>"
//							+ "		<input type='hidden' name='username' value='" + g_username + "'/>"
//							+ "		<input type='hidden' name='fromUrl' value='" + window.location.href + "'/>"
//							+ "		<input type='hidden' name='server' value='" + data[i].server + "'/>"
//							+ "		<input type='submit' class='submit' value='预约使用' />"
//							+ "	</form>" 
//							;
//							break;
						case 3:
//							tr += "<td>未激活</td>"
							tr += "<td><label>无法使用</label><br/><br/>";
							break;
					}
					tr += "<input type='button' class='submit' value='预约设备' onClick='doReserve(\"" + server + "\", \"" + serialNumber + "\")'/>";
					$("#devices").append(tr);
//					device_count++;
				}
			} else {
				$("#deviceTable").hide();
				$("#deviceTips").show();
			}
		}
	});
	dispDataTable("deviceTable");
//	var device_count_temp = device_count;
//	var isChangeTimer = window.setInterval(function() {
//		if(device_count != device_count_temp) {
//			device_count_temp = device_count;
//			dispDataTable("deviceTable");
//		} else {window.clearInterval(isChangeTimer);}
//	}, 1600);
}

var g_username;
var g_lefttime;

function checkLefttime(obj) {
	if(g_username == undefined || g_username == "null") {
		alert("温馨提示：操作前请先登陆");
		return false;
	}
	obj.action += "&lefttime=" + g_lefttime; 
	return g_lefttime > 0;
}

function getLefttime(username) {
	g_username = username;
	$.ajax({
		url: "../servlet/GetLefttime",
		type: "POST",
		data: "username=" + username,
		success: function(data) {
			g_lefttime = data - 0;
			var welcome = "<br/>您的当前机时";
			var hour = 0, minute = 0, second = 0;
			if(data < -1) {
				welcome += "出现异常，请联系我们";
			} else if(data > 60 && data < 3600) {
				minute = Math.floor(data / 60);
				second = data % 60;
			} else if(data >= 3600) {
				hour = Math.floor(data / 3600);
				minute = Math.floor((data - hour * 3600) / 60);
				second = (data - hour * 3600) % 60;
			} else {
				second = data;
			}
			welcome += "是 <font>" + hour + "小时" + minute + "分" + second + "秒</font>";
			$("#lefttime").html(welcome);
		}
	});
}
function addLefttime() {
	var time = $("#hiddenReservTime").val();
	time *= 60;
	$.ajax({
		url: "../servlet/AddLefttime",
		type: "POST",
		data: "username=" + g_username + "&time=" + time,
		success: function(data) {
			if(data == "true") {
				getLefttime(g_username);
				$("#hiddenReservTime").val("10");
				jQuery(document).trigger('close.facebox');
				alert("申请成功")
			} else {
				$("#reservTips").html("<font color='red'>提示:申请未成功，请稍候重试</font>");
			}
		}
	});
}

function doReserve(server, serialNumber) {
	if(g_username == undefined || g_username == "null") {
		alert("温馨提示：操作前请先登陆");
		return;
	}
	$("#server").val(server);
	$("#serialNumber").val(serialNumber);
	jQuery.facebox({div:"#reserveDiv"});
}
function reserve() {
	var sTimeVal = $("#facebox input[id='startTime']").val();
	var eTimeVal = $("#facebox input[id='endTime']").val();
	if(sTimeVal == "" || eTimeVal == "") return;
	
	var startTime = new Date(formatDate(sTimeVal)).getTime();
	var endTime = new Date(formatDate(eTimeVal)).getTime();
	
	var diff = (endTime - startTime) / 1000;
	
	if(diff < 10 * 60) {
		$("#facebox span[id='reserveTips']").html("<font color='red'>预约时间至少10分钟!</font>");
		return;
	} else {
		if(diff > g_lefttime) {
			$("#facebox span[id='reserveTips']").html("<font color='red'>您的当前机时不够，请重新选择时间!</font>");
			return;
		} else {
			var server = $("#facebox input[id='server']").val();
			var serialNumber = $("#facebox input[id='serialNumber']").val();
			$.ajax({
				url: "../servlet/reservation/NewReservation",
				type: "POST",
				data: "username=" + g_username + "&server=" + server + "&serialNumber=" 
					+ serialNumber + "&startTime=" + startTime + "&endTime=" + endTime,
				success: function(data) {
					if(data == "0" || data == 0) {
						alert("预约成功");
						// 向定时器队列中插入新对象
						window.location.reload();
					} else {
						$("#facebox span[id='reserveTips']").html("<font color='red'>该时间段已经被预约，请重新选择!</font>");
					}
				}
			});
		}
	}
}
var months = new Array("January", "February", "March", "April", "May", "June", "July", "Augest", "September", "October", "November", "December");
function formatDate(fullDate) {
	var d1 = fullDate.split(" ");
	var d2 = d1[0].split("-");
	var year = d2[0];
	var month = d2[1];
	var day = d2[2];
	return months[month - 1] + " " + day + "," + year + " " + d1[1];
}