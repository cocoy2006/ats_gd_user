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
			@import "../css/facebox.css";
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
		<script type="text/javascript" src="../datePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../js/facebox.js"></script>	
		<script type="text/javascript" src="../js/molab.js"></script>
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
					首页 > 日志查询
				</div>
				<h4 style="font:14px Geneva, Arial, Helvetica, sans-serif; line-height:30px; border-bottom:1px solid #CCCCCC;">日志查询</h4><br />
				<div id="container" style="height: 530px;">
					<div>
						<label>请输入查询条件:</label>
						<table>
							<tr>
								<td style="text-align: right;">用户名</td>
								<td>
									<input type="text" id="username" class="inputtext"/>
								</td>
								<td style="text-align: right;">开始时间</td>
								<td>
									<input type="text" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy,MM,dd HH:mm:ss', readOnly:true, minDate:'1900-01-01 00:00:00', maxDate:'2099-12-31 23:59:59'})" class="Wdate" style="width: 200px;"/>
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">设备名</td>
								<td>
									<input type="text" id="device" class="inputtext"/>&nbsp;<label id="deviceTips" class="tips red" style="display: none;">设备名不完整，请重新输入!</label>
									<p>请输入完整的设备名，如 &lt;设备所属服务器&gt;&lt;设备序列号&gt;</p>
									<p>示例：http://127.0.0.1:8080/ATS/emulator-5554</p>
								</td>
								<td style="text-align: right;">结束时间</td>
								<td>
									<input type="text" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy,MM,dd HH:mm:ss', readOnly:true, minDate:'1900-01-01 00:00:00', maxDate:'2099-12-31 23:59:59'})" class="Wdate" style="width: 200px;"/>
								</td>
							</tr>
							<tr>
								<td colspan="4"><input type="button" class="btn_01" value="开 始 查 询" onClick="query()"/></td>
							</tr>
						</table>
					</div>
					<br/><label>查询结果：</label>
					<div id="queryResultDiv">
						<table id="queryResultTable" width="100%" border="1" cellspacing="0" cellpadding="0" style="float: left; text-align: center;">
							<thead>
								<tr>
									<th>编号</th>
									<th>用户</th>
									<th>操作</th>
									<th>设备所属服务器</th>
									<th>设备序列号</th>
									<th style="width: 180px;">时间</th>
								</tr>
							</thead>
							<tbody style="aa">
								
							</tbody>
						</table>
					</div>
					<div id="queryResultTips" style="display: none; text-align: center; width: 30%; color: red;"></div>
				</div>	
			</div>
		</div>
	</body>
</html>
