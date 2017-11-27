package android.testing.system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.log.LogHandler;
import android.testing.system.user.User;
import android.testing.system.user.UserHandler;

public class Login extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("l_u");
		String password = request.getParameter("l_p");
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		Object obj = new UserHandler().checkUser(user);
		String result = "{'exist':'%s', 'state':'%s','role':'%s'}";
		if(obj == null) {
			result = String.format(result, "false", "", "");
		} else {
			Object[] objs = (Object[]) obj;
			int state = (Integer) objs[1];
			int role = (Integer) objs[2];
			switch(role) {
				case 0: // admin
					result = String.format(result, "true", "%s", "0");
					break;
				case 1: // user
					result = String.format(result, "true", "%s", "1");
					break;
			}
			switch(state) {
				case 0: // success
					result = String.format(result, "0");
					new LogHandler().newLog(username, null, null, "LOGIN", System.currentTimeMillis());
					break;
				case 1: // unactived
					result = String.format(result, "1");
					break;
				case 2: // login limited
					result = String.format(result, "2");
					break;
				case 3: // operation limited
					result = String.format(result, "3");
					break;
			}
			request.getSession().setAttribute("username", username);
		}
		response.getWriter().print(result);
	}
}
