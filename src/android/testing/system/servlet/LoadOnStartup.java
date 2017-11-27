package android.testing.system.servlet;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import android.testing.system.reservation.ReservationHandler;
import android.testing.system.util.Util;

public class LoadOnStartup extends HttpServlet implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {
		Util util = Util.getInstance();
		// ��ʼ����̨����������
//		String servers_dir = arg0.getServletContext().getRealPath("/servers/");
//		util.initServers(servers_dir);
		// ��ʼ��ԤԼ��ʱ��ʱ��
		
		// ��ʼ����������Ŀ¼
		util.addProperty("testcases", arg0.getServletContext().getInitParameter("testcases"));
//		util.addProperty("testcaseRoot", arg0.getServletContext().getInitParameter("testcaseRoot"));
//		util.addProperty("logRoot", arg0.getServletContext().getInitParameter("logRoot"));
		
		util.addProperty("temp", arg0.getServletContext().getRealPath("/temp/"));
		// mail relative
		util.addProperty("MAIL_STMP", arg0.getServletContext().getInitParameter("MAIL_STMP"));
		util.addProperty("MAIL_PORT", arg0.getServletContext().getInitParameter("MAIL_PORT"));
		util.addProperty("MAIL_USERNAME", arg0.getServletContext().getInitParameter("MAIL_USERNAME"));
		util.addProperty("MAIL_PASSWORD", arg0.getServletContext().getInitParameter("MAIL_PASSWORD"));
		util.addProperty("MAIL_ADDRESS", arg0.getServletContext().getInitParameter("MAIL_ADDRESS"));
		util.addProperty("MAIL_TITLE", arg0.getServletContext().getInitParameter("MAIL_TITLE"));
		
		Timer reservationPollingTimer = new Timer();
		reservationPollingTimer.schedule(new PollingTask(), 0, 60 * 1000); // 60 seconds
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	
	class PollingTask extends TimerTask {

		@Override
		public void run() {
			new ReservationHandler().handleReservations(System.currentTimeMillis());
		}
	}
}
