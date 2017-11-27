$(document).ready(function() {
	dispWelcome(username);
	getUser(username);
	loadTestcases(username);
});

var g_username;
function getUser(username) {
	g_username = username;
	$.ajax({
		url: "../servlet/GetUser",
		type: "POST",
		data: "username=" + username,
		dataType: "JSON",
		success: function(data) {
			data = eval("(" + data + ")");
			$("#u_id").val(data.id);
			$("#u_n").val(data.nickname);
			$("#u_email").val(data.email);
			$("#u_phone_no").val(data.phone_no);
		}
	});
};
var g_projects;
var g_testcases;
function loadTestcases(username) {
	$("#testcasesTree").dynatree({
		clickFolderMode: 1, // 1:activate, 2:expand, 3:active and expand
		initAjax: {
			url: "../servlet/testcases/LoadTestcases",
			data: "username=" + g_username
		},
		onDblClick: function(node, event) {
			if(node.data.isFolder) {
				jQuery.facebox({div: "#projectsDiv"});
				fillManagementDiv(true, node.data);
			} else {
				jQuery.facebox({div: "#testcasesDiv"});
				fillManagementDiv(false, node.data);
			}
      	},
      	onPostInit: function(isReloading, isError) {
      		g_projects = new Array();
      		var children = this.getRoot().getChildren();
      		if(!children) {
      			$("#testcasesTree").addClass("tips gray").html("暂时没有项目!");
      		} else {
      			var select = "<option value=''>请选择</option>";
	  			for(var i = 1; i < children.length; i++) {
	      			g_projects[i] = children[i].data.title;
	      			select += "<option value='" + g_projects[i] + "'>" + g_projects[i] + "</option>";
	      		}
	  			$("#uploadBTProject").html(select);
      		}
      	}
	});
}
function fillManagementDiv(flag, data) {
	var content;
	if(flag == true) { // is project
		content = 
			"当前项目名称:<font class='green'>" + data.key + "</font>&nbsp;&nbsp;"
			+ "<input type='button' class='submit' value='删除' onClick='deleteProject1(\"" + data.key + "\")'/><br><br>"
			+ "<input type='button' class='submit' value='重命名为' onClick='renameProject1(\"" + data.key + "\")'/>"
			+ "<input type='text' id='renameProjectName' class='inputtext' value='" + data.key + "'/>";
		$("#facebox form[name='projectsForm']").html(content);
	} else { // is testcase
		var info = data.key.split("/");
		content = 
			"当前案例名称:<font class='green'>" + info[1] + "</font>"
			+ "<input type='button' class='submit' value='删除' onClick='deleteTestcase(\"" + info[0] + "\", \"" + info[1] + "\")'/><br><br>"
			+ "<input type='button' class='submit' value='重命名为' onClick='renameTestcase(\"" + info[0] + "\", \"" + info[1] + "\")'/>"
			+ "<input type='text' id='renameTestcaseName' class='inputtext' value='" + info[1] + "'/><br><br>" 
			+ "所属项目:<font class='green'>" + info[0] + "</font><br><input type='button' class='submit' value='移动至' onClick='moveTestcase(\"" + info[0] + "\", this, \"" + info[1] + "\")'/>" + fillSelect();
		$("#facebox form[name='testcasesForm']").html(content);
	}
}
function fillSelect() {
	var select = "<select class='inputtext'><option value=''>请选择</option>";
	for(var i = 1; i < g_projects.length; i++) {
		var project = g_projects[i];
		select += "<option value='" + project + "'>" + project + "</option>";
	}
	select += "</select>";
	return select;
}
/**
 * 0：正常
 * 1：文件夹已经存在
 * 2：文件夹名称不符合条件*/
function newProject() {
	var project = $("#facebox input[id='newProjectName']").val();
	if (!isLegel(project)) {
		$("#testcasesTips").addClass("red").html("不能包含/\\*:?\"<>|");
		jQuery(document).trigger('close.facebox');
		return;
	}
	if(hasChinese(project)) {
		$("#testcasesTips").addClass("red").html("暂时不支持中文!");
		jQuery(document).trigger('close.facebox');
		return;
	}
	for(i in g_projects) {
		if(g_projects[i] == project) {
			$("#testcasesTips").html("<font class='tips red'>" + project + "已经存在!</font>");
			jQuery(document).trigger('close.facebox');
			return;
		}
	}
	$.ajax({
		url: "../servlet/testcases/NewProject",
		type: "POST",
		data: "username=" + g_username + "&project=" + project,
		success: function(data) {
			var tips = "";
			switch(data) {
				case "0":
					$("#testcasesTree").dynatree("getTree").reload();
					tips = "<font class='tips green'>" + project + "创建成功!</font>";
					break;
				case "1":
					tips = "<font class='tips red'>" + project + "已经存在!</font>";
					break;
				case "2":
					tips = "<font class='tips red'>" + project + "不符合命名条件!</font>";
					break;
			}
			$("#testcasesTips").html(tips);
			jQuery(document).trigger('close.facebox');
		}
	});
}
function isLegel(name) {
	var pattern = /^[^\/\\\*\:\?\"\<\>\|]{0,}$/g; // 判断是否含有特殊字符	
	return pattern.exec(name);
}
function hasChinese(name) {
	var pattern = /.*[\u4e00-\u9fa5]+.*$/; // 判断是否有中文
	return pattern.test(name)
}
function deleteProject1(project) {
	if(!confirm("删除项目将同时删除属于该项目的所有测试案例，确定继续?")) return;
	$.ajax({
		url: "../servlet/testcases/DeleteProject",
		type: "POST",
		data: "username=" + g_username + "&project=" + project,
		success: function(data) {
			var tips = "";
			switch(data) {
				case "0":
					$("#testcasesTree").dynatree("getTree").reload();
					tips = "<font class='tips green'>" + project + "删除成功!</font>";
					break;
				case "1":
					tips = "<font class='tips red'>" + project + "删除失败!</font>";
					break;
			}
			$("#testcasesTips").html(tips);
			jQuery(document).trigger('close.facebox');
		}
	});
}
function renameProject1(project) {
	var pro2 = $("#facebox input[id='renameProjectName']").val();
	if(project == pro2 || pro2 == "") return;
	if(isExist(pro2)) {
		jQuery(document).trigger('close.facebox');
		$("#testcasesTips").html("<font class='tips red'>" + pro2 + "已经存在!</font>"); 
		return;
	}
	$.ajax({
		url: "../servlet/testcases/RenameProject",
		type: "POST",
		data: "username=" + g_username + "&project1=" + project + "&project2=" + pro2,
		success: function(data) {
			var tips = "";
			switch(data) {
				case "0":
					$("#testcasesTree").dynatree("getTree").reload();
					tips = "<font class='tips green'>" + pro2 + "重命名成功!</font>";
					break;
				case "2":
					tips = "<font class='tips red'>" + pro2 + "不符合命名条件!</font>";
					break;
				default:
					break;
			}
			$("#testcasesTips").html(tips);
			jQuery(document).trigger('close.facebox');
		}
	});
}
function deleteTestcase(project, testcase) {
	if(!confirm("删除案例将无法恢复，确定继续?")) return;
	$.ajax({
		url: "../servlet/testcases/DeleteTestcase",
		type: "POST",
		data: "username=" + g_username + "&project=" + project + "&testcase=" + testcase,
		success: function(data) {
			var tips = "";
			switch(data) {
				case "0":
					$("#testcasesTree").dynatree("getTree").reload();
					tips = "<font class='tips green'>" + testcase + "删除成功!</font>";
					break;
				case "1":
					tips = "<font class='tips red'>" + testcase + "删除失败!</font>";
					break;
			}
			$("#testcasesTips").html(tips);
			jQuery(document).trigger('close.facebox');
		}
	});
}
function renameTestcase(project, testcase1) {
	var testcase2 = $("#facebox input[id='renameTestcaseName']").val();
	if (testcase2.match(/\.xml$/) == null) {
		testcase2 += ".xml";
	}
	if(testcase1 == testcase2 || testcase2 == "") return;
	$.ajax({
		url: "../servlet/testcases/RenameTestcase",
		type: "POST",
		data: "username=" + g_username + "&project=" + project + "&testcase1=" + testcase1 + "&testcase2=" + testcase2,
		success: function(data) {
			var tips = "";
			switch(data) {
				case "0":
					$("#testcasesTree").dynatree("getTree").reload();
					tips = "<font class='tips green'>" + testcase2 + "重命名成功!</font>";
					break;
				case "2":
					tips = "<font class='tips red'>" + testcase2 + "不符合命名条件!</font>";
					break;
				default:
					break;
			}
			$("#testcasesTips").html(tips);
			jQuery(document).trigger('close.facebox');
		}
	});
}
function moveTestcase(project1, obj, testcase) {
	var project2 = $(obj).next().val();
	if(project1 == project2 || project2 == "") return;
	$.ajax({
		url: "../servlet/testcases/MoveTestcase",
		type: "POST",
		data: "username=" + g_username + "&project1=" + project1 + "&project2=" + project2 + "&testcase=" + testcase,
		success: function(data) {
			var tips = "";
			switch(data) {
				case "0":
					$("#testcasesTree").dynatree("getTree").reload();
					tips = "<font class='tips green'>" + testcase + "移动成功!</font>";
					break;
				case "1":
					tips = "<font class='tips red'>" + testcase + "移动失败!</font>";
					break;
			}
			$("#testcasesTips").html(tips);
			jQuery(document).trigger('close.facebox');
		}
	});
}
function addProject(project) {
	return "<label><img src='../images/book.jpg'/>" + project + "</label>";
}
function addTestcases(project, testcases) {
	var ul = "<ul>";
	for(var i = 0; i < testcases.length; i++) {
		ul += addTestcase(project, testcases[i].name);
	}
	ul += "</ul>";
	return ul;
}
function addTestcase(project, name) {
	li = 
		"<li>" +
		"	<img src='../../image/testcases/testcase.jpg'/>" + name + 
		"</li>" +
		"<li style='display: none;'>" + project + "/" + name + "</li>";
	return li;
}

function isExist(project) {
	for(i in g_projects) {
		if(project == g_projects[i]) return true;
	}
	return false;
}

function fillProjects(select) {
//	for(var i in g_projects) {
	var sel;
	for(var i = 1; i < g_projects.length; i++) {
		var project = g_projects[i];
		sel += "<option value='" + project + "'>" + project + "</option>";
	}
	$(select).html(sel);
}

function uploadTestcases() {
	var xml = $("#xml").val();	
	if(!xml) {
		$("#uploadTips").addClass("tips").html("请选择文件!");
		return;
	}
	if (xml.match(/\.xml$/) == null) {
		$("#uploadTips").addClass("tips").html("文件格式错误!");
		return;
	}
	var project = $("#uploadBTProject").val();
	if(!project) {
		$("#uploadTips").addClass("tips").html("请选择测试项目,或创建新的测试项目!");
		return;
	}
	$("#uploadTips").addClass("tips").html("正在上传xml文件...");
	var url = "../servlet/testcases/UploadTestcases?username=" + g_username + "&project=" + project;
	$("form[name='uploadForm']").attr("action", url).submit();
}
function uploadTestcaseResult(data) {
	var tips = "";
	switch(data) {
		case 0:
			$("#testcasesTree").dynatree("getTree").reload();
			tips = "<font class='green'>上传成功!</font>";
			break;
		case 1:
			tips = "<font class='red'>上传失败，请稍后重试!</font>";
			break;
	}
	$("#uploadTips").addClass("tips").html(tips);
}
function autotest() {
		alert($("#historyForm input:checked").val());
}