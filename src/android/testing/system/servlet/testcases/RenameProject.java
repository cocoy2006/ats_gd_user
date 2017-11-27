package android.testing.system.servlet.testcases;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.project.ProjectHandler;

public class RenameProject extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//		String testcases = Util.getInstance().getProperty("testcases");
		String username = request.getParameter("username");
		String project1 = request.getParameter("project1");
		String project2 = request.getParameter("project2");

//		int result = new TestcasesHandler(username).renameProject(project1, project2);
		int result = new ProjectHandler().renameProject(username, project1, project2);

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}
}