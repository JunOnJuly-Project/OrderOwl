package controller.user;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.common.Controller;
import controller.common.ModelAndView;
import dto.MenuDTO;
import dto.OrderDTO;
import dto.StoreDTO;
import dto.UserDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.user.UserService;
import service.user.UserServiceImpl;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/user")
public class UserController extends HttpServlet implements Controller {
	private UserService userService = new UserServiceImpl();
	
    public UserController() {}
    
    public ModelAndView insertMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    	
    	String closeTime = request.getParameter("closeTime");
    	LocalTime closeLocalTime = closeTime.equals("") ? 
    				LocalTime.parse("24:00", formatter) : 
    				LocalTime.parse(closeTime, formatter);
    	
    	MenuDTO menu = userService.createMenu(
    			((UserDTO) request.getSession().getAttribute("user")).getUserId(),
    			request.getParameter("name"), 
    			Integer.parseInt(request.getParameter("price")), 
    			request.getParameter("description"), 
    			request.getParameter("src"), 
    			Optional.ofNullable(request.getParameter("category1Code"))
    			.map(Integer::parseInt)
    			.orElse(1),
    			Optional.ofNullable(request.getParameter("category2Code"))
    			.map(Integer::parseInt)
    			.orElse(1),
    			request.getParameter("checkRec"), 
    			request.getParameter("orderRequest"), 
    			closeLocalTime,
    			request.getParameter("soldOut")
		);
    	
    	System.out.println(menu);
    	userService.insertMenu(menu);
    	return new ModelAndView("/front?key=user&methodName=selectAllMenu");
    }
    
    public ModelAndView selectById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MenuDTO menu = userService.selectById(Integer.parseInt(request.getParameter("menuId")));
    	request.setAttribute("menu", menu);
    	System.out.println(menu);
    	return new ModelAndView("user/menu/update/update.jsp");
    }
    
    public ModelAndView selectForUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	StoreDTO store = userService.selectStore(
			((UserDTO) request.getSession().getAttribute("user")).getUserId()
		);
    	
    	request.setAttribute("store", store);
    	return new ModelAndView("user/inform/update/update.jsp");
    }
    
    public ModelAndView updateMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    	
    	String closeTime = request.getParameter("closeTime");
    	LocalTime closeLocalTime = closeTime.equals("") ? 
    				LocalTime.parse("24:00", formatter) : 
    				LocalTime.parse(closeTime, formatter);
    	
    	MenuDTO menu = userService.createMenu(
    			Integer.parseInt(request.getParameter("menuId")), 
    			Integer.parseInt(request.getParameter("storeId")), 
    			request.getParameter("name"), 
    			Integer.parseInt(request.getParameter("price")), 
    			request.getParameter("description"), 
    			request.getParameter("src"), 
    			Optional.ofNullable(request.getParameter("category1Code"))
    			.map(Integer::parseInt)
    			.orElse(1),
    			Optional.ofNullable(request.getParameter("category2Code"))
    			.map(Integer::parseInt)
    			.orElse(1),
    			request.getParameter("checkRec"), 
    			request.getParameter("orderRequest"), 
    			closeLocalTime,
    			request.getParameter("soldOut")
		);
    	
    	System.out.println(menu);
    	userService.updateMenu(menu);
    	return new ModelAndView("/front?key=user&methodName=selectAllMenu");
    }
    
    public ModelAndView deleteMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	System.out.println(request.getParameter("menuId"));
    	int result = userService.deleteMenu(Integer.parseInt(request.getParameter("menuId")));
    	
    	return new ModelAndView("/front?key=user&methodName=selectAllMenu");
    }
    
    public ModelAndView selectAllMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<MenuDTO> menus = userService.selectAllMenu(
			((UserDTO) request.getSession().getAttribute("user")).getUserId()
		);
    	System.out.println(menus);
    	System.out.println("userController/selectAllMenu");
    	request.setAttribute("menus", menus);
    	
    	return new ModelAndView("user/menu/list/list.jsp");
    }

    public ModelAndView selectSales(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<Integer, Integer> sales = userService.selectSales(
			((UserDTO) request.getSession().getAttribute("user")).getUserId(), 
			request.getParameter("state")
		);
    	
    	StoreDTO store = userService.selectStore(
			((UserDTO) request.getSession().getAttribute("user")).getUserId()
		);
    	
    	System.out.println(sales);
    	request.setAttribute("sales", sales);
    	request.setAttribute("store", store);
    	request.setAttribute("state", request.getParameter("state"));
    	return new ModelAndView("user/inform/inform/inform.jsp");
    }
    
    public ModelAndView selectAllOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<OrderDTO> orders = userService.selectAllOrder(
			((UserDTO) request.getSession().getAttribute("user")).getUserId());
    	System.out.println(orders);
    	
    	List<MenuDTO> menus = userService.selectAllMenu(
			((UserDTO) request.getSession().getAttribute("user")).getUserId());
    	
    	request.setAttribute("menus", menus);
    	request.setAttribute("orders", orders);
    	
    	return new ModelAndView("user/order/order/order.jsp");
    }
    
    public ModelAndView updateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	userService.updateOrder(
			Integer.parseInt(request.getParameter("orderId")), 
			request.getParameter("state")
		);
    	
    	return selectAllOrder(request, response);
    }
    
    public ModelAndView account(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserDTO user = userService.createUser(
			request.getParameter("username"), 
			request.getParameter("password"), 
			request.getParameter("email"), 
			request.getParameter("role")
		);
    	
    	UserDTO accountUser = userService.account(user);
    	if (accountUser == null) {
    		return new ModelAndView("user/auth/login/login.jsp");
    	}
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("user", accountUser);
    	
    	return new ModelAndView("user/menu/list/list.jsp");
    }
    
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	System.out.println(request.getParameter("userEmail"));
    	System.out.println(request.getParameter("password"));
    	
    	UserDTO user = userService.login(
			request.getParameter("userEmail"),
			request.getParameter("password")
		);
    	
    	System.out.println(user);

    	if (user == null) {
    		return new ModelAndView("user/auth/login/login.jsp");
    	}
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("user", user);
    	
    	return new ModelAndView("/front?key=user&methodName=selectAllMenu");
    }
    
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
    	session.invalidate();
    	
    	return new ModelAndView("user/auth/login/login.jsp");
    }
    
    public StoreDTO createUpdateStore(int storeId, String storeName, String address, String region, String phoneNumber,
			String description, String imgSrc) throws Exception {
    	return new StoreDTO(
			storeId,
			storeName,
			address,
			region,
			phoneNumber,
			description,
			imgSrc
		);
    }
    
    public ModelAndView updateStore(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	StoreDTO store = createUpdateStore(
    		Integer.parseInt(request.getParameter("storeId")),
    		request.getParameter("storeName"),
    		request.getParameter("address"),
    		request.getParameter("region"),
    		request.getParameter("phoneNumber"),
    		request.getParameter("description"),
    		request.getParameter("imgSrc")
		);
    	
    	userService.updateStore(store);
    	return new ModelAndView("/front?key=user&methodName=selectSales&state=hour");
    }
    
    public ModelAndView quitStore(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	userService.quitStore(
			Integer.parseInt(request.getParameter("userId"))
		);
    	
    	return new ModelAndView("user/auth/login/login.jsp");
    }
}
