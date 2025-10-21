package controller.customer;

import controller.common.Controller;
import controller.common.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MenuController implements Controller {
	
	public MenuController() {
	
	}

	public ModelAndView select(HttpServletRequest request, HttpServletResponse response) {
	
		return new ModelAndView("elec/list.jsp");
	}
	
	
	

}
