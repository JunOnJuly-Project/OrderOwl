package controller.admin;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import dto.StoreDTO;
import dto.UserDTO;
import dto.MenuDTO;

/**
 * 관리자 Controller - DispatcherServlet 방식
 * OrderOwl.sql 스키마 + adminDashboard.jsp 기준 리팩토링
 */
public class AdminController implements Controller {
    
    private AdminServiceImpl adminService;
    private AdminDAO adminDAO;
    private Gson gson;

    public AdminController() {
        this.adminService = new AdminServiceImpl();
        this.adminDAO = new AdminDAO();
        
        // LocalDate, LocalDateTime, LocalTime을 지원하는 Gson 생성
        this.gson = new GsonBuilder()
            // LocalDate 직렬화/역직렬화
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> 
                src == null ? null : context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> 
                json == null || json.isJsonNull() ? null : LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            // LocalDateTime 직렬화/역직렬화
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> 
                src == null ? null : context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> 
                json == null || json.isJsonNull() ? null : LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            // LocalTime 직렬화/역직렬화 추가
            .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, typeOfSrc, context) -> 
                src == null ? null : context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_TIME)))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> 
                json == null || json.isJsonNull() ? null : LocalTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_TIME))
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
     * 매장 상세 정보 조회
     */
    public ModelAndView getStoreInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int storeId = Integer.parseInt(request.getParameter("storeId"));
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
     * 매장 정보 수정 폼 데이터 조회
     */
    public ModelAndView getStoreForEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int storeId = Integer.parseInt(request.getParameter("storeId"));
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
     * 매장 추가
     */
    public ModelAndView addStore(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            StoreDTO store = new StoreDTO(
                0,
                Integer.parseInt(request.getParameter("ownerId")),
                request.getParameter("storeName"),
                request.getParameter("address"),
                request.getParameter("region"),
                request.getParameter("phoneNumber"),
                request.getParameter("description"),
                request.getParameter("imgSrc"),
                null,
                null
            );
            
            boolean success = adminService.addStore(store);
            
            result.put("success", success);
            result.put("message", success ? "매장이 추가되었습니다." : "매장 추가에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "매장 추가 중 오류가 발생했습니다.");
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
            StoreDTO store = new StoreDTO(
                Integer.parseInt(request.getParameter("storeId")),
                Integer.parseInt(request.getParameter("ownerId")),
                request.getParameter("storeName"),
                request.getParameter("address"),
                request.getParameter("region"),
                request.getParameter("phoneNumber"),
                request.getParameter("description"),
                request.getParameter("imgSrc"),
                null,
                null
            );
            
            boolean success = adminService.updateStoreInfo(store);
            
            result.put("success", success);
            result.put("message", success ? "매장 정보가 수정되었습니다." : "수정에 실패했습니다.");
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
            int storeId = Integer.parseInt(request.getParameter("storeId"));
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

    // ==================== 메뉴 관리 ====================
    
    /**
     * 매장별 메뉴 목록 조회
     */
    public ModelAndView getStoreMenus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int storeId = Integer.parseInt(request.getParameter("storeId"));
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
     * 메뉴 직접 수정 (요청 없이)
     * JSP에서 전달하는 파라미터: menuId, storeId, menuName, category, price, description
     */
    public ModelAndView updateMenuDirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            MenuDTO menu = new MenuDTO();
            menu.setMenuId(Integer.parseInt(request.getParameter("menuId")));
            menu.setStoreId(Integer.parseInt(request.getParameter("storeId")));
            menu.setMenuName(request.getParameter("menuName"));
            menu.setPrice(Integer.parseInt(request.getParameter("price")));
            menu.setDescription(request.getParameter("description"));
            
            // imgSrc는 선택사항
            String imgSrc = request.getParameter("imgSrc");
            if (imgSrc != null && !imgSrc.isEmpty()) {
                menu.setImgSrc(imgSrc);
            }
            
            // category는 선택사항 (JSP에서 'category'로 전달)
            // 기본값 0으로 설정 (데이터베이스에서 0은 유효하지 않은 카테고리로 처리)
            String category = request.getParameter("category");
            if (category != null && !category.isEmpty()) {
                try {
                    int categoryCode = Integer.parseInt(category);
                    // 유효한 카테고리인지 확인 (1~5 사이 값만 허용)
                    if (categoryCode >= 1 && categoryCode <= 5) {
                        menu.setCategory1Code(categoryCode);
                    } else {
                        // 유효하지 않으면 0 설정
                        menu.setCategory1Code(0);
                    }
                } catch (NumberFormatException e) {
                    // category가 숫자가 아닐 경우 0 설정
                    menu.setCategory1Code(0);
                }
            } else {
                // category가 없을 경우 0 설정
                menu.setCategory1Code(0);
            }
            
            // category2_code는 항상 0으로 설정 (사용하지 않음)
            menu.setCategory2Code(0);
            
            // checkRec, orderRequest, soldOut 기본값 설정
            String checkRec = request.getParameter("checkRec");
            menu.setCheckRec(checkRec != null ? checkRec : "N");
            
            String orderRequest = request.getParameter("orderRequest");
            menu.setOrderRequest(orderRequest);
            
            String soldOut = request.getParameter("soldOut");
            menu.setSoldOut(soldOut != null ? soldOut : "N");
            
            boolean success = adminService.updateMenuDirect(menu);
            
            result.put("success", success);
            result.put("message", success ? "메뉴가 수정되었습니다." : "수정에 실패했습니다.");
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
     * 메뉴 직접 삭제 (요청 없이)
     */
    public ModelAndView deleteMenuDirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int menuId = Integer.parseInt(request.getParameter("menuId"));
            boolean success = adminService.deleteMenuDirect(menuId);
            
            result.put("success", success);
            result.put("message", success ? "메뉴가 삭제되었습니다." : "삭제에 실패했습니다.");
        } catch (IllegalStateException e) {
            // 진행 중인 주문에 포함된 메뉴 삭제 시도 시
            e.printStackTrace();
            result.put("success", false);
            result.put("message", e.getMessage()); // "진행 중인 주문에 포함된 메뉴는 삭제할 수 없습니다."
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "메뉴 삭제 중 오류가 발생했습니다.");
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
            List<UserDTO> users = adminService.getUserList();
            result.put("success", true);
            result.put("data", users);
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
            int userId = Integer.parseInt(request.getParameter("userId"));
            UserDTO user = adminService.getUserInfo(userId);
            
            if (user != null) {
                result.put("success", true);
                result.put("data", user);
            } else {
                result.put("success", false);
                result.put("message", "유저 정보를 찾을 수 없습니다.");
            }
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
            int userId = Integer.parseInt(request.getParameter("userId"));
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
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            
            Map<String, Object> salesReport = adminService.getStoreSalesInfo(storeId, startDate, endDate);
            
            if (salesReport != null) {
                result.put("success", true);
                result.put("data", salesReport);
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

    // ==================== 테이블별 QR 관리 ====================
    
    /**
     * 매장 테이블 목록 조회
     */
    public ModelAndView getStoreTables(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int storeId = Integer.parseInt(request.getParameter("storeId"));
            List<Map<String, Object>> tables = adminService.getStoreTables(storeId);
            
            result.put("success", true);
            result.put("data", tables);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "테이블 목록을 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }

    /**
     * 테이블별 QR 코드 생성
     */
    public ModelAndView generateTableQR(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int tableId = Integer.parseInt(request.getParameter("tableId"));
            boolean success = adminService.generateTableQRCode(tableId);
            
            result.put("success", success);
            result.put("message", success ? "QR 코드가 생성되었습니다." : "QR 코드 생성에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "QR 코드 생성 중 오류가 발생했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }

    /**
     * 테이블 정보 조회
     */
    public ModelAndView getTableInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int tableId = Integer.parseInt(request.getParameter("tableId"));
            Map<String, Object> table = adminService.getTableInfo(tableId);
            
            if (table != null) {
                result.put("success", true);
                result.put("data", table);
            } else {
                result.put("success", false);
                result.put("message", "테이블 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "테이블 정보를 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }

    /**
     * 테이블별 QR 코드 조회
     */
    public ModelAndView getQRCodeByTableId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            int tableId = Integer.parseInt(request.getParameter("tableId"));
            Map<String, Object> qrCode = adminService.getQRCodeByTableId(tableId);
            
            if (qrCode != null) {
                result.put("success", true);
                result.put("data", qrCode);
            } else {
                result.put("success", false);
                result.put("message", "QR 코드 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "QR 코드 정보를 불러오는데 실패했습니다.");
        }
        
        out.print(gson.toJson(result));
        out.flush();
        
        return null;
    }
}
