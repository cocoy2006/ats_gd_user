package android.testing.system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.user.UserHandler;

public class UpdateUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("u_id"));
		String nickname = request.getParameter("u_n");
//		String password = request.getParameter("u_p");
		String email = request.getParameter("u_email");
		String phone_no = request.getParameter("u_phone_no");

		
		boolean success = new UserHandler().updateUser(id, nickname, email, phone_no);
		int flag = 0;
		if(success) flag = 1;
		
		response.getWriter().print(flag);
	}
}
