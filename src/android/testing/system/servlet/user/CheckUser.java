package android.testing.system.servlet.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.log.LogHandler;
import android.testing.system.user.User;
import android.testing.system.user.UserHandler;

import com.google.gson.Gson;

public class CheckUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Object o = new UserHandler().checkUser(username, password);

		response.setContentType("text/html;charset=utf-8");
		if(o == null) {
			response.getWriter().print("");
		} else {
			User u = (User) o;
			
			int role = Integer.parseInt(request.getParameter("role"));
			if(role == u.getRole()) {
				Map map = new HashMap();
				map.put("state", u.getState());
				map.put("role", u.getRole());
				
				Gson gson = new Gson();
				String result = gson.toJson(map);
				
				request.getSession().setAttribute("username", username);
				new LogHandler().newLog(username, null, null, "LOGIN", System.currentTimeMillis());
				
				response.getWriter().print(result);
			} else {
				response.getWriter().print(1);
			}
		}
	}
}
