package android.testing.system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.user.UserHandler;

public class AddLefttime extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		int time = Integer.parseInt(request.getParameter("time"));
		
		boolean success = new UserHandler().addLefttime(username, time);
		
		response.getWriter().print(success);
	}

}
