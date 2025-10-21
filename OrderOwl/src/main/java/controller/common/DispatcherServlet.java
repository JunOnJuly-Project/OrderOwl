package controller.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/front", loadOnStartup = 1)
@MultipartConfig(    // 업로드 관련 기본 설정
	    fileSizeThreshold = 1024 * 1024,
	    maxFileSize = 1024 * 1024 * 10, 
	    maxRequestSize = 1024 * 1024 * 50 
)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Map<String, Controller> clazzMap;
	
    public DispatcherServlet() {
    	System.out.println("DispatcherServlet constructor call...");
    }

    @Override
    public void init() throws ServletException {
    	ServletContext application = super.getServletContext();
    	this.clazzMap = (Map<String, Controller>) application.getAttribute("clazzMap");
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("key");
		String methodName = request.getParameter("methodName");
		System.out.println(key + " " + methodName);
		if (key == null || key.equals("")) {
			key = "elec";
		}
		
		if (methodName == null || methodName.equals("")) {
			methodName = "list";
		}
		
		try {
			Controller con = clazzMap.get(key);
			Class<?> className = con.getClass();
			Method method = className.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		
			ModelAndView mv = (ModelAndView) method.invoke(con, request, response);
			String viewName = mv.getViewName();
			
			if (mv.isRedirect()) {
				response.sendRedirect(viewName);
			}
			
			else {
				request.getRequestDispatcher(viewName).forward(request, response);
			}
		} 
		
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getCause().getMessage());
			request.getRequestDispatcher("error/error.jsp").forward(request, response);
		}
	}
}
