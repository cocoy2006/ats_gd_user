<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>中国电信手机测试云平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<style type="text/css" media="screen">
			@import "stlye.css";
			body {
				margin: 0 auto;
				text-align: center;
			}
			input {
				position: absolute; 
				line-height: 20px; 
				color: #C6FF00; 
				background: none; 
				border: none; 
			}
		</style>		
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript">
			function login(role) {
				var username = $("#username").val();
				var password = $("#password").val();
				if(!username || !password) return;
				$.ajax({
					url: "../servlet/user/CheckUser",
					type: "POST",
					data: "username=" + username + "&password=" + password + "&role=" + role,
					dataType: "JSON",
					success: function(data) {
						if(data != "") {
							if(data == 1 || data == "1") {
								alert("请使用普通用户链接登陆系统");
							} else {
								data = eval("(" + data + ")");
								switch(data.state) {
									case 0: case "0": case 4: case "4": // 正常
										switch(data.role) {
											case 0: case "0": // 管理员
												window.location = "home.jsp";
												break;
											case 1: case "1": // 普通用户
												alert("您不是管理员，请使用普通用户链接登录系统");
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
		</script>		
	</head>

	<body>
		<div style="width: 980px; height: 449px; position: relative; margin: 0 auto; text-align: center; background: url(images/lodin.jpg) no-repeat;">
			<input type="text" id="username"
				style="left: 430px; top: 193px; width: 160px; height: 20px; font-family: Geneva, Arial, Helvetica, sans-serif;"/>
			<input type="password" id="password"
				style="left: 430px; top: 225px; width: 160px; height: 20px;"/>
			<input type="button" onClick="login(0)"
				style="left: 426px; top: 270px; width: 88px; height: 30px; cursor: pointer;"/>
		</div>	
	</body>
</html>
