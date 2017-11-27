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
			.btn_01{width:119px; height:29px; background:url(images/btn_bg2.jpg) no-repeat; border:none; cursor:pointer;}
			.right_main{padding: 10px;}
			.right_main_tille{height:28px; padding-left:20px;font:12px Arial, Helvetica, sans-serif; color:#666666; line-height:28px; background:url(images/it_img_11.jpg) 1px center no-repeat;}
		</style>
		
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../plugins/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/users.js"></script>
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
					首页 > 用户管理
				</div>
				<h4 style="font:14px Geneva, Arial, Helvetica, sans-serif; line-height:30px; border-bottom:1px solid #CCCCCC;">用户列表</h4><br />
				<form id="userForm" name="userForm" action="" method="post">
					<table id="userTable" width="90%" border="1" cellspacing="0" cellpadding="0" style="float: left; text-align: center;">
						<thead>
							<tr>
								<th>编号</th>
								<th>用户名</th>
								<th>昵称</th>
								<th>密码</th>
								<th>邮箱</th>
								<th>手机号码</th>
								<th>机时/秒</th>
								<th>当前状态</th>
								<th>角色</th>
								<th><!-- <input type="checkbox" onChange="selectAllUsers(this);" disabled="disabled"/> --></th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
						<tfoot>
							<tr>
								<td colspan="10">
									<div style="float: left;">
										<span>
											<input type="button" class="btn_01" value="注 销 用 户" onClick="unactivateUsers()"/>
										</span>
										<span>&nbsp;</span>
										<span>
											<input type="button" class="btn_01" value="激 活 用 户" onClick="activateUsers()"/>
										</span>
										<span>&nbsp;</span>
										<span>
											<input type="button" class="btn_01" value="删 除 用 户" onClick="deleteUsers()"/>
										</span>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</form>	
			</div>
		</div>
	</body>
</html>
