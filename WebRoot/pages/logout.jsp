<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="android.testing.system.log.LogHandler"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
if(request.getSession().getAttribute("username") != null) {
	String username = request.getSession().getAttribute("username").toString();
	new LogHandler().newLog(username, null, null, "LOGOUT", System.currentTimeMillis());
  	
	request.getSession().removeAttribute("username");
}
%>
<script type="text/javascript">
	window.location = "index.jsp";
</script>