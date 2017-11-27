<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>中国电信手机测试云平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="stlye.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			td { border-style: none !important; padding: 1px !important; }
		</style>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="js/reg.js"></script>
	</head>

	<body>
   		<div id="header">
			<img src="images/yun_01.jpg" />
		</div>
		
		<div id="nav">
			<div id="nav_content">
				<ul>
					<li><a href="index.jsp">返回主页</a></li>
				</ul>
			</div>
		</div>
		
		<div id="main">
			<form name="reg_form" method="POST" action="../servlet/Reg">
                <table>
                    <tr>
                        <td><label for="r_u">用户名:</label></td>
                        <td><input type="text" id="r_u" name="r_u" class="inputtext"/><label></label></td>
                        <td><label id="r_u_tips" class="tips">占位符</label></td>
                    </tr>
                    <tr>
                    	<td><label for="r_n">昵称:</label></td>
                        <td><input type="text" id="r_n" name="r_n" class="inputtext"/><label></label></td>
                        <td><label id="r_n_tips" class="tips">占位符</label></td>
                    </tr>
                    <tr>
                        <td><label for="r_p">创建密码:</label></td>
                        <td><input type="password" id="r_p" name="r_p" class="inputtext"/><label></label></td>
                        <td><label id="r_p_tips" class="tips">占位符</label></td>
                    </tr>
                    <tr>
                        <td><label for="r_p_ag">确认密码:</label></td>
                        <td><input type="password" id="r_p_ag" name="r_p_ag" class="inputtext"/><label></label></td>
                        <td><label id="r_p_ag_tips" class="tips">占位符</label></td>
                    </tr>
                    <tr>
                        <td><label for="r_email">注册邮箱:</label></td>
                        <td><input type="text" id="r_email" name="r_email" class="inputtext"/><label></label></td>
                        <td><label id="r_email_tips" class="tips">占位符</label></td>
                    </tr>
                    <tr>
                        <td><label for="r_phone_no">手机号码:</label></td>
                        <td><input type="text" id="r_phone_no" name="r_phone_no" class="inputtext"/><label></label></td>
                        <td><label id="r_phone_no_tips" class="tips">占位符</label></td>
                    </tr>
                    <tr>
                        <td><label>&nbsp;</label></td>
                        <td><input type="submit" id="r_submit" value="免 费 注 册" class="submit"/></td>
                        <td><label id="reg_tips"></label></td>
                    </tr>
                </table>
            </form>
		</div>
		<p style="clear: both;"></p>
		<div id="footer">
			版权所有©2011, ICP证:粤B2-20040647
		</div>
	</body>
</html>
