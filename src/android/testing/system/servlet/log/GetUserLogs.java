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

import android.testing.system.device.Device;
import android.testing.system.device.DeviceHandler;
import android.testing.system.log.Log;
import android.testing.system.log.LogHandler;

import com.google.gson.Gson;

public class GetUserLogs extends HttpServlet {
	
	private Map<Integer, String[]> deviceInfo;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		
		long startTime = Long.parseLong(request.getParameter("startTime"));
		long endTime = Long.parseLong(request.getParameter("endTime"));
		if(endTime == 0) {
//			Calendar c = Calendar.getInstance();
//			c.set(Calendar.YEAR, 2999);
//			endTime = c.getTimeInMillis();
			endTime = 32489436216541L;
		}
		
		
		deviceInfo = new HashMap<Integer, String[]>();
		
		List list = new ArrayList();
		for(Object obj : new LogHandler().getLogs(username, startTime, endTime)) {
			Log log = (Log) obj;
			Map map = new HashMap();
			map.put("username", username);
			map.put("time", log.getTime());
			map.put("operation", log.getOperation());
			String[] info = getDeviceInfo(log.getDevice_id());
			if(info != null) {
				map.put("server", info[0]);
				map.put("serialNumber", info[1]);
			} else {
				map.put("server", null);
				map.put("serialNumber", null);
			}
			list.add(map);
		}
//		Gson gson = new Gson();
		response.setContentType("text/html;charset=utf-8");
//		response.getWriter().print(gson.toJson(list));
		
//		List list = new LogHandler().getLogs(username, startTime, endTime);
		response.getOutputStream().print(new Gson().toJson(list));
	}
	
	private String[] getDeviceInfo(int id) {
		if(id == 0) {
			return null;
		} else {
			if(!deviceInfo.containsKey(id)) { // not contain
				Device d = new DeviceHandler().getDeviceInfo(id);
				if(d != null) {
					String[] info = new String[2];
					info[0] = d.getServer();
					info[1] = d.getSerialNumber();
					deviceInfo.put(id, info);
					return info;
				} else {
					return null;
				}
			} else {
				return deviceInfo.get(id);
			}
		}
	}
}
