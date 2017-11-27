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
		<script type="text/javascript" src="../js/jquery/jquery-ui.custom.min.js"></script>
		<script type="text/javascript" src="../js/molab.js"></script>
		<script type="text/javascript" src="js/testcase.js"></script>
		
		<link rel="stylesheet" type="text/css" href="../css/facebox.css">
		<script type="text/javascript" src="../js/facebox.js"></script>
		
		<link rel="stylesheet" type="text/css" href="../plugins/dynaTree/skin-vista/ui.dynatree.css">
		<script type="text/javascript" src="../plugins/dynaTree/jquery.dynatree.min.js"></script>			
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
					<li><a href="#">测试管理</a></li>
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
				<div class="rzgl">
					<div>
						<fieldset>
							<legend>&nbsp;测试管理&nbsp;</legend><br />
							<!-- 测试项目及案例树 -->
							<div>
								<div style="padding:0 0 10px 10px;">
									<input type="button" class="submit" value="创建项目" onClick="javascript:jQuery.facebox({div: '#newProjectDiv'});">
									<span style="padding-left:30px; color:#666666;">提示:
										<span id="testcasesTips"></span>
									</span>
								</div>
								<h4 style="padding-left: 10px;">已经创建项目
									<span style="font-weight:100; color:#666666; padding-left: 10px;">提示：双击测试项目和测试案例进行管理!</span>
								</h4>
								<div style="height: 220px; margin: 0 0 0 10px; border:1px solid #CCCCCC; overflow: auto;">
									<form id="historyForm" name="historyForm" style="margin: 0; padding: 0;">
										<div id="testcasesTree"></div>
									</form>
								</div>
							</div>
							<div style="padding-top:10px;">
								<form name="uploadForm" enctype="multipart/form-data" target="hiddenUploadFrame" method="post">
									<h4 style="background:url(images/24-book-green-open.png) left center no-repeat; padding-left:30px; ">上传本地测试案例</h4>
									<p><input id="xml" name="xml" type="file"></p><br/>
									<p>选择测试项目:
										<select id="uploadBTProject" class="inputtext">
											<option value="">请选择</option>
										</select>
									</p><br/>
									<input type="button" class="submit" value=" 上 传 " onClick="uploadTestcases()">
									<span style="color:#666666; padding-left:30px;">提示：新案例将覆盖同工程下的同名案例!</span><br/><br/>
									<div id="uploadTips" class="red"></div>
								</form>
								<iframe name="hiddenUploadFrame" id="hiddenUploadFrame" style="display: none;"></iframe>
							</div>
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
		
        <!-- 创建测试案例项目 -->
		<div id="newProjectDiv" style="display: none;">
			<fieldset>
				<legend>&nbsp;创建测试案例项目&nbsp;</legend>
				<div>
					<form name="newProjectForm">
						<label for="newProjectName">项目名称</label>
						<input type="text" id="newProjectName" name="newProjectName" class="inputtext"/>
						<input type="button" class="submit" value=" 创 建 " onClick="newProject()"/>
					</form>
				</div>
			</fieldset>
		</div>		
		<!-- 测试项目管理 -->
		<div id="projectsDiv" style="display: none;">
			<fieldset>
				<legend>&nbsp;测试项目管理&nbsp;</legend>
				<div>
					<form name="projectsForm">
					
					</form>
				</div>
			</fieldset>
		</div>
		<!-- 测试案例管理 -->
		<div id="testcasesDiv" style="display: none;">
			<fieldset>
				<legend>&nbsp;测试案例管理&nbsp;</legend>
				<div>
					<form name="testcasesForm">
					
					</form>
				</div>
			</fieldset>
		</div>
	</body>
</html>
