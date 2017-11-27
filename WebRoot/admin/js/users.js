var oTable;
$(document).ready(function() {
	getAllUsers();
	oTable = dispDataTable("userTable");
});

function getAllUsers() {
	$.ajax({
		url: "../servlet/user/GetAllUsers",
		type: "POST",
		dataType: "JSON",
		async: false,
		success: function(data) {
			data = eval("(" + data + ")");
			$(data).each(function(i, user) {
				$("#userTable tbody").append(addTr1(user.id, user.username, user.nickname, user.password, 
					user.email, user.phone_no, user.lefttime, user.state, user.role));
			});
		}
	});
}
function addTr1(id, username, nickname, password, email, phone_no, lefttime, state, role) {
	var tr = 
		"<tr>"
		+	"<td>" + id + "</td>"
		+	"<td>" + username + "</td>"
		+	"<td>" + nickname + "</td>"
		+	"<td>" + password + "</td>"
		+	"<td>" + email + "</td>"
		+	"<td>" + phone_no + "</td>"
		+	"<td>" + lefttime + "</td>"
		;
	switch(state) {
		case 0: // 正常
			tr += "<td>正常</td>";
			break;
		case 1: // 未激活
			tr += "<td>未激活</td>";
			break;
		case 2: // 已销户
			tr += "<td>已销户</td>";
			break;
		case 3: // 限制登录
			tr += "<td>限制登录</td>";
			break;
		case 4: // 限制操作
			tr += "<td>限制操作</td>";
			break;
		default:
			tr += "<td></td>";
			break;
	}
	switch(role) {
		case 0: // 管理员
			tr += "<td>管理员</td>"
				+ "<td><input type='checkbox' value='" + id + "' disabled/></td>";
			break;
		case 1: // 普通用户
			tr += "<td>普通用户</td>"
				+ "<td><input type='checkbox' name='ids' value='" + id + "'/></td>";
			break;
		default:
			tr += "<td></td>";
			break;
	}
//	tr += "<td><input type='checkbox' name='ids' value='" + id + "'/></td>";
	tr += "</tr>";
	return tr;
}

function unactivateUsers() {
	genAjaxReq("注销用户后，用户将无法登录，确定继续?", "../servlet/user/UpdateStates?state=2");
}

function activateUsers() {
	genAjaxReq("激活用户后，用户可以登录系统，确定继续?", "../servlet/user/UpdateStates?state=0");
}

function deleteUsers() {
	genAjaxReq("用户删除后将无法恢复，确定继续?", "../servlet/user/DeleteUsers");
}

function genAjaxReq(confirmContent, url) {
	var dataString = $("input", oTable.fnGetNodes()).serialize();
	if(dataString.search("ids") == -1) return;
	if(confirm(confirmContent)) {
		$.ajax({
			url: url,
			data: dataString,
			success: function(data) {
				switch(data) {
					case 0: case "0":
						window.location.reload();
						break;
					case -1: case "-1":
						alert("系统繁忙，请稍后再试!");
						break;
				}
			}
		});
	}
}