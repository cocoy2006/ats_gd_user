package android.testing.system.servlet.device;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.device.Device;
import android.testing.system.device.DeviceHandler;

public class UnactiveDevices extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		String[] ids = request.getParameterValues("ids");
//		if(ids.length != 1) {
//			response.getOutputStream().print(-1);
////			return;
//		} else {
//			int id = Integer.parseInt(ids[0]);
//			DeviceHandler dh = new DeviceHandler();
//			Device d = dh.getDevice(id);
//			String server = d.getServer();
//			String serialNumber = d.getSerialNumber();
//			dh.unactiveDevice(id);
//			response.sendRedirect(server + "servlet/device/UnactiveDevice?serialNumber=" + serialNumber);
////			return;
//		}
		int result = new DeviceHandler().unactiveDevices(ids);
		
		response.setContentType("text/html;charset=utf-8");
//		response.getWriter().print(result);
		response.getOutputStream().print(result);
	}

}
