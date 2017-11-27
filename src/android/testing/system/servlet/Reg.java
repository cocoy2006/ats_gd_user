package android.testing.system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.user.UserHandler;
import android.testing.system.util.Mail.Mail;

public class Reg extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fromUrl = request.getRequestURL().toString();
		
		String username = request.getParameter("r_u");
		String password = request.getParameter("r_p");
		
		UserHandler handler = new UserHandler();
		int result = 1;
		if(handler.checkUser(username, password) != null) {
			result = -1;
		} else {
			String nickname = request.getParameter("r_n");
			if(nickname == null) nickname = "";
			String email = request.getParameter("r_email");
			String phone_no = request.getParameter("r_phone_no");
			if(phone_no == null) phone_no = "";
			
			if(handler.createUser(username, nickname, password, email, phone_no, 15 * 60, 1, 1) != -1) {
				int id = handler.getUser(username, 0).getId();
//				request.getSession().setAttribute("username", username);
				Mail mail = new Mail(id, username, email, fromUrl);
				mail.send();
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}
}
