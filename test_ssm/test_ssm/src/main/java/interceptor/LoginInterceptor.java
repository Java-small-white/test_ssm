package interceptor;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hjn.tmall.pojo.User;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,Object handler) throws Exception {
		HttpSession session=request.getSession();
		String contextPath=session.getServletContext().getContextPath();
		String[] notLoginPage=new String[] {
			"home",
			"Register",
			"login",
			"Product",
			"CheckLogin",
			"LoginAjax",
			"Category",
			"Search",
		};
		String uri=request.getRequestURI();
		uri=StringUtils.remove(uri, contextPath);
		if(uri.startsWith("/fore")) {
			String method=StringUtils.substringAfterLast(uri, "/fore");
			if(!Arrays.asList(notLoginPage).contains(method)) {
				User user =(User) session.getAttribute("user");
				if(user==null) {
					response.sendRedirect("loginPage");
					return false;
				}
			}
		}
		return true;
	}
}
