package controller.admin;

import controller.common.Controller;
import controller.common.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import dao.AdminDAO;
import service.AdminServiceImpl;
import dto.AdminDTO.*;

/**
 * 관리자 Controller - DispatcherServlet 방식
 */
public class AdminController implements Controller {
    
    private AdminServiceImpl adminService;
    private AdminDAO adminDAO;
    private Gson gson;

    public AdminController() {
        this.adminService = new AdminServiceImpl();
        this.adminDAO = new AdminDAO();
        
        // LocalDate를 지원하는 Gson 생성
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> 
                context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> 
                LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            .create();
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
     * 전체 매장 목록 조회 (통계 정보 포함)
     */
    public ModelAndView getStoreList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<StoreDTO> stores = adminService.getStoreList();
            
            // 각 매장의 통계 정보 추가
            for (StoreDTO store : stores) {
                // PENDING 상태 매장(음수 ID)이 아닌 경우에만 통계 조회
                if (store.getStoreId() > 0) {
                    try {
                        // 메뉴 개수
                        int menuCount = adminDAO.countStoreMenus(store.getStoreId());
                        store.setMenuCount(menuCount);
                        
                        // 주문 개수
                        int orderCount = adminDAO.countStoreOrders(store.getStoreId());
                        store.setTotalOrders(orderCount);
                        
                        // 총 매출
                        long totalSales = adminDAO.sumStoreSales(store.getStoreId());
                        store.setTotalSales(totalSales);
                        
                    } catch (Exception e) {
                        // 통계 조회 실패시 0으로 설정
                        store.setMenuCount(0);
                        store.setTotalOrders(0);
                        store.setTotalSales(0);
                    }
                }
            }
            
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
    
    // ==================== 매장 요청 거절 API 추가 ====================
    
    /**
     * 매장 등록 요청 거절
     */
    public ModelAndView rejectStoreInfoAddRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long requestId = Long.parseLong(request.getParameter("requestId"));
            String reason = request.getParameter("reason");
            
            boolean success = adminService.rejectStoreInfoAddRequest(requestId, reason);
            
            result.put("success", success);
            result.put("message", success ? "매장 등록 요청이 거절되었습니다." : "거절에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "거절 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 정보 수정 요청 거절
     */
    public ModelAndView rejectStoreInfoUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long requestId = Long.parseLong(request.getParameter("requestId"));
            String reason = request.getParameter("reason");
            
            boolean success = adminService.rejectStoreInfoUpdateRequest(requestId, reason);
            
            result.put("success", success);
            result.put("message", success ? "매장 정보 수정 요청이 거절되었습니다." : "거절에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "거절 처리 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 정보 직접 수정
     */
    public ModelAndView updateStoreInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            
            // 기존 매장 정보 조회
            StoreDTO store = adminService.getStoreInfo(storeId);
            if (store == null) {
                result.put("success", false);
                result.put("message", "매장을 찾을 수 없습니다.");
            } else {
                // 수정 가능한 필드만 업데이트
                store.setStoreName(request.getParameter("storeName"));
                store.setAddress(request.getParameter("address"));
                store.setPhoneNumber(request.getParameter("phoneNumber"));
                store.setStatus(request.getParameter("status"));
                
                boolean success = adminService.updateStoreInfo(store);
                
                result.put("success", success);
                result.put("message", success ? "매장 정보가 수정되었습니다." : "수정에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 정보 수정 중 오류가 발생했습니다.");
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

    // ==================== QR 관리 ====================
    
    /**
     * 전체 매장 QR 정보 조회
     */
    public ModelAndView getAllStoresWithQR(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<StoreDTO> stores = adminService.getAllStoresWithQR();
            result.put("success", true);
            result.put("data", stores);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 QR 정보를 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 QR 코드 재생성
     */
    public ModelAndView regenerateStoreQR(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            boolean success = adminService.regenerateStoreQR(storeId);
            
            result.put("success", success);
            result.put("message", success ? "QR 코드가 재생성되었습니다." : "QR 코드 재생성에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "QR 코드 재생성 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 매장 QR 경로 업데이트
     */
    public ModelAndView updateStoreQRPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            String qrPath = request.getParameter("qrPath");
            
            boolean success = adminService.updateStoreQRPath(storeId, qrPath);
            
            result.put("success", success);
            result.put("message", success ? "QR 경로가 업데이트되었습니다." : "QR 경로 업데이트에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "QR 경로 업데이트 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }

    // ==================== 메뉴 관리 ====================
    
    /**
     * 매장별 메뉴 목록 조회
     */
    public ModelAndView getStoreMenus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long storeId = Long.parseLong(request.getParameter("storeId"));
            List<MenuDTO> menus = adminService.getStoreMenus(storeId);
            
            result.put("success", true);
            result.put("data", menus);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "메뉴 목록을 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 메뉴 직접 추가
     */
    public ModelAndView addMenuDirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            MenuDTO menu = new MenuDTO();
            menu.setStoreId(Long.parseLong(request.getParameter("storeId")));
            menu.setMenuName(request.getParameter("menuName"));
            menu.setPrice(Integer.parseInt(request.getParameter("price")));
            menu.setCategory(request.getParameter("category"));
            menu.setDescription(request.getParameter("description"));
            
            boolean success = adminService.addMenuDirect(menu);
            
            result.put("success", success);
            result.put("message", success ? "메뉴가 추가되었습니다." : "메뉴 추가에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "메뉴 추가 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 메뉴 직접 수정
     */
    public ModelAndView updateMenuDirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            MenuDTO menu = new MenuDTO();
            menu.setMenuId(Long.parseLong(request.getParameter("menuId")));
            menu.setStoreId(Long.parseLong(request.getParameter("storeId")));
            menu.setMenuName(request.getParameter("menuName"));
            menu.setPrice(Integer.parseInt(request.getParameter("price")));
            menu.setCategory(request.getParameter("category"));
            menu.setDescription(request.getParameter("description"));
            
            boolean success = adminService.updateMenuDirect(menu);
            
            result.put("success", success);
            result.put("message", success ? "메뉴가 수정되었습니다." : "메뉴 수정에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "메뉴 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
    
    /**
     * 메뉴 직접 삭제
     */
    public ModelAndView deleteMenuDirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            long menuId = Long.parseLong(request.getParameter("menuId"));
            boolean success = adminService.deleteMenuDirect(menuId);
            
            result.put("success", success);
            result.put("message", success ? "메뉴가 삭제되었습니다." : "메뉴 삭제에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "메뉴 삭제 중 오류가 발생했습니다: " + e.getMessage());
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
    
    /**
     * 승인/거절 히스토리 조회
     */
    /**
     * 승인/거절 히스토리 조회 - 메뉴 부분 제거
     */
    public ModelAndView getApprovalHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            String type = request.getParameter("type");
            String sortOrder = request.getParameter("sortOrder");
            int page = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            
            // 기본값 설정 - 메뉴 타입 제거
            if (type == null || type.trim().isEmpty() || "MENU".equals(type)) {
                type = "STORE"; // 기본값을 STORE로 변경
            }
            if (sortOrder == null || sortOrder.trim().isEmpty()) {
                sortOrder = "DESC";
            }
            
            result = adminService.getApprovalHistory(type, sortOrder, page, pageSize);
            
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "히스토리 조회 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
}