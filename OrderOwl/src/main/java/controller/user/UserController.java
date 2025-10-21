package controller.user;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    	
    	MenuDTO menu = userService.createMenu(
    			3, // 임의 삽입임
    			request.getParameter("name"), 
    			Integer.parseInt(request.getParameter("price")), 
    			request.getParameter("description"), 
    			request.getParameter("src"), 
    			Integer.parseInt(request.getParameter("category1Code")), 
				Integer.parseInt(request.getParameter("category2Code")), 
    			request.getParameter("checkRec"), 
    			request.getParameter("orderRequest"), 
    			LocalTime.parse(request.getParameter("closeTime"), formatter),
    			request.getParameter("soldOut")
		);
    	System.out.println(menu);
    	userService.insertMenu(menu);
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
