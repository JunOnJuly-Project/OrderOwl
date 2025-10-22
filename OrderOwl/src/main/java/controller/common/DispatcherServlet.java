package controller.common;


import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/front")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String,Controller> classMap;
	Gson gson = new Gson();
    public DispatcherServlet() {

    }

	public void init() throws ServletException {
		ServletContext app = super.getServletContext();
		classMap = (Map<String, Controller>)app.getAttribute("clazzMap");
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
	String key = request.getParameter("key");
	String methodName = request.getParameter("methodName");
	if(key == null) {
		
		BufferedReader rd =  request.getReader();
		String rawData = rd.lines().collect(Collectors.joining());
		 Map<String, Object> map = gson.fromJson(rawData, Map.class);
		 key =(String) map.get("key");  
		 methodName=   (String) map.get("methodName");

		 
		 Object jsonData = map.get("orders");
		 Object orderId = map.get("orderid");
		   request.setAttribute("jsonData",jsonData);
		   request.setAttribute("orderId",orderId);
	
	
	}


	try {
		Controller con = classMap.get(key);
		Class<?> className = con.getClass();
		Method method = className.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
		ModelAndView mv = (ModelAndView)method.invoke(con, request,response);
		
		if(mv.isRedirect()) {
			response.sendRedirect(mv.getViewName());
		}else {
			request.getRequestDispatcher(mv.getViewName()).forward(request, response);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

}

