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
				background: #EBEBEB;
			}
			
			.right_main {
				padding: 0 10px;
			}
			
			.right_main_tille {
				height: 28px;
				padding-left: 20px;
				font: 12px Arial, Helvetica, sans-serif;
				color: #666666;
				line-height: 28px;
				background: url(images/it_img_11.jpg) 1px center no-repeat;
			}
		</style>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var username = "<%=request.getSession().getAttribute("username") %>";
				if(username == "null") window.parent.location = "index.jsp";
			});
		</script>		
	</head>

	<body>
		<div class="right">
			<div class="right_main">
				<div class="right_main_tille">
					首页 > 首页
				</div>
				<img src="images/welcome_bg.jpg" />
			</div>
		</div>
	</body>
</html>
