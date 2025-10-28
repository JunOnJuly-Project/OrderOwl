package listener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import controller.common.Controller;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class HandlerMappingListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent e)  { 
    	ServletContext application = e.getServletContext();
    	String uploadRelPath = application.getInitParameter("saveDir");
        String saveDir = application.getRealPath(uploadRelPath);

        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

		String fileName = application.getInitParameter("fileName");
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		
    	application.setAttribute("saveDir", saveDir);
    	
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