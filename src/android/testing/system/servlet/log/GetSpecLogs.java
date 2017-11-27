package android.testing.system.servlet.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.log.LogHandler;

import com.google.gson.Gson;

public class GetSpecLogs extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String server = request.getParameter("server");
		String serialNumber = request.getParameter("serialNumber");
		long startTime = Long.parseLong(request.getParameter("startTime"));
		long endTime = Long.parseLong(request.getParameter("endTime"));
		if(endTime == 0) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, 2999);
			endTime = c.getTimeInMillis();
		}
		
		List list = new LogHandler().getQueryLogs(username, server, serialNumber, startTime, endTime);
		
		Gson gson = new Gson();
		String result = gson.toJson(list);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}

}
