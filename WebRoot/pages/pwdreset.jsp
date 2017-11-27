<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>中国电信手机测试云平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="stlye.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/pwdreset.js"></script>			
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
			<form name="form">
               	<table>
                    <tr>
                        <td style="width: 100px;"><label for="username">用户名:</label></td>
                        <td style="width: 200px;"><input type="text" id="username" name="username" class="inputtext"/><label></label></td>
                        <td><label id="username_tips" class="tips"></label></td>
                    </tr>
                    <tr>
                        <td><label for="email">邮箱:</label></td>
                        <td><input type="text" id="email" name="email" class="inputtext"/><label></label></td>
                        <td><label id="email_tips" class="tips"></label></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="密 码 重 置" class="submit" style="width: 180px;"/></td>
                    </tr>
                    <tr>
                        <td colspan="3"><label id="tips"></label></td>
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
