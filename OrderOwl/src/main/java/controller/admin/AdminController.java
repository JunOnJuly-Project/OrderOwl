package controller.admin;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import controller.common.Controller;
import controller.common.ModelAndView;
import dto.AdminDTO.SalesReportDTO;
import dto.AdminDTO.StoreDTO;
import dto.AdminDTO.StoreRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminServiceImpl;

/**
 * 관리자 Controller - DispatcherServlet 방식
 */
public class AdminController implements Controller {
    
    private AdminServiceImpl adminService;
    private Gson gson;

    public AdminController() {
        this.adminService = new AdminServiceImpl();
        this.gson = new Gson();
    }

    // ==================== 대시보드 페이지 ====================
    
    /**
     * 관리자 대시보드 페이지로 이동
     */
    public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("admin/adminDashboard.jsp");
    }

    // ==================== 매장 관리 ====================
    
    /**
     * 전체 매장 목록 조회
     */
    public ModelAndView getStoreList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<StoreDTO> stores = adminService.getStoreList();
            result.put("success", true);
            result.put("data", stores);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 목록을 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 삭제 대기 매장 목록 조회
     */
    public ModelAndView getDeletePendingStores(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<StoreDTO> stores = adminService.getDeletePendingStores();
            result.put("success", true);
            result.put("data", stores);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "삭제 대기 매장 목록을 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 삭제 대기 매장 최종 승인
     */
    public ModelAndView approveStoreDeletion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            boolean success = adminService.approveStoreDeletion(storeId);
            
            result.put("success", success);
            result.put("message", success ? "매장이 삭제되었습니다." : "삭제에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 삭제 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 삭제 대기 취소
     */
    public ModelAndView cancelStoreDeletion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            boolean success = adminService.cancelStoreDeletion(storeId);
            
            result.put("success", success);
            result.put("message", success ? "삭제가 취소되었습니다." : "취소에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "삭제 취소 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 상세 정보 조회
     */
    public ModelAndView getStoreInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            StoreDTO store = adminService.getStoreInfo(storeId);
            
            if (store != null) {
                result.put("success", true);
                result.put("data", store);
            } else {
                result.put("success", false);
                result.put("message", "매장 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 정보를 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 가입 요청 목록 조회
     */
    public ModelAndView getStoreRequests(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<StoreRequestDTO> requests = adminService.getStoreRequests();
            result.put("success", true);
            result.put("data", requests);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 요청 목록을 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 등록 요청 승인
     */
    public ModelAndView approveStoreInfoAddRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long requestId = Long.parseLong(request.getParameter("requestId"));
            boolean success = adminService.approveStoreInfoAddRequest(requestId);
            
            result.put("success", success);
            result.put("message", success ? "매장 등록이 승인되었습니다." : "승인에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "승인 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 정보 수정 요청 승인
     */
    public ModelAndView approveStoreInfoUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long requestId = Long.parseLong(request.getParameter("requestId"));
            boolean success = adminService.approveStoreInfoUpdateRequest(requestId);
            
            result.put("success", success);
            result.put("message", success ? "매장 정보 수정이 승인되었습니다." : "승인에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "승인 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 삭제
     */
    public ModelAndView deleteStore(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            boolean success = adminService.deleteStore(storeId);
            
            result.put("success", success);
            result.put("message", success ? "매장이 삭제되었습니다." : "삭제에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 삭제 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 QR 코드 생성
     */
    public ModelAndView createStoreQR(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            String qrPath = adminService.createStoreQR(storeId);
            
            if (qrPath != null) {
                result.put("success", true);
                result.put("qrPath", qrPath);
                result.put("message", "QR 코드가 생성되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "QR 코드 생성에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "QR 코드 생성 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }

    // ==================== 메뉴 요청 관리 ====================
    
    /**
     * 메뉴 요청 목록 조회
     */
    public ModelAndView getMenuRequests(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            result.put("success", true);
            result.put("data", new java.util.ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "메뉴 요청 목록을 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 메뉴 추가 요청 승인
     */
    public ModelAndView approveMenuAddRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long requestId = Long.parseLong(request.getParameter("requestId"));
            boolean success = adminService.approveMenuAddRequest(requestId);
            
            result.put("success", success);
            result.put("message", success ? "메뉴 추가가 승인되었습니다." : "승인에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "승인 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 메뉴 수정 요청 승인
     */
    public ModelAndView approveMenuUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long requestId = Long.parseLong(request.getParameter("requestId"));
            boolean success = adminService.approveMenuUpdateRequest(requestId);
            
            result.put("success", success);
            result.put("message", success ? "메뉴 수정이 승인되었습니다." : "승인에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "승인 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 메뉴 삭제 요청 승인
     */
    public ModelAndView approveMenuDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long requestId = Long.parseLong(request.getParameter("requestId"));
            boolean success = adminService.approveMenuDeleteRequest(requestId);
            
            result.put("success", success);
            result.put("message", success ? "메뉴 삭제가 승인되었습니다." : "승인에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "승인 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }

    // ==================== 유저 관리 ====================
    
    /**
     * 유저 목록 조회
     */
    public ModelAndView getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            result.put("success", true);
            result.put("data", new java.util.ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "유저 목록을 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 유저 상세 정보 조회
     */
    public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long userId = Long.parseLong(request.getParameter("userId"));
            result.put("success", true);
            result.put("message", "유저 정보 조회 기능은 구현 중입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "유저 정보를 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 유저 강제 탈퇴
     */
    public ModelAndView forceDeleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long userId = Long.parseLong(request.getParameter("userId"));
            String reason = request.getParameter("reason");
            
            boolean success = adminService.forceDeleteUser(userId, reason);
            
            result.put("success", success);
            result.put("message", success ? "유저가 강제 탈퇴 처리되었습니다." : "강제 탈퇴에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "강제 탈퇴 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }

    // ==================== 매출 정보 ====================
    
    /**
     * 매장 매출 정보 조회
     */
    public ModelAndView getStoreSalesInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            
            SalesReportDTO report = adminService.getStoreSalesInfo(storeId, startDate, endDate);
            
            if (report != null) {
                result.put("success", true);
                result.put("data", report);
            } else {
                result.put("success", false);
                result.put("message", "매출 정보를 불러오는데 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매출 정보 조회 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
}
