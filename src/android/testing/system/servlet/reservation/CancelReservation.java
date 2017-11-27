package android.testing.system.servlet.reservation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.testing.system.reservation.ReservationHandler;

public class CancelReservation extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		/*int result = */new ReservationHandler().cancelReservation(id);
		
		String fromUrl = request.getParameter("fromUrl");
		
		response.setContentType("text/html;charset=utf-8");
		response.sendRedirect(fromUrl);
	}
}
