package controller.customer;

import controller.common.Controller;
import controller.common.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.user.MenuService;
import service.user.OrderService;

public class OrderController implements Controller {
	
	OrderService os = new OrderService();
	MenuService ss = new MenuService();

public ModelAndView selectByModelNum(HttpServletRequest request, HttpServletResponse response) {
	;
		try {
			request.setAttribute("store",os.selectStoreByStoreId());
			request.setAttribute("menu",ss.selectMenuByStoreId());
		
			return new ModelAndView("/user/order_page.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/user/order_page.jsp");
		}
	}

}
