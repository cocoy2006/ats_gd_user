$(document).ready(function() {
	dispWelcome(username);
	getUser(username);
	bindFocusTips();
	bindFocusEvent();
	validate();
});
var defaultTips = {
	"u_p" : "密码由5-20个字符组成",
	"u_p_ag" : "请再次确认您的新密码"
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
			u_p: {required: true, minlength: 5, maxlength: 20},
			u_p_ag: {required: true, equalTo: "#u_p"}
		},
		messages: {
			u_p: star_mark, u_p_ag: star_mark
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.next("label"));
		},
		submitHandler: function() {
			$.ajax({
				url: "../servlet/user/PasswordModify",
				type: "POST",
				data: {
					u_id: $("#u_id").val(), u_p: $("#u_p").val()
				},
				success: function(data) {
					if(data == 0 || data == "0") {
						window.location = "logout.jsp";
					} else {
						$("#updateTips").html("<font color='red'>修改失败，请稍后重试!</font>");
					}
				}
			});
		}
	});
}

function getUser(username) {
	$.ajax({
		url: "../servlet/GetUser",
		type: "POST",
		data: "username=" + username,
		dataType: "JSON",
		success: function(data) {
			data = eval("(" + data + ")");
			$("#u_id").val(data.id);
		}
	});
}