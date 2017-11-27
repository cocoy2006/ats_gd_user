package android.testing.system.servlet.testcases;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.testcase.TestcaseHandler;

public class RenameTestcase extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String project = request.getParameter("project");
		String testcase1 = request.getParameter("testcase1");
		String testcase2 = request.getParameter("testcase2");

//		int result = new TestcasesHandler(username).renameTestcase(project, testcase1, testcase2);
		int result = new TestcaseHandler().renameTestcase(username, project, testcase1, testcase2);

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}
}
