package android.testing.system.servlet.device;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.device.DeviceHandler;

public class NewDevice extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String imei = request.getParameter("imei");
		String server = request.getParameter("server");
		if(!server.endsWith("/")) server = server.concat("/");
		String serialNumber = request.getParameter("serialNumber");
		String manufacturer = request.getParameter("manufacturer");
		String model = request.getParameter("model");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		int isEmulator = Integer.parseInt(request.getParameter("isEmulator")); //1 for yes, 0 for no
		String os = request.getParameter("os");
		
		int result = new DeviceHandler().newDevice(imei, server, serialNumber, manufacturer, model, isEmulator, os, width, height);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
	}
}
