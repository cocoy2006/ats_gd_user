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
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/pwdmodify.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var username = "<%=request.getSession().getAttribute("username") %>";
				if(username == "null") window.parent.location = "index.jsp";
				else getUser(username);
			});
		</script>		
	</head>

	<body>
		<div class="right">
			<div class="right_main">
				<div class="right_main_tille">
					首页 > 修改密码
				</div>
				<h4 style="font:14px Geneva, Arial, Helvetica, sans-serif; line-height:30px; border-bottom:1px solid #CCCCCC;">修改密码</h4><br />
				<form name="updateForm" method="POST">
					<input type="hidden" id="u_id" name="u_id" value=""/>
					<table>
                        <tr>
                            <td style="width: 100px;"><label for="u_p">新的密码:</label></td>
                            <td style="width: 280px;"><input type="password" id="u_p" name="u_p" class="inputtext" value="" style="width: 260px;"/><label></label></td>
                            <td><label id="u_p_tips" class="tips"></label></td>
                        </tr>
                        <tr>
                            <td><label for="u_p_ag">确认新的密码:</label></td>
                            <td><input type="password" id="u_p_ag" name="u_p_ag" class="inputtext" value="" style="width: 260px;"/><label></label></td>
                            <td><label id="u_p_ag_tips" class="tips"></label></td>
                        </tr>
                        <tr>
                        	<td><label>&nbsp;</label></td>
                            <td><input type="submit" value="确 定 修 改" class="submit" style="width: 260px;"/></td>
                            <td><label>&nbsp;</label></td>
                        </tr>
                    </table>
				</form>
				<span id="updateTips" style="margin-left: 20px;"></span>
			</div>
		</div>
	</body>
</html>
