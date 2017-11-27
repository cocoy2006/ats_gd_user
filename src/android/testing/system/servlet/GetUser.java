package android.testing.system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.user.UserHandler;

public class GetUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		
		Object obj = new UserHandler().getUser(username);
		Object[] objs = (Object[]) obj;
		int id = (Integer) objs[0];
		String nickname = objs[1].toString();
		String email = objs[2].toString();
		String phone_no = objs[3].toString();
		
//		StringBuffer sb = new StringBuffer();
//		sb.append("{\"id\":\"").append(id).append("\",\"email\":\"").append(email)
//			.append("\",\"phone\":\"").append(phone).append("\"}");
//		response.getWriter().print(sb.toString());
		
		String json = "{'id':'%s', 'nickname':'%s', 'email':'%s', 'phone_no':'%s'}";
		json = String.format(json, id, nickname, email, phone_no);
		response.getWriter().print(json);
	}
}
