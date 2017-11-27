function dispWelcome(username) {
	$("#main_right_login").hide();
	var grxx = $("#main_right_login_grxx");
	grxx.children("p").children("span").html(username);
	grxx.show();
}

var g_role;
function login(role) {
	var username = $("#main_right_login_01").val();
	var password = $("#main_right_login_02").val();
	if(!username || !password) return;
	$.ajax({
		url: "../servlet/user/CheckUser",
		type: "POST",
		data: "username=" + username + "&password=" + password + "&role=" + role,
		dataType: "JSON",
		success: function(data) {
			if(data != "") {
				if(data == 1 || data == "1") {
					alert("请使用管理员链接登陆系统");
				} else {
					data = eval("(" + data + ")");
					switch(data.state) {
						case 0: case "0": // 正常
							g_role = data.role;
							switch(data.role) {
								case 0: case "0": // 管理员
									alert("请使用管理员链接登陆系统");
									break;
								case 1: case "1": // 普通用户
									window.location = "index.jsp";
									break;
							}
							break;
						case 1: case "1": // 未激活
							alert("请登录邮箱激活您的账号");
							break;
						case 2: case "2": // 限制登录
							alert("您的账户异常，请联系我们!");
							break;
						case 3: case "3": // 限制操作
							break;
					}
				}
			} else {
				alert("用户名或密码错误，请重新输入");
			}
		}
	});
}

function isLogin(username) {
	return username != "null";
}

function forward(username, url) {
	if(isLogin(username)) {
		window.location = url;
	} else {
		window.location = "index.jsp";
	}
}

function parseTime(time) {
	var d = new Date(time);
	var year = d.getFullYear();
	var month = isSingleDigit(d.getMonth() + 1);
	var date = isSingleDigit(d.getDate());
	var hour = isSingleDigit(d.getHours());
	var minute = isSingleDigit(d.getMinutes());
	var second = isSingleDigit(d.getSeconds());
	return year + "年" + month + "月" + date + "日" + hour + ":" + minute + ":" + second;
}
function isSingleDigit(digit) {
	if(digit < 10) return '0' + digit;
	else return digit;
}

function parseDiffTime(startTime, endTime) {
	var diff = (endTime - startTime) / 1000;
	var h = Math.floor(diff / 3600);
	var temp = diff - h * 3600;
	var m = Math.floor(temp / 60);
	var s = temp - m * 60;
	if(m < 10) m = '0' + m;
	if(s < 10) s = '0' + s;
	return h + "小时" + m + "分" + s + "秒";
}

function isURL(url) {
	var regex = "^((https|http)://)"
		+ "(([0-9]{1,3}.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
		+ "|" // 允许IP和DOMAIN（域名）
		+ "([0-9a-z_!~*'()-]+.)*" // 域名- www.
		+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]." // 二级域名
		+ "[a-z]{2,6})" // first level domain- .com or .museum
		+ "(:[0-9]{1,4})?" // 端口- :80
		+ "((/?)|" // a slash isn't required if there is no file name
		+ "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var re = new RegExp(regex);
	//re.test()
	if (re.test(url)) {
		return (true);
	} else {
		return (false);
	}
}

function parseOperation(operation) {
//	operation = operation.toUpperCase();
	if(operation == "LOGIN") {
		operation = "登陆系统";
	} else if(operation == "LOGOUT") {
		operation = "退出系统";
	} else if(operation == "LOCK") {
		operation = "锁定设备";
	} else if(operation == "UNLOCK") {
		operation = "释放设备";
	} else {
		var ops = operation.split(" ");
		switch(ops[0]) {
			case "TOUCH":
				switch(ops[3]) {
					case "DOWN":
						operation = "长按屏幕";
						break;
					case "DOWN_AND_UP":
						operation = "点击屏幕";
						break;
				}
				operation +=  "(" + ops[1] + "," + ops[2] + ")";
				break;
			case "PRESS":
				if(ops[1] == "BACK" || ops[1] == "SEARCH" || ops[1] == "UP" || ops[1] == "DOWN"
					|| ops[1] == "LEFT" || ops[1] == "RIGHT" || ops[1] == "CENTER" || ops[1] == "ENTER") {
					operation = "点击" + ops[1] + "键";
				} else {
					switch(ops[2]) {
						case "DOWN":
							operation = "长按";
							break;
						case "DOWN_AND_UP":
							operation = "点击";
							break;
					}
					operation += ops[1] + "键";
				}
				break;
			case "DRAG":
				operation = "滑动屏幕(" + ops[1] + "," + ops[2] + ")到(" + ops[3] + "," + ops[4] + ")";
				break;
			case "TYPE":
				operation = "输入" + ops[1];
				break;
			case "SYNC":
				operation = "同步" + ops[1].substring(ops[1].lastIndexOf("/") + 1, ops[1].length);
				break;
			case "REMOVE":
				operation = "删除" + ops[1];
				break;
			case "INSTALL":
				operation = "安装" + ops[1].substring(ops[1].lastIndexOf("/") + 1, ops[1].length);
				break;
			case "REINSTALL":
				operation = "覆盖安装" + ops[1].substring(ops[1].lastIndexOf("/") + 1, ops[1].length);
				break;
			case "UNINSTALL":
				operation = "卸载" + ops[1];
				break;
			case "START":
				operation = "启动" + ops[1].substring(0, ops[1].indexOf("/"));
				break;
			case "UPLOAD":
				operation = "上传" + ops[1];
				break;
		}
	}
	return operation;
}

(function($) {
/*
 * Function: fnGetColumnData
 * Purpose:  Return an array of table values from a particular column.
 * Returns:  array string: 1d data array 
 * Inputs:   object:oSettings - dataTable settings object. This is always the last argument past to the function
 *           int:iColumn - the id of the column to extract the data from
 *           bool:bUnique - optional - if set to false duplicated values are not filtered out
 *           bool:bFiltered - optional - if set to false all the table data is used (not only the filtered)
 *           bool:bIgnoreEmpty - optional - if set to false empty values are not filtered from the result array
 * Author:   Benedikt Forchhammer <b.forchhammer /AT\ mind2.de>
 */
	try {
		$.fn.dataTableExt.oApi.fnGetColumnData = function (oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty ) {
		    // check that we have a column id
		    if ( typeof iColumn == "undefined" ) return new Array();
		     
		    // by default we only wany unique data
		    if ( typeof bUnique == "undefined" ) bUnique = true;
		     
		    // by default we do want to only look at filtered data
		    if ( typeof bFiltered == "undefined" ) bFiltered = true;
		     
		    // by default we do not wany to include empty values
		    if ( typeof bIgnoreEmpty == "undefined" ) bIgnoreEmpty = true;
		     
		    // list of rows which we're going to loop through
		    var aiRows;
		     
		    // use only filtered rows
		    if (bFiltered == true) aiRows = oSettings.aiDisplay; 
		    // use all rows
		    else aiRows = oSettings.aiDisplayMaster; // all row numbers
		 
		    // set up data array    
		    var asResultData = new Array();
		     
		    for (var i=0,c=aiRows.length; i<c; i++) {
		        iRow = aiRows[i];
		        var aData = this.fnGetData(iRow);
		        var sValue = aData[iColumn];
		         
		        // ignore empty values?
		        if (bIgnoreEmpty == true && sValue.length == 0) continue;
		 
		        // ignore unique values?
		        else if (bUnique == true && jQuery.inArray(sValue, asResultData) > -1) continue;
		         
		        // else push the value onto the result data array
		        else asResultData.push(sValue);
		    }
		     
		    return asResultData;
		}
	} catch(e) {
		jQuery.error = e.message;
	}
}(jQuery));
 
function fnCreateSelect( aData ) {
    var r='<select><option value=""></option>', i, iLen=aData.length;
    for ( i=0 ; i<iLen ; i++ )
    {
        r += '<option value="'+aData[i]+'">'+aData[i]+'</option>';
    }
    return r+'</select>';
}

function dispDataTable(id) {
	var oTable = $("#" + id).dataTable({
		"oLanguage": {
//			"sUrl": "../plugins/dataTables/de_CHN.txt"
			"sProcessing": "正在载入...",
			"sLengthMenu": " 显示_MENU_条 ",
			"sLoadingRecords": "正在载入...",
			"sZeroRecords": "暂时没有记录",
			"sInfo": " 从_START_ 到 _END_ 条记录——总记录数为 _TOTAL_ 条",
			"sInfoEmpty": "暂时没有记录",
			"sInfoFiltered": "(全部记录数 _MAX_  条)",
			"sInfoPostFix": "",
			"sSearch": "模糊搜索",
			"oPaginate": {
				"sFirst":    " 第一页 ",
				"sPrevious": " 上一页 ",
				"sNext":     " 下一页 ",
				"sLast":     " 最后一页 "
			}
		},
//		"bJQueryUI": true,
		"bStateSave": true,
		"bDestroy": true,
//		"bRetrieve": true,
		"sPaginationType": "full_numbers"
	});
	return oTable;
}


function dispDataTableWithFilter(id) {
	var oTable = dispDataTable(id);
	$("#" + id + " tfoot tr th").each(function(i) {
        this.innerHTML = fnCreateSelect(oTable.fnGetColumnData(i));
        $('select', this).change(function() {
            oTable.fnFilter($(this).val(), i);
        } );
    });
	return oTable;
}

/**for admin/logquery.jsp--start*/
var logqueryDT = null;
function query() {
	clearLogqueryTips();
	if(logqueryDT != null) {
		logqueryDT.fnClearTable();
	}
//	var count = 0;
	var username = $("#username").val();
	var device = $("#device").val();
	var startTime = 0;
	if($("#startTime").val() != "") startTime = new Date($("#startTime").val()).getTime();
	var endTime = 0;
	if($("#endTime").val() != "") endTime = new Date($("#endTime").val()).getTime();
	var url;
	var data;

	if(device == "") {
		if(username == "") {
			url = "../servlet/log/GetAllLogs";
			data = "roll=0.1";
		} else {
			url = "../servlet/log/GetUserLogs";
			data = "username=" + username;
		}
	} else { // device != ""
		if(!isURL(device)) {
			$("#deviceTips").show();
			return;
		}
		var server = device.substr(0, device.lastIndexOf("/") + 1);
		var serialNumber = device.substr(device.lastIndexOf("/") + 1);
		if(username == "") {
			url = "../servlet/log/GetDeviceLogs";
			data = "server=" + server + "&serialNumber=" + serialNumber;
		} else {
			url = "../servlet/log/GetSpecLogs";
			data = "username=" + username + "&server=" + server + "&serialNumber=" + serialNumber;
		}
	}
	data += "&startTime=" + startTime + "&endTime=" + endTime;
//	jQuery.facebox('正在载入...');
	$.ajax({
		url: url,
		type: "POST",
		data: data,
		dataType: "json",
		async: false,
		success: function(data) {
			if(data == undefined || data == "" || data == null) {
//				$("#queryResultDiv").hide();
//				$("#queryResultTips").html("无日志记录!").show();
			} else {
//				$("#queryResultTips").hide();
//				$("#queryResultDiv").show();
//				if(logqueryDT != null) {
//					logqueryDT.fnClearTable();
//				}
				$("#queryResultTable tbody").empty();
				for(var i = 0; i < data.length; i++) {
					$("#queryResultTable tbody").append(addLogqueryTr(i + 1, data[i].username, data[i].server, data[i].serialNumber, data[i].operation, data[i].time));
//					count++;
				}
			}
		}, error: function() {
//			$("#queryResultDiv").hide();
//			$("#queryResultTips").html("无日志记录!").show();
		}
	});
	logqueryDT = dispDataTable("queryResultTable");
//	jQuery(document).trigger('close.facebox');
//	var count_temp = count;
//	var isChangeTimer = window.setInterval(function() {
//		if(count != count_temp) {
//			count_temp = count;
//			logqueryDT = dispDataTable("queryResultTable");
//		} else {window.clearInterval(isChangeTimer);}
//	}, 500);
}

function addLogqueryTr(i, username, server, serialNumber, operation, time) {
	if(username == undefined) username = "无";
	if(server == undefined) server = "无";
	if(serialNumber == undefined) serialNumber = "无";
	var tr = 
		"<tr>"
		+	"<td>" + i + "</td>"
		+	"<td>" + username + "</td>"
		+	"<td>" + parseOperation(operation) + "</td>"
		+	"<td>" + server + "</td>"
		+	"<td>" + serialNumber + "</td>"
		+ 	"<td>" + parseTime(time) + "</td>"
		+"</tr>"
	return tr;
}

function clearLogqueryTips() {
	$("#deviceTips").hide();
}
/**for admin/logquery.jsp--end*/