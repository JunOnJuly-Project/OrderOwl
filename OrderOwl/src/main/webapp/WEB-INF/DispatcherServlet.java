package controller.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 모든 사용자 요청을 처리할 진입점 Controller의 역할
 */
@WebServlet(urlPatterns = "/front", loadOnStartup = 1)
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 5,      // 5MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Map<String, Controller> map;
    
    public DispatcherServlet() {
        System.out.println("DispatcherServlet 생성자...");
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext application = config.getServletContext();
        map = (Map<String, Controller>)application.getAttribute("map");
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String key = request.getParameter("key");
        String methodName = request.getParameter("methodName");
        
        // 기본값 설정
        if (key == null || key.equals("")) {
            key = "elec";
        }
        
        if (methodName == null || methodName.equals("")) {
            methodName = "select";
        }
        
        try {
            Controller con = map.get(key);
            
            if (con == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, 
                    "Controller not found for key: " + key);
                return;
            }
            
            Class<?> clz = con.getClass();
            Method method = clz.getMethod(methodName, 
                HttpServletRequest.class, HttpServletResponse.class);
            
            // 메서드 실행
            ModelAndView mv = (ModelAndView) method.invoke(con, request, response);
            
            // ⭐ 핵심 수정: mv가 null이면 JSON 응답을 직접 출력한 것이므로 리턴
            if (mv == null) {
                return;
            }
            
            // ModelAndView가 있는 경우 페이지 이동 처리
            if (mv.isRedirect()) {
                response.sendRedirect(mv.getViewName());
            } else {
                request.getRequestDispatcher(mv.getViewName())
                    .forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // 에러 메시지 처리 개선
            String errorMsg = e.getMessage();
            if (errorMsg == null && e.getCause() != null) {
                errorMsg = e.getCause().getMessage();
            }
            if (errorMsg == null) {
                errorMsg = "알 수 없는 오류가 발생했습니다.";
            }
            
            request.setAttribute("errorMsg", errorMsg);
            request.getRequestDispatcher("error/error.jsp")
                .forward(request, response);
        }
    }
}