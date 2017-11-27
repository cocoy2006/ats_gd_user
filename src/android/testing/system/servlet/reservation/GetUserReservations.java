package android.testing.system.servlet.reservation;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import android.testing.system.reservation.ReservationHandler;

public class GetUserReservations extends HttpServlet {

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
		
		List list = new ReservationHandler().getUserReservations(username, startTime, endTime);
				
		response.setContentType("text/html;charset=utf-8");
		response.getOutputStream().print(new Gson().toJson(list));
	}

}
