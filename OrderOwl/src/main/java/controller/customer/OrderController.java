package controller.customer;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import controller.common.Controller;
import controller.common.ModelAndView;
import dao.customer.OrderDAOImpl;
import dao.customer.TransferJsonDTO;
import dto.CategoryDTO;
import dto.OrderDetailDTO;
import dto.StoreDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.customer.MenuService;
import service.customer.OrderService;

public class OrderController implements Controller {

	OrderService os = new OrderService();
	MenuService ss = new MenuService();

	public ModelAndView selectByModelNum(HttpServletRequest request, HttpServletResponse response) {
		;
		try {
			int tableNo = Integer.parseInt(request.getParameter("tableNo"));
			StoreDTO store = os.selectStoreByStoreId(tableNo);
			int storeId = store.getStoreId();

			int orderId = os.canOrderCheck(tableNo, storeId);
			if(!(request.getAttribute("categoryKey").equals("null"))) {
				System.out.println(ss.selectMenuByCategory(
						
					
					Integer.parseInt((String)request.getAttribute("categoryKey"))));
				
				request.setAttribute("menu", ss.selectMenuByCategory(
						
					
					Integer.parseInt((String)request.getAttribute("categoryKey"))));
				
			}
			else {
				
				request.setAttribute("menu", ss.selectMenuByStoreId(tableNo));
			}
			System.out.println(request.getAttribute("categoryKey"));
			request.setAttribute("cusOrderId", orderId);
			request.setAttribute("store", store);
			request.setAttribute("orderid", os.selectStoreByStoreId(tableNo));
			return new ModelAndView("/user/order_page.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/user/order_page.jsp");
		}
	}

	public ModelAndView requestOrder(HttpServletRequest request, HttpServletResponse response) {

		try {
			int orderId = Integer.parseInt(request.getAttribute("orderId").toString());
			List list = (List) request.getAttribute("jsonData");
			orderId = new OrderDAOImpl().findLastOrderId(orderId);
			os.requestOrder(list, orderId);
			
			return new ModelAndView("/user/order_page.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("/user/order_page.jsp");
		}
	}

	public Object requestOrderData(HttpServletRequest request, HttpServletResponse response) {

		int orderId = Integer.parseInt(request.getAttribute("orderId").toString());
		List<TransferJsonDTO> list = null;
		try {
			orderId = new OrderDAOImpl().findLastOrderId(orderId);
			list = os.requestOrderData(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}
	
	
	public Object requestCategoryData(HttpServletRequest request, HttpServletResponse response) {

		int orderId = Integer.parseInt(request.getAttribute("orderId").toString());
		List<CategoryDTO> list = null;
		try {
			list = os.requestCategoryData(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}
	

}
