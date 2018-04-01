package interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hjn.tmall.pojo.Category;
import com.hjn.tmall.pojo.OrderItem;
import com.hjn.tmall.pojo.User;
import com.hjn.tmall.service.CategoryService;
import com.hjn.tmall.service.OrderItemService;

public class OtherInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	CategoryService categoryService;
	@Autowired
	OrderItemService orderItemService;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 * 可在modelAndView中加入数据，比如当前时间
	 */

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,    
			ModelAndView modelAndView) throws Exception {
		
		HttpSession session = request.getSession();		
		/*获取购物车中的产品数量*/
		User user =(User)  session.getAttribute("user");
		int  cartTotalItemNumber = 0;
		if(null!=user) {
			List<OrderItem> ois = orderItemService.CartOrderItem(user.getId());
			for (OrderItem oi : ois) {
				cartTotalItemNumber+=oi.getNumber();
			}

		}
		session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);

	}

	public void afterCompletion(HttpServletRequest request,    
			HttpServletResponse response, Object handler, Exception ex)  
					throws Exception {  
	}  
}
