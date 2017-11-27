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
			
			.left {
				width: 170px;
			}
			
			.left_main {
				padding: 5px;
				margin: 0 auto;
				text-align: center;
				padding-top: 0;
			}
			
			.left_list {
				width: 159px;
				background: url(images/it_19.jpg) repeat-y;
				text-align: left;
				margin-bottom: 2px;
			}
			
			.left_list_tille {
				height: 30px;
				font: 14px Arial, Helvetica, sans-serif;
				background: url(images/list_tille.jpg) no-repeat;
				line-height: 30px;
				padding-left: 35px;
				position: relative;
			}
			
			.tille_icon {
				position: absolute;
				left: 10px;
				top: 5px;
			}
			
			.left_list_content {
				font: 12px Arial, Helvetica, sans-serif;
			}
			
			.left_list_content ul {
				list-style-type: none;
				padding: 5px 5px;
			}
			
			.left_list_content ul li {
				padding-left: 30px;
				line-height: 26px;
				display: block;
				border-bottom: 1px solid #EBE5CB;
			}
			
			.list_text_01 {
				background: url(images/folder.png) 5px center no-repeat;
			}
			
			.left_list_content ul li a:link,.left_list_content ul li a:visited {
				color: #000;
				text-decoration: none;
			}
			
			.left_list_content ul li a:hover {
				color: #F1381A;
				text-decoration: underline;
			}
			
			.left_list_botom {
				height: 9px;
				background: url(images/it_21.jpg) no-repeat;
			}
		</style>
	</head>

	<body>
		<div class="left">
			<div class="left_main">
				<div class="left_list">
					<div class="left_list_tille">
						<span class="tille_icon"><img src="images/folder_table.png" />
						</span>操作菜单
					</div>
					<div class="left_list_content">
						<ul>
							<li class="list_text_01">
								<a href="right.jsp" target="mainFrame">首页</a>
							</li>
							<li class="list_text_01">
								<a href="users.jsp" target="mainFrame">用户管理</a>
							</li>
							<li class="list_text_01">
								<a href="devices.jsp" target="mainFrame">设备管理</a>
							</li>
							<li class="list_text_01">
								<a href="logquery.jsp" target="mainFrame">日志查询</a>
							</li>
							<li class="list_text_01">
								<a href="pwdmodify.jsp" target="mainFrame">修改密码</a>
							</li>
						</ul>
					</div>
					<div class="left_list_botom"></div>
				</div>
			</div>
		</div>
	</body>
</html>
