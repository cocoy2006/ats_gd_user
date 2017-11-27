package android.testing.system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.project.ProjectHandler;
import android.testing.system.user.UserHandler;

public class ActivateUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.valueOf(request.getParameter("id"));
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		boolean result = new UserHandler().activateUser(id, username, email);
		
		if(result && new ProjectHandler().newProject(username, "Test") == 0) {
			request.getSession().setAttribute("username", username);
		}
		response.sendRedirect("../pages/index.jsp");
	}
}
