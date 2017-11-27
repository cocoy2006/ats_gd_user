package android.testing.system.servlet.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.log.Log;
import android.testing.system.log.LogHandler;
import android.testing.system.user.User;
import android.testing.system.user.UserHandler;

import com.google.gson.Gson;

public class GetDeviceLogs extends HttpServlet {
	
	private Map<Integer, String> userInfo;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String server = request.getParameter("server");
		String serialNumber = request.getParameter("serialNumber");
//		int deviceId = Integer.parseInt(request.getParameter("id"));
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, 2999);
		long startTime = Long.parseLong(request.getParameter("startTime"));
		long endTime = Long.parseLong(request.getParameter("endTime"));
		if(endTime == 0) {
//			Calendar c = Calendar.getInstance();
//			c.set(Calendar.YEAR, 2999);
//			endTime = c.getTimeInMillis();
			endTime = 32489436216541L;
		}
		
		userInfo = new HashMap<Integer, String>();
		
		List list = new ArrayList();
		for(Object obj : new LogHandler().getLogs(server, serialNumber, startTime, endTime)) {
//		for(Object obj : new LogHandler().getLogs(deviceId, 0, c.getTimeInMillis())) {
			Log log = (Log) obj;
			Map map = new HashMap();
			map.put("time", log.getTime());
//			map.put("time", new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss").format(new Date(log.getTime())));
			map.put("operation", log.getOperation());
			map.put("username", getUserInfo(log.getUser_id()));
			list.add(map);
		}
		
		Gson gson = new Gson();
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(gson.toJson(list));
	}
	
	private String getUserInfo(int id) {
		if(id == 0) {
			return null;
		} else {
			if(!userInfo.containsKey(id)) { // not contain
				User u = new UserHandler().getUser(id);
				userInfo.put(id, u.getUsername());
				return u.getUsername();
			} else {
				return userInfo.get(id);
			}
		}
	}
}
