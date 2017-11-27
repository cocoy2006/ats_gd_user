package android.testing.system.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.user.UserHandler;

public class UpdateStates extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("ids");
		int state = Integer.parseInt(request.getParameter("state"));
		
		int result = new UserHandler().updateStates(ids, state);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}

}
