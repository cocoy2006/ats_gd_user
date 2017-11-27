$(document).ready(function() {
	dispWelcome(username);
	getUser(username);
	bindFocusTips();
	bindFocusEvent();
	validate();
});
var defaultTips = {
	"u_n" : "请输入您的昵称",
	"u_email" : "请输入您的新邮箱",
	"u_phone_no" : "请输入您的新手机号码"
}
function bindFocusTips() {
	for(tips in defaultTips) {
		var tipsId = "#" + tips + "_tips";
		$(tipsId).html(defaultTips[tips]).hide();
	}
}
function bindFocusEvent() {
	for(tips in defaultTips) {
		var id = "#" + tips;
		var tipsId = id + "_tips";
		$(id).bind("focus", {tipsId: tipsId, tips: tips}, function(event) {
			$(event.data.tipsId).show();
		});
		$(id).bind("blur", {tipsId: tipsId}, function(event) {
			$(event.data.tipsId).hide();
		});
	}
}
var star_mark = "<em>&nbsp;*</em>";
function validate() {
	$("form[name='updateForm']").validate({
		rules: {
			u_email: {email: true}
		},
		messages: {
			u_email: star_mark
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.next("label"));
		},
		submitHandler: function() {
			var url = "../servlet/UpdateUser";
			$.ajax({
				url: url,
				type: "POST",
				data: {
					u_id: $("#u_id").val(), u_n: $("#u_n").val(), u_email: $("#u_email").val(), u_phone_no: $("#u_phone_no").val()
				},
				success: function(data) {
					if(data == 1) {
						$("#updateTips").html("<font color='green'>修改成功</font>");
					} else {
						$("#updateTips").html("<font color='red'>修改失败，请稍后重试</font>");
					}
				}
			});
		}
	});
}
var g_username;
function getUser(username) {
	g_username = username;
	$.ajax({
		url: "../servlet/GetUser",
		type: "POST",
		data: "username=" + username,
		dataType: "JSON",
		success: function(data) {
			data = eval("(" + data + ")");
			$("#u_id").val(data.id);
			$("#u_n").val(data.nickname);
			$("#u_email").val(data.email);
			$("#u_phone_no").val(data.phone_no);
		}
	});
}