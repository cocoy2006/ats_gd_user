package android.testing.system.servlet.testcases;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.testcase.TestcaseHandler;

public class DeleteTestcase extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//		String testcases = Util.getInstance().getProperty("testcases");
		String username = request.getParameter("username");
		String project = request.getParameter("project");
		String testcase = request.getParameter("testcase");
		
//		int result = new TestcasesHandler(username).deleteTestcase(project, testcase);
		int result = new TestcaseHandler().deleteTestcase(username, project, testcase);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}
}
