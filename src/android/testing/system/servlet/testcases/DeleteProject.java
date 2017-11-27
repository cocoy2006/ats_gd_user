package android.testing.system.servlet.testcases;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.project.ProjectHandler;

public class DeleteProject extends HttpServlet {

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

//		int result = new TestcasesHandler(username).deleteProject(project);
		int result = new ProjectHandler().deleteProject(username, project);

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}
}
