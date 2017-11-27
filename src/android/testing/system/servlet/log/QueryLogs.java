package android.testing.system.servlet.log;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import android.testing.system.log.LogHandler;

public class QueryLogs extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String device = request.getParameter("device");
		String server = device.substring(0, device.lastIndexOf("/"));
		String serialNumber = device.substring(device.lastIndexOf("/") + 1);
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
