var oTable;
function init() {
	loadDevicesInfo();
	oTable = dispDataTable("deviceTable");
	$("#dispNewDeviceDiv").bind("click", function() {
		jQuery.facebox({div: "#newDeviceDiv"});
		validation("../servlet/device/NewDevice");
	});
}

//var device_count = 0;
function loadDevicesInfo() {
	$.ajax({
		url: "../servlet/device/GetAllDevices",
		type: "POST",
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.length != 0) {
				for(var i = 0; i < data.length; i++) {
					var tr = 
						"<tr>"
						+ "<td>" + data[i].server + "</td>"
						+ "<td>" + data[i].serialNumber + "</td>"
						+ "<td>" + data[i].manufacturer + "</td>"
						+ "<td>" + data[i].model + "</td>"
						+ "<td>" + data[i].width + "×" + data[i].height + "</td>";
					if(data[i].isEmulator == 1) {
						tr += "<td>是</td>";
					} else {
						tr += "<td>否</td>";
					}
					switch(data[i].state) {
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
					tr += "<td><input type='checkbox' name='ids' value='" + data[i].id + "'/></td>";
					tr += "<td>" + addViewForm(data[i].id, data[i].server, data[i].serialNumber, data[i].os) + "</td>";
					tr += "</tr>";
					$("#deviceTable tbody").append(tr);
//					device_count++;
				}
			} else {
				$("#deviceTable").hide();
				$("#deviceTips").show();
			}
		}
	});
	
//	var device_count_temp = device_count;
//	var isChangeTimer = window.setInterval(function() {
//		if(device_count != device_count_temp) {
//			device_count_temp = device_count;
//			oTable = dispDataTable("deviceTable");
//		} else {window.clearInterval(isChangeTimer);}
//	}, 500);
}
function restartServer(server, serialNumber) {
	var url = server + "servlet/Restart";
//	alert("restart server begin...")
	var i = document.getElementById("restartIframe");
	if(!i) return false;
//	i.bind("load", function() {
//		
//	});
//	var i = document.createElement("iframe");
//	i.name = "restartIframe";
	i.onload = function() {
		var result = i.contentWindow.document.body.innerHTML;
		alert(result);
//		if(result != "") {
			if(result == 1 || result == "1") alert("成功启动，可进行设备状态调整");
			else if(result == "<pre></pre>" || result == -1 || result == "-1") alert("失败！请确认服务器已正常启动");
//		}
		
//		$(i).remove();
	};
	document.body.appendChild(i);
	var f = document.createElement("form");
	f.action = url;
	f.method = "post";
	f.target = "restartIframe";
	i.appendChild(f);
	f.submit();
	$(i).hide();
//	$(i).remove();
//	$.ajax({
//		url: "../servlet/Restart",
//		type: "POST",
//		data: "server=" + server + "&serialNumber=" + serialNumber,
////		async: false,
//		success: function(data) {
////			alert(data);
//		}
//	});
}
function addViewForm(id, server, serialNumber, os) {
	var form = 
		"<form method='POST' action='logview.jsp' target='_blank' style='padding: 0; margin: 0;'>"
		+	"<input type='hidden' name='server' value='" + server + "'/>"
		+	"<input type='hidden' name='serialNumber' value='" + serialNumber + "'/>"
		+	"<input type='hidden' name='id' value='" + id + "'/>"
		+	"<input type='submit' class='submit' value='查看日志'/>&nbsp;"
		+	"<input type='button' class='submit' value='编辑信息' onClick='dispDeviceInfo(" + id + ")'/>&nbsp;"
		;
//	if(!isIOS(os)) {
//		form += "<input type='button' class='submit' value='重启设备' onClick='restartServer(\"" + server + "\",\"" + serialNumber + "\")'/>"
//	}
	form +=	"</form>";
	return form;
}

function isIOS(os) {
	return /iOS.*?/.test(os);
}

var star_mark = "<em>&nbsp;*</em>";
function validation(url) {
	$("#facebox form[name='newDeviceForm']").validate({
		rules: {
			server: {required: true},
			serialNumber: {required: true},
			width: {required: true, digits: true},
			height: {required: true, digits: true},
			os: {required: true}
		},
		messages: {
			server: star_mark, serialNumber: star_mark, manufacturer: star_mark, 
			model: star_mark, width: star_mark, height: star_mark, os: star_mark
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.nextAll("label").first());
		},
		submitHandler: function() {
			$.ajax({
				url: url,
				type: "POST",
				data: $("#facebox form").serialize(),
				success: function(data) {
					var tips = $("#facebox label[id='newDeviceTips']");
					switch(data) {
						case 0: case "0":
							tips.addClass("green").html("成功，2秒钟后自动消失!");
							window.setTimeout(function() {
								jQuery(document).trigger('close.facebox');
								window.location.reload();
							}, 1600);
							break;
						case 1: case "1":
							tips.addClass("red").html("设备已经存在，请重新指定!");
							break;
						case -1: case "-1":
							tips.addClass("red").html("系统繁忙，请稍候重试!");
							break;
					}
				}
			});
		}
	});
}

function dispDeviceInfo(id) {
	$.ajax({
		url: "../servlet/device/GetDevice",
		type: "POST",
		data: "id=" + id,
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.length != 0) {
				jQuery.facebox({div: "#newDeviceDiv"});
				$("#facebox input[name='id']").val(id);
				$("#facebox input[name='imei']").val(data.imei);
				$("#facebox input[name='server']").val(data.server);
				$("#facebox input[name='serialNumber']").val(data.serialNumber);
				$("#facebox input[name='manufacturer']").val(data.manufacturer);
				$("#facebox input[name='model']").val(data.model);
				$("#facebox input[name='width']").val(data.width);
				$("#facebox input[name='height']").val(data.height);
				$("#facebox input[name='os']").val(data.os);
				$("#facebox input[name='isEmulator'][value='" + data.isEmulator + "']").attr("checked", "checked");
				validation("../servlet/device/UpdateDevice");
			} else {}
		}
	});
	
}

function unactiveDevices() {
	genAjaxReq("移除设备后，用户将无法继续使用，但不会删除设备，确定继续?", "UnactiveDevices");
}

function activeDevices() {
	genAjaxReq("激活设备后，用户将可以使用设备，确定继续?", "ActiveDevices");
}

function deleteDevices() {
	genAjaxReq("设备删除后将无法恢复，确定继续?", "DeleteDevices");
}

function genAjaxReq(confirmContent, url) {
	var dataString = $("input", oTable.fnGetNodes()).serialize();
	if(dataString.search("ids") == -1) return;
	if(confirm(confirmContent)) {
		$.ajax({
			url: "../servlet/device/" + url,
			data: dataString + "&roll=" + Math.random(),
			success: function(data) {
//				alert(data);
				switch(data) {
					case 1: case "1":
						window.location.reload();
						break;
					case 0: case "0": 
						alert("服务器异常，请稍后重试!");
						break;
					case -1: case "-1":
						alert("请每次移除一个设备!");
						break;
				}
			}
		});
	}
}