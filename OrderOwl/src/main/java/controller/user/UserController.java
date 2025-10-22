package controller.user;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import controller.common.Controller;
import controller.common.ModelAndView;
import dto.MenuDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    			3, // 임의 삽입임
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
    	List<MenuDTO> menus = userService.selectAllMenu(3);
    	System.out.println(menus);
    	System.out.println("userController/selectAllMenu");
    	request.setAttribute("menus", menus);
    	
    	return new ModelAndView("user/menu/list/list.jsp");
    }

}
