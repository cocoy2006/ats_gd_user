$(document).ready(function() {
	bindFocusTips();
	bindFocusEvent();
	validate();
});
var defaultTips = {
	"username" : "请输入您的用户名",
	"email" : "请输入您的邮箱地址"
}
function bindFocusTips() {
	for(tips in defaultTips) {
		var tipsId = "#" + tips + "_tips";
		$(tipsId).html(defaultTips[tips]).hide();
	}
}
function bindFocusEvent() {
	var o_username = "";
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
	$("form[name='form']").validate({
		rules: {
			username: "required", email: {required: true, email: true}
		},
		messages: {
			username: star_mark, email: star_mark
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.next("label"));
		},
		submitHandler: function() {
			$.ajax({
				url: "../servlet/user/PasswordReset",
				type: "POST",
				data: {
					username: $("#username").val(), email: $("#email").val()
				},
				success: function(data) {
					var tips = "";
					switch(data) {
						case 0: case '0':
							tips = "新的密码已经发送至您的邮箱，请查收";
							break;
						case 1: case '1':
							tips = "用户名有错误，请重新输入";
							break;
						case 2: case '2':
							tips = "邮箱地址有错误，请重新输入";
							break;
						case 3: case '3':
							tips = "系统繁忙，请稍候重试";
							break;
					}
					$("#tips").html(tips);
				}
			});
		}
	});
}