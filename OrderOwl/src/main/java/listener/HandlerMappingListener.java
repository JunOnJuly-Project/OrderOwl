package listener;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import controller.common.Controller;

@WebListener
public class HandlerMappingListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent e)  { 
    	ServletContext application = e.getServletContext();
		String fileName = application.getInitParameter("fileName");
		ResourceBundle rb = ResourceBundle.getBundle(fileName);

		Map<String, Controller> clazzMap = new HashMap<>();
		try {
			for (String key : rb.keySet()) {
				String value = rb.getString(key);
				
				Class<?> className = Class.forName(value);
				Controller con = (Controller) className.getDeclaredConstructor().newInstance();
				
				clazzMap.put(key, con);
			}			
			
			application.setAttribute("clazzMap", clazzMap);
			application.setAttribute("path", application.getContextPath());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
    }
}