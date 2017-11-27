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
		<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="../plugins/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="../js/facebox.js"></script>	
		<script type="text/javascript" src="../js/molab.js"></script>	
		<script type="text/javascript" src="js/devices.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var username = "<%=request.getSession().getAttribute("username") %>";
				if(username == "null") window.parent.location = "index.jsp";
				else init();
			});
		</script>
	</head>

	<body>
		<div class="right">
			<div class="right_main">
				<div class="right_main_tille">
					首页 &gt; 设备管理
				</div>
				<h4 style="font:14px Geneva, Arial, Helvetica, sans-serif; line-height:30px; border-bottom:1px solid #CCCCCC;">设备列表
					<input type="button" id="dispNewDeviceDiv" class="btn_01" value="新 增 设 备" style="float: right;"/>
				</h4><br />
				<form id="deviceForm" name="deviceForm" action="" method="post" style="padding: 0; margin: 0;">
					<table id="deviceTable" width="90%" border="1" cellspacing="0" cellpadding="0" style="float: left; text-align: center;">
						<thead>
							<tr>
								<th>所在服务器</th>
								<th>设备序列号</th>
								<th>厂商</th>
								<th>型号</th>
								<th>分辨率</th>
								<th>模拟器</th>
								<th>状态</th>
								<th><!-- <input type="checkbox" onChange="selectAllDevices(this);" disabled="disabled"/> --></th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
						<tfoot>
							<tr>
								<td colspan="8">
									<div style="float: left;">
										<span>
											<input type="button" class="btn_01" value="移 除 设 备" onClick="unactiveDevices()"/>
										</span>
										<span>&nbsp;</span>
										<span>
											<input type="button" class="btn_01" value="激 活 设 备" onClick="activeDevices()"/>
										</span>
										<span>&nbsp;</span>
										<span>
											<input type="button" class="btn_01" value="删 除 设 备" onClick="deleteDevices()"/>
										</span>
										<span>&nbsp;</span>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
					<div id="deviceTips" class="tips gray" style="display: none;">
						暂时没有设备!
					</div>					
				</form>
			</div>
		</div>
		<!-- 新增设备 -->
		<div id="newDeviceDiv" style="display: none;">
			<fieldset>
				<legend>&nbsp;设备信息&nbsp;</legend>
				<form id="newDeviceForm" name="newDeviceForm">
					<table>
						<tr>
	                        <td><label for="server">所在服务器 *</label></td>
	                        <td><input type="text" id="server" name="server" class="inputtext" value="http://"/><label></label></td>
	                    </tr>
						<tr>
	                        <td><label for="serialNumber">设备序列号 *</label></td>
	                        <td><input type="text" id="serialNumber" name="serialNumber" class="inputtext"/><label></label></td>
	                    </tr>
						<tr>
	                        <td><label for="width">分辨率 *</label></td>
	                        <td>
	                        	<input type="text" id="width" name="width" class="inputtext" style="width: 40px;"/>×
	                        	<input type="text" id="height" name="height" class="inputtext" style="width: 40px;"/>(宽×高)(像素)<label>&nbsp;</label>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td><label for="manufacturer">厂商 *</label></td>
	                        <td><input type="text" id="manufacturer" name="manufacturer" class="inputtext"/><label></label></td>
	                    </tr>
						<tr>
	                        <td><label for="model">型号 *</label></td>
	                        <td><input type="text" id="model" name="model" class="inputtext"/><label></label></td>
	                    </tr>
						<tr>
	                        <td><label for="isEmulator">模拟器</label></td>
	                        <td>是<input type="radio" name="isEmulator" value="1">&nbsp;否<input type="radio" name="isEmulator" value="0" checked><label></label></td>
	                    </tr>
	                    <tr>
	                        <td><label for="imei">IMEI</label></td>
	                        <td><input type="text" id="imei" name="imei" class="inputtext"/><label></label></td>
	                    </tr>
						<tr>
	                        <td><label for="os">操作系统 *</label></td>
	                        <td><input type="text" id="os" name="os" class="inputtext"/><label></label></td>
	                    </tr>
						<!-- 
						<tr style="background-color: gray;">
	                        <td><label for="preview">设备缩略图</label></td>
	                        <td><input type="file" id="preview" name="preview" disabled/><label></label></td>
	                    </tr>
	                     -->
	                    <tr>
	                    	<td colspan="2">
	                    		<input type="hidden" id="id" name="id">
	                    		<input type="submit" class="submit" value="确 定">
	                    		<input type="reset" class="submit" value="清 空">
	                    		带*标记的为必填项
	                    	</td>
	                    </tr>
	                    <tr>
	                    	<td colspan="2">
	                    		<label id="newDeviceTips"></label>
	                    	</td>
	                    </tr>
					</table>
				</form>
			</fieldset>
		</div>
		<iframe style="display: none;" name="restartIframe" id="restartIframe">
			
		</iframe>	
	</body>
</html>
