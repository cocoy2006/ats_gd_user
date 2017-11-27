package android.testing.system.servlet.reservation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.reservation.ReservationHandler;

public class NewReservation extends HttpServlet {

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
//		int diff = Integer.parseInt(request.getParameter("diff"));
		
//		Reservation r = new Reservation();
//		r.setUsername(username);
//		r.setServer(server);
//		r.setSerialNumber(serialNumber);
//		r.setStartTime(startTime);
//		r.setEndTime(endTime);
		
//		boolean result = new ReservationHandler().reserve(r);
		int result = new ReservationHandler().newReservation(username, server, serialNumber, startTime, endTime);
		
		response.getWriter().print(result);
	}
}
