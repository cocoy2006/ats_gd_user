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
				background: #1D588C;
			}
			
			.header {
				width: 100%;
				position: relative;
				height: 74px;
				background: url(images/header_bg.jpg) repeat-x;
			}
			
			.header_logo {
				position: absolute;
				left: 0;
				top: 0;
				display: block;
				overflow: hidden;
				border: none;
			}
			
			.exit {
				position: absolute;
				right: 40px;
				top: 0;
				width: 90px;
				height: 26px;
				background: url(images/eixt_bg.jpg) no-repeat;
				border: none;
				vertical-align: middle;
				cursor: pointer;
			}
			
			.urse {
				position: absolute;
				right: 140px;
				top: 7px;
				text-align: right;
				font: 14px Arial, Helvetica, sans-serif;
				text-align: right;
				color: #B9CED8;
			}
		</style>
		<script type="text/javascript" src="../js/jquery.js"></script>		
		<script type="text/javascript">
			$(document).ready(function() {
				var username = "<%=request.getSession().getAttribute("username") %>";
				if(username == "null") {
					window.location = "index.jsp";
				}
			});
		</script>
	</head>

	<body>
		<div class="header">
			<div class="header_logo">
				<img src="images/logo.jpg" />
			</div>
			<a class="exit" target="_parent" href="logout.jsp"/></a>
			<div class="urse">
				今天是<%=new java.text.SimpleDateFormat("yyyy年MM月dd日 E").format(new java.util.Date()) %>
				欢迎您!
				<span style="color: #0BB1F6; padding-left: 10px;">
					<%=request.getSession().getAttribute("username") %>
				</span>
			</div>
		</div>
	</body>
</html>
