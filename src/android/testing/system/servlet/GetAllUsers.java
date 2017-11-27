package android.testing.system.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.user.User;
import android.testing.system.user.UserHandler;

import com.google.gson.Gson;

public class GetAllUsers extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		List users = new UserHandler().getAllUsers();
		List users = new ArrayList();
		for(Object obj : new UserHandler().getAllUsers()) {
			User u = (User) obj;
			Map user = new HashMap();
			user.put("id", u.getId());
			user.put("username", u.getUsername());
			user.put("nickname", u.getNickname());
			user.put("password", u.getPassword());
			user.put("email", u.getEmail());
			user.put("phone_no", u.getPhone_no());
			user.put("lefttime", u.getLefttime());
			user.put("state", u.getState());
			user.put("role", u.getRole());
			users.add(user);
		}
		Gson gson = new Gson();
		response.getWriter().print(gson.toJson(users));
	}
}
