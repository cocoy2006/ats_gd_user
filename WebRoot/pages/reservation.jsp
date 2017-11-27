<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>中国电信手机测试云平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		
		<style type="text/css" media="screen">
			@import "../plugins/dataTables/css/demo_page.css";
			@import "../plugins/dataTables/css/demo_table.css";
			@import "stlye.css";
			table {border-collapse: collapse; text-align: center; width: 100%;}
			table td {border: 1px solid #D5D5D5; font-size: 12px; padding: 7px 5px;}
			table th {background-color: #EEE; border-right: 1px solid #D5D5D5; font-size: 13.5px;
				line-height: 120%; font-weight: bold; padding: 8px 5px;}
		</style>
		<script type="text/javascript">
			var username = "<%=request.getSession().getAttribute("username") %>";
			if(username == "null") window.location = "index.jsp";
		</script>		
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../plugins/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/reservation.js"></script>			
	</head>

	<body>
   		<div id="header">
			<img src="images/yun_01.jpg" />
		</div>
		
		<div id="nav">
			<div id="nav_content">
				<ul>
					<li><a href="index.jsp">主页</a></li>
					<li class="nav_line">|</li>
					<li><a href="info.jsp">个人信息</a></li>
					<li class="nav_line">|</li>
					<li><a href="pwdmodify.jsp">修改密码</a></li>
					<li class="nav_line">|</li>
					<li><a href="testcase.jsp">测试管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="log.jsp">日志管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="#">预约管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="logout.jsp">安全退出</a></li>
				</ul>
			</div>
		</div>
		
		<div id="main">
			<div id="main_lfet">
				<div class="rzgl">
					<div>
						<fieldset>
							<legend>&nbsp;预约管理&nbsp;</legend><br />
							<table id="reservationTable" width="100%" border="1" cellspacing="0" cellpadding="0" style="float: left">
								<thead>
									<tr>
										<th>编号</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>时长</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
							<!-- 
							<div id="reservationTips" class="tips gray" style="display: none;">
								暂时没有预约!
								<a href="index.jsp" style="padding-left: 30px; font-size: 12px; color: #FF0033;">立即预约</a> 
							</div>
							-->
						</fieldset>
					</div>
				</div>
			</div>
			<div id="main_right">
				<div id="main_right_login">		
					<input id="main_right_login_01" type="text" />
					<input id="main_right_login_02" type="password" />
					<input id="main_right_login_03" type="button" onClick="login()"/>
					<p id="main_right_login_04">
						<a href="pwdreset.jsp">密码找回</a>
					</p>
					<p id="main_right_login_05">
						<a href="reg.jsp">新用户注册</a>
					</p>
				</div>
				<div id="main_right_login_grxx" style="display: none;">
					<p id="main_right_login_06">欢迎您:<span style="color:#0066CC; padding:0 5px;"></span></p>
				</div>
			
				<div class="main_right_banner">
					<img src="images/yun_18.jpg" />
				</div>
			
				<div id="seach_mobile">
					<input class="seach_mobile_01" type="text" value="将于近期开放" disabled/>
					<input class="seach_mobile_02" type="button"/>
				</div>
			</div>
		</div>
		<p style="clear: both;"></p>
		<div id="footer">
			版权所有©2011, ICP证:粤B2-20040647
		</div>
	</body>
</html>
