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
		</style>
		<script type="text/javascript">
			var username = "<%=request.getSession().getAttribute("username") %>";
		</script>		
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/facebox.js"></script>
		<script type="text/javascript" src="../plugins/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="../plugins/datePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../plugins/jquery.ui/js/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/index.js"></script>			
	</head>

	<body>
   		<div id="header">
			<img src="images/yun_01.jpg" />
		</div>
		
		<div id="nav">
			<div id="nav_content">
				<ul>
					<li><a href="#">主页</a></li>
					<li class="nav_line">|</li>
					<li><a href="#" onClick="forward(username, 'info.jsp')">个人信息</a></li>
					<li class="nav_line">|</li>
					<li><a href="#" onClick="forward(username, 'pwdmodify.jsp');">修改密码</a></li>
					<li class="nav_line">|</li>
					<li><a href="#" onClick="forward(username, 'testcase.jsp')">测试管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="#" onClick="forward(username, 'log.jsp')">日志管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="#" onClick="forward(username, 'reservation.jsp')">预约管理</a></li>
					<li class="nav_line">|</li>
					<li><a href="#" onClick="forward(username, 'logout.jsp')">安全退出</a></li>
				</ul>
			</div>
		</div>
		
		<div id="main">
			<div id="main_lfet">
				<div id="main_lfet_02"></div>
			
				<div class="main_lfet_content_01">
					<div class="main_lfet_content_01_tille">
						<span>温馨提示：操作前请先登陆</span>
					</div>
					<div class="main_lfet_content_01_text">
						<div class="main_lfet_content_01_text_01" style="height: 500px; overflow: auto;">
							<table id="deviceTable" width="90%" border="1" cellspacing="0" cellpadding="0" style="float: left; text-align: center;">
								<thead>
									<tr>
										<th>缩略图</th>
										<th>所属服务器</th>
										<th>序列号</th>
										<th>厂商</th>				
										<th>型号</th>
										<th>分辨率</th>
										<th>模拟器</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="devices">
									
								</tbody>
							</table>
							<div id="deviceTips" class="tips gray" style="display: none;">暂时没有设备!</div>
						</div>
					</div>
				</div>
			</div>
			<div id="main_right">
				<div id="main_right_login">		
					<input id="main_right_login_01" type="text" />
					<input id="main_right_login_02" type="password" />
					<input id="main_right_login_03" type="button" onClick="login(1)"/>
					<p id="main_right_login_04">
						<a href="pwdreset.jsp">密码找回</a>
					</p>
					<p id="main_right_login_05">
						<a href="reg.jsp">新用户注册</a>
					</p>
				</div>
				<div id="main_right_login_grxx" style="display: none;">
					<p id="main_right_login_06" style="left: 0;">欢迎您:
						<span style="color:#0066CC; padding:0 5px;"></span>
						<label id="lefttime"></label>
						<input type="button" class="submit" value="申请更多机时" onClick="javascript:jQuery.facebox({div:'#reservDiv'})"/>
					</p>
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
		<!-- 申请机时DIV -->
		<div id="reservDiv" style="display: none;">
			<fieldset>
				<legend>&nbsp;申请机时&nbsp;</legend>
				<div>
					<form name="reservForm">
						<label for="reservTime">申请</label>
						<select id="reservTime" name="reservTime" class="inputtext" onChange="javascript:$('#hiddenReservTime').val(this.value)" style="width: 80px;">
							<option value="10" >10</option>
							<option value="30">30</option>
							<option value="60">60</option>
							<option value="120">120</option>
						</select>
						<label for="reservTime"> 分钟</label>
						<input type="button" class="submit" value="申  请" onClick="addLefttime()"/>
						<input type="hidden" id="hiddenReservTime" value="10"/>
					</form>
					<span id="reservTips" style="margin-left: 20px; "></span>
				</div>
			</fieldset>		
		</div>
		<!-- 预约设备DIV -->
		<div id="reserveDiv" style="display: none;">
			<fieldset>
				<legend>&nbsp;预约设备&nbsp;</legend>
				<div>
					<form name="reserveForm">
						<label for="startTime">开始时间</label>
						<input type="text" id="startTime" name="startTime" style="width: 200px;"
							onfocus="WdatePicker({})" class="Wdate"/>
						
						<br/><br/>
						<label for="endTime">结束时间</label>
						<input type="text" id="endTime" name="endTime" style="width: 200px;" 
							onfocus="WdatePicker({})" class="Wdate"/>
						
						<br/><br/>
						<input type="button" class="submit" value="预  约" onClick="reserve()"/>
						<br/>
						<input type="hidden" id="server" name="server"/>
						<input type="hidden" id="serialNumber" name="serialNumber"/>
					</form>
					<span id="reserveTips" style="margin-left: 20px; ">提示:预约时间不少于10分钟</span>
				</div>
			</fieldset>		
		</div>		
	</body>
</html>
