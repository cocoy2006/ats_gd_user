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
			.right_main{padding: 10px;}
		</style>
		
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../plugins/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/logview.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var username = "<%=request.getSession().getAttribute("username") %>";
				if(username == "null") window.location = "index.jsp";
				var server = "<%=request.getParameter("server") %>";
				var serialNumber = "<%=request.getParameter("serialNumber") %>";
				getAllLogs(server, serialNumber);
			});
		</script>
	</head>

	<body>
		<div class="right">
			<div class="right_main">
				<h4 style="font:14px Geneva, Arial, Helvetica, sans-serif; line-height:30px; border-bottom:1px solid #CCCCCC;">日志列表</h4><br />
				<table id="logTable" width="100%" border="1" cellspacing="0" cellpadding="0" style="float: left; text-align: center;">
					<thead>
						<tr>
							<th>用户</th>
							<th>操作</th>
							<th>时间</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
				<div id="logTips" class="tips gray" style="display: none;">
					暂时没有日志记录!
				</div>	
			</div>
		</div>
	</body>
</html>
