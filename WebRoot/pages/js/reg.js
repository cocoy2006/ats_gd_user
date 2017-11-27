$(document).ready(function() {
	bindFocusTips();
	bindFocusEvent();
	validate();
});

var defaultTips = {
	"r_u" : "请输入您的用户名",
	"r_n" : "请输入您的昵称",
	"r_p" : "密码由5-20个字符组成",
	"r_p_ag" : "请再次确认您的密码",
	"r_email" : "请输入正确的邮箱",
	"r_phone_no" : "请输入您的手机号码"
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
			if("r_u" == event.data.tips) {
				o_username = $(this).val();
			}
		});
		if(tips == "r_u") {
			$(id).bind("blur", function() { // 判断用户名是否可用
				if($(this).val() != o_username) {
					checkUsername(this);
				}
			});
		} else {
			$(id).bind("blur", {tipsId: tipsId}, function(event) {
				$(event.data.tipsId).hide();
			});
		}
	}
}
function checkUsername(obj) {
	var username = $(obj).val();
	var tips_e = $(obj).parent("td").next("td").first();
	var submit = $("form[name='reg_form'] :submit");
	var url = "../servlet/CheckUsername";
	$.ajax({
		url: url,
		type: "POST",
		data: {username: username},
		success: function(msg) {
			if(msg == 1) {
				tips_e.html("<label class='tips'><img src='../image/no.png'/>&nbsp;<font color='red'>用户名不可用</font></label>");
				submit.attr("disabled", "disabled");
			} else {
				tips_e.html("<label class='tips'><img src='../image/yes.png'/>&nbsp;<font color='green'>用户名可以使用</font></label>");
				submit.removeAttr("disabled");
			}
		}
	});
}
var star_mark = "<em>&nbsp;*</em>";
function validate() {
	$("form[name='reg_form']").validate({
		rules: {
			r_u: "required", r_p: {required: true, minlength: 5, maxlength: 20},
			r_p_ag: {required: true, equalTo: "#r_p"},
			r_email: {required: true, email: true}
		},
		messages: {
			r_u: star_mark, r_p: star_mark, r_p_ag: star_mark, r_email: star_mark
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.next("label"));
		},
		submitHandler: function() {
			$("#r_submit").val("正 在 提 交 信 息...").attr("disabled", "disabled");
			$.ajax({
				url: "../servlet/Reg",
				type: "POST",
				data: {
					r_u: $("#r_u").val(), r_n: $("#r_n").val(), r_p: $("#r_p").val(), r_email: $("#r_email").val(), r_phone_no: $("#r_phone_no").val()
				},
				success: function(data) {
					if(data == 1 || data == "1") {
						$("#reg_tips").html("<font color='green'>注册成功，请登录邮箱激活账号</font>");
					} else if(data == -1 || data == "-1") {
						$("#reg_tips").html("<font color='red'>用户已经注册!</font>");
					} else {
						$("#reg_tips").html("<font color='red'>注册失败!</font>");
					}
					$("#r_submit").val("免 费 注 册").removeAttr("disabled");
				}
			});
		}
	});
}