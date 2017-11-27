<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>中国电信手机测试云平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="stlye.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			var username = "<%=request.getSession().getAttribute("username") %>";
			if(username == "null") window.location = "index.jsp";
		</script>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/info.js"></script>
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
					<li><a href="#">个人信息</a></li>
					<li class="nav_line">|</li>
					<li><a href="pwdmodify.jsp">修改密码</a></li>
					<li class="nav_line">|</li>
					<li><a href="testcase.jsp">测试管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="log.jsp">日志管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="reservation.jsp">预约管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="logout.jsp">安全退出</a></li>
				</ul>
			</div>
		</div>
		
		<div id="main">
			<div id="main_lfet">
				<fieldset>
					<legend>&nbsp;个人信息&nbsp;</legend><br />			
					<form name="updateForm" method="POST">
						<input type="hidden" id="u_id" name="u_id" value=""/>
						<table>
	                        <tr>
	                        	<td style="width: 100px;"><label for="u_n">昵称:</label></td>
	                            <td style="width: 280px;"><input type="text" id="u_n" name="u_n" class="inputtext" value="" style="width: 260px;"/><label></label></td>
	                            <td><label id="u_n_tips" class="tips"></label></td>
	                        </tr>
	                        <tr>
	                            <td><label for="u_email">邮箱:</label></td>
	                            <td><input type="text" id="u_email" name="u_email" class="inputtext" value="" style="width: 260px;"/><label></label></td>
	                            <td><label id="u_email_tips" class="tips"></label></td>
	                        </tr>
	                        <tr>
	                            <td><label for="u_phone_no">手机号码:</label></td>
	                            <td><input type="text" id="u_phone_no" name="u_phone_no" class="inputtext" value="" style="width: 260px;"/><label></label></td>
	                            <td><label id="u_phone_no_tips" class="tips"></label></td>
	                        </tr>
	                        <tr>
	                        	<td><label>&nbsp;</label></td>
	                            <td><input type="submit" value="确 定 修 改" class="submit" style="width: 260px;"/></td>
	                            <td><label>&nbsp;</label></td>
	                        </tr>
	                    </table>
					</form>
					<span id="updateTips" style="margin-left: 20px;"></span>
				</fieldset>
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
