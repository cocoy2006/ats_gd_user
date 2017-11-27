package android.testing.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession(false);
		String currentURL = request.getRequestURI();
		System.out.println("currentURL is : " + currentURL);
		String targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());
		System.out.println("targetURL is : " + targetURL);
		if (!"/pages/index.jsp".equals(targetURL)) {
			if(session == null || session.getAttribute("username") == null) {
				System.out.println("request.getContextPath() is : " + request.getContextPath());
				response.sendRedirect(request.getContextPath() + "/pages/index.jsp");
				return;
			}
		}
		chain.doFilter(arg0, arg1);
	}

	public void init(FilterConfig config) throws ServletException {
		
	}
}