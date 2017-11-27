package android.testing.system.servlet.device;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.device.Device;
import android.testing.system.device.DeviceHandler;

import com.google.gson.Gson;

public class GetDevice extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Device d = new DeviceHandler().getDevice(id);
		
		response.setContentType("text/html;charset=utf-8");
		response.getOutputStream().print(new Gson().toJson(d));
	}

}
