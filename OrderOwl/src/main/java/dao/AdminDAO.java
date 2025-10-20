package dao;

import dto.*;
import util.DbUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import dto.AdminDTO.*;

/**
 * 관리자 DAO - 데이터베이스 접근
 * Properties 파일에서 쿼리를 로드하여 사용
 */
public class AdminDAO {
    
    private Properties queries;
    
    /**
     * 생성자 - Properties 파일 로드
     */
    public AdminDAO() {
        queries = new Properties();
        loadQueries();
    }
    
    /**
     * Properties 파일에서 쿼리 로드
     */
    private void loadQueries() {
        InputStream is = null;
        
        try {
            // 방법 1: 클래스패스에서 로드 시도 (dbQuery.properties로 변경)
            is = getClass().getClassLoader().getResourceAsStream("dbQuery.properties");
            
            // 방법 2: 실패하면 프로젝트 resources 폴더에서 로드
            if (is == null) {
                File file = new File("resources/dbQuery.properties");
                if (file.exists()) {
                    is = new FileInputStream(file);
                    System.out.println("📂 파일 경로에서 로드: " + file.getAbsolutePath());
                }
            }
            
            // 방법 3: OrderOwl/resources에서 로드
            if (is == null) {
                File file = new File("OrderOwl/resources/dbQuery.properties");
                if (file.exists()) {
                    is = new FileInputStream(file);
                    System.out.println("📂 파일 경로에서 로드: " + file.getAbsolutePath());
                }
            }
            
            // 방법 4: 상대 경로 ../resources
            if (is == null) {
                File file = new File("../OrderOwl/resources/dbQuery.properties");
                if (file.exists()) {
                    is = new FileInputStream(file);
                    System.out.println("📂 파일 경로에서 로드: " + file.getAbsolutePath());
                }
            }
            
            // 방법 5: src/main/resources (Maven/Gradle 구조)
            if (is == null) {
                File file = new File("src/main/resources/dbQuery.properties");
                if (file.exists()) {
                    is = new FileInputStream(file);
                    System.out.println("📂 파일 경로에서 로드: " + file.getAbsolutePath());
                }
            }
            
            if (is == null) {
                throw new IOException("dbQuery.properties 파일을 찾을 수 없습니다.");
            }
            
            queries.load(is);
            System.out.println("✅ AdminDAO 쿼리 로드 완료: " + queries.size() + "개");
            
            // 디버그: 로드된 모든 키 출력
            System.out.println("📋 로드된 쿼리 키: " + queries.keySet());
            
        } catch (IOException e) {
            System.err.println("❌ AdminDAO 쿼리 로드 실패!");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 쿼리 가져오기
     */
    private String getQuery(String key) {
        String query = queries.getProperty(key);
        if (query == null) {
            System.err.println("❌ 쿼리를 찾을 수 없습니다: " + key);
            System.err.println("📋 사용 가능한 쿼리 키: " + queries.keySet());
            throw new RuntimeException("쿼리를 찾을 수 없습니다: " + key);
        }
        return query;
    }
    
    // ==================== 매장 관리 ====================
    
    /**
     * 사업자번호 중복 확인
     */
    public boolean existsByBusinessNumber(String businessNumber) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.existsByBusinessNumber"));
            pstmt.setString(1, businessNumber);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return false;
    }
    
    /**
     * 매장 등록
     */
    public int insertStore(StoreDTO store) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.insert"));
            pstmt.setLong(1, store.getOwnerId());
            pstmt.setString(2, store.getStoreName());
            pstmt.setString(3, store.getBusinessNumber());
            pstmt.setString(4, store.getAddress());
            pstmt.setString(5, store.getPhoneNumber());
            pstmt.setString(6, store.getStatus());
            pstmt.setBoolean(7, store.isBusinessVerified());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * 매장 업데이트
     */
    public int updateStore(StoreDTO store) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.update"));
            pstmt.setString(1, store.getStoreName());
            pstmt.setString(2, store.getAddress());
            pstmt.setString(3, store.getPhoneNumber());
            pstmt.setString(4, store.getStatus());
            pstmt.setBoolean(5, store.isBusinessVerified());
            pstmt.setLong(6, store.getStoreId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * ID로 매장 조회
     */
    public StoreDTO selectStoreById(long storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.selectById"));
            pstmt.setLong(1, storeId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                StoreDTO store = new StoreDTO();
                store.setStoreId(rs.getLong("store_id"));
                store.setOwnerId(rs.getLong("owner_id"));
                store.setStoreName(rs.getString("store_name"));
                store.setBusinessNumber(rs.getString("business_number"));
                store.setAddress(rs.getString("address"));
                store.setPhoneNumber(rs.getString("phone_number"));
                store.setStatus(rs.getString("status"));
                store.setBusinessVerified(rs.getBoolean("business_verified"));
                return store;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 매장 상태 변경
     */
    public int updateStoreStatus(long storeId, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.updateStatus"));
            pstmt.setString(1, status);
            pstmt.setLong(2, storeId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * ✅ 매장 완전 삭제 (하드 삭제)
     */
    public int hardDeleteStore(long storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String query = "DELETE FROM Store WHERE store_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, storeId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * 대기 중인 주문 개수
     */
    public int countPendingOrders(long storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.countPendingOrders"));
            pstmt.setLong(1, storeId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return 0;
    }
    
    /**
     * QR 코드 경로 업데이트
     */
    public void updateStoreQR(long storeId, String qrPath) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.updateQR"));
            pstmt.setString(1, qrPath);
            pstmt.setLong(2, storeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 매장 메뉴 개수
     */
    public int countStoreMenus(long storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.countMenus"));
            pstmt.setLong(1, storeId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return 0;
    }
    
    /**
     * 매장 주문 개수
     */
    public int countStoreOrders(long storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.countOrders"));
            pstmt.setLong(1, storeId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return 0;
    }
    
    /**
     * 매장 총 매출
     */
    public long sumStoreSales(long storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.sumSales"));
            pstmt.setLong(1, storeId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return 0;
    }
    
    /**
     * 매장 존재 여부 확인
     */
    public boolean existsStoreById(long storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.existsById"));
            pstmt.setLong(1, storeId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return false;
    }
    
    /**
     * 전체 매장 목록 조회 (StoreRequest도 포함)
     */
    public List<StoreDTO> selectAllStores() {
        List<StoreDTO> stores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("store.selectAll"));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                StoreDTO store = new StoreDTO();
                store.setStoreId(rs.getLong("store_id"));
                store.setOwnerId(rs.getLong("owner_id"));
                store.setStoreName(rs.getString("store_name"));
                store.setBusinessNumber(rs.getString("business_number"));
                store.setAddress(rs.getString("address"));
                store.setPhoneNumber(rs.getString("phone_number"));
                store.setStatus(rs.getString("status"));
                store.setBusinessVerified(rs.getBoolean("business_verified"));
                stores.add(store);
            }
            
            // ✅ PENDING 상태의 StoreRequest도 추가
            List<StoreRequestDTO> pendingRequests = selectPendingStoreRequests();
            for (StoreRequestDTO request : pendingRequests) {
                StoreDTO store = new StoreDTO();
                // requestId를 음수로 저장하여 실제 storeId와 구분
                store.setStoreId(-request.getRequestId()); 
                store.setOwnerId(request.getOwnerId());
                store.setStoreName(request.getStoreName());
                store.setBusinessNumber(request.getBusinessNumber());
                store.setAddress(request.getAddress());
                store.setPhoneNumber(request.getPhoneNumber());
                store.setStatus("PENDING"); // 대기중 상태
                store.setBusinessVerified(false);
                store.setMenuCount(0);
                store.setTotalOrders(0);
                store.setTotalSales(0);
                stores.add(store);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return stores;
    }
    
    /**
     * ✅ 삭제 대기 중인 매장 목록 조회
     */
    public List<StoreDTO> selectDeletePendingStores() {
        List<StoreDTO> stores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String query = "SELECT * FROM Store WHERE status = 'DELETE_PENDING' ORDER BY store_id DESC";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                StoreDTO store = new StoreDTO();
                store.setStoreId(rs.getLong("store_id"));
                store.setOwnerId(rs.getLong("owner_id"));
                store.setStoreName(rs.getString("store_name"));
                store.setBusinessNumber(rs.getString("business_number"));
                store.setAddress(rs.getString("address"));
                store.setPhoneNumber(rs.getString("phone_number"));
                store.setStatus(rs.getString("status"));
                store.setBusinessVerified(rs.getBoolean("business_verified"));
                stores.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return stores;
    }
    
    // ==================== 유저 관리 ====================
    
    /**
     * ID로 유저 조회
     */
    public UserDTO selectUserById(long userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("user.selectById"));
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 전체 유저 목록 조회
     */
    public List<UserDTO> selectAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("user.selectAll"));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return users;
    }
    
    /**
     * 전체 유저 수 조회
     */
    public int countAllUsers() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("user.countAll"));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return 0;
    }
    
    /**
     * 유저 상태 변경
     */
    public int updateUserStatus(long userId, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("user.updateStatus"));
            pstmt.setString(1, status);
            pstmt.setLong(2, userId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * 강제 탈퇴 로그 기록
     */
    public void insertForceDeleteLog(long userId, String reason) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("user.insertForceDeleteLog"));
            pstmt.setLong(1, userId);
            pstmt.setString(2, reason);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    // ==================== 메뉴 관리 ====================
    
    /**
     * 메뉴 추가
     */
    public int insertMenu(MenuDTO menu) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("menu.insert"));
            pstmt.setLong(1, menu.getStoreId());
            pstmt.setString(2, menu.getMenuName());
            pstmt.setInt(3, menu.getPrice());
            pstmt.setString(4, menu.getCategory());
            pstmt.setString(5, menu.getDescription());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * 메뉴 업데이트
     */
    public int updateMenu(MenuDTO menu) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("menu.update"));
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2, menu.getPrice());
            pstmt.setString(3, menu.getCategory());
            pstmt.setString(4, menu.getDescription());
            pstmt.setLong(5, menu.getMenuId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * 메뉴 삭제
     */
    public int deleteMenu(long menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("menu.delete"));
            pstmt.setLong(1, menuId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    // ==================== 메뉴 요청 관리 ====================
    
    /**
     * ID로 메뉴 요청 조회
     */
    public MenuRequestDTO selectMenuRequestById(long requestId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("menuRequest.selectById"));
            pstmt.setLong(1, requestId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                MenuRequestDTO request = new MenuRequestDTO();
                request.setRequestId(rs.getLong("request_id"));
                request.setMenuId(rs.getLong("menu_id"));
                request.setStoreId(rs.getLong("store_id"));
                request.setOwnerId(rs.getLong("owner_id"));
                request.setMenuName(rs.getString("menu_name"));
                request.setPrice(rs.getInt("price"));
                request.setCategory(rs.getString("category"));
                request.setDescription(rs.getString("description"));
                request.setStatus(rs.getString("status"));
                return request;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 대기 중인 메뉴 요청 목록 조회
     */
    public List<MenuRequestDTO> selectPendingMenuRequests() {
        List<MenuRequestDTO> requests = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("menuRequest.selectPending"));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MenuRequestDTO request = new MenuRequestDTO();
                request.setRequestId(rs.getLong("request_id"));
                request.setMenuId(rs.getLong("menu_id"));
                request.setStoreId(rs.getLong("store_id"));
                request.setOwnerId(rs.getLong("owner_id"));
                request.setMenuName(rs.getString("menu_name"));
                request.setPrice(rs.getInt("price"));
                request.setCategory(rs.getString("category"));
                request.setDescription(rs.getString("description"));
                request.setStatus(rs.getString("status"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return requests;
    }
    
    /**
     * 메뉴 요청 상태 변경
     */
    public int approveMenuRequest(long requestId, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("menuRequest.updateStatus"));
            pstmt.setString(1, status);
            pstmt.setLong(2, requestId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * 메뉴가 활성 주문에 포함되어 있는지 확인
     */
    public boolean hasActiveOrdersWithMenu(long menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("menuRequest.hasActiveOrders"));
            pstmt.setLong(1, menuId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return false;
    }
    
    // ==================== 매장 요청 관리 ====================
    
    /**
     * ID로 매장 요청 조회
     */
    public StoreRequestDTO selectStoreRequestById(long requestId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("storeRequest.selectById"));
            pstmt.setLong(1, requestId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                StoreRequestDTO request = new StoreRequestDTO();
                request.setRequestId(rs.getLong("request_id"));
                request.setStoreId(rs.getLong("store_id"));
                request.setOwnerId(rs.getLong("owner_id"));
                request.setStoreName(rs.getString("store_name"));
                request.setBusinessNumber(rs.getString("business_number"));
                request.setAddress(rs.getString("address"));
                request.setPhoneNumber(rs.getString("phone_number"));
                request.setStatus(rs.getString("status"));
                return request;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 매장 요청 상태 변경
     */
    public int approveStoreRequest(long requestId, String status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("storeRequest.updateStatus"));
            pstmt.setString(1, status);
            pstmt.setLong(2, requestId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
        return 0;
    }
    
    /**
     * 대기 중인 매장 요청 목록 조회
     */
    public List<StoreRequestDTO> selectPendingStoreRequests() {
        List<StoreRequestDTO> requests = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("storeRequest.selectPending"));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                StoreRequestDTO request = new StoreRequestDTO();
                request.setRequestId(rs.getLong("request_id"));
                request.setStoreId(rs.getLong("store_id"));
                request.setOwnerId(rs.getLong("owner_id"));
                request.setStoreName(rs.getString("store_name"));
                request.setBusinessNumber(rs.getString("business_number"));
                request.setAddress(rs.getString("address"));
                request.setPhoneNumber(rs.getString("phone_number"));
                request.setStatus(rs.getString("status"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return requests;
    }
    
    // ==================== 매출 정보 ====================
    
    /**
     * 기간별 매출 합계
     */
    public long sumSalesByPeriod(long storeId, LocalDate startDate, LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("sales.sumByPeriod"));
            pstmt.setLong(1, storeId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return 0;
    }
    
    /**
     * 기간별 주문 개수
     */
    public int countOrdersByPeriod(long storeId, LocalDate startDate, LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("sales.countOrdersByPeriod"));
            pstmt.setLong(1, storeId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return 0;
    }
    
    /**
     * 일별 매출 조회
     */
    public List<DailySalesDTO> selectDailySales(long storeId, LocalDate startDate, LocalDate endDate) {
        List<DailySalesDTO> dailySales = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("sales.selectDailySales"));
            pstmt.setLong(1, storeId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                DailySalesDTO sales = new DailySalesDTO();
                sales.setSaleDate(rs.getDate("sale_date").toLocalDate());
                sales.setDailyTotal(rs.getLong("daily_total"));
                dailySales.add(sales);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return dailySales;
    }
    
    /**
     * 메뉴별 매출 조회
     */
    public List<MenuSalesDTO> selectMenuSales(long storeId, LocalDate startDate, LocalDate endDate) {
        List<MenuSalesDTO> menuSales = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            pstmt = conn.prepareStatement(getQuery("sales.selectMenuSales"));
            pstmt.setLong(1, storeId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MenuSalesDTO sales = new MenuSalesDTO();
                sales.setMenuName(rs.getString("menu_name"));
                sales.setTotalQuantity(rs.getInt("total_quantity"));
                sales.setTotalSales(rs.getLong("total_sales"));
                menuSales.add(sales);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return menuSales;
    }
    
    // ==================== 대시보드 통계 ====================
    
    /**
     * 대시보드 통계 데이터 조회
     */
    public Map<String, Integer> getDashboardStats() {
        Map<String, Integer> stats = new HashMap<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            
            // 전체 매장 수
            pstmt = conn.prepareStatement(getQuery("dashboard.totalStores"));
            rs = pstmt.executeQuery();
            if (rs.next()) stats.put("totalStores", rs.getInt(1));
            
            // 전체 유저 수
            pstmt = conn.prepareStatement(getQuery("dashboard.totalUsers"));
            rs = pstmt.executeQuery();
            if (rs.next()) stats.put("totalUsers", rs.getInt(1));
            
            // 대기 중인 매장 요청 수
            pstmt = conn.prepareStatement(getQuery("dashboard.pendingStoreRequests"));
            rs = pstmt.executeQuery();
            if (rs.next()) stats.put("pendingStoreRequests", rs.getInt(1));
            
            // 대기 중인 메뉴 요청 수
            pstmt = conn.prepareStatement(getQuery("dashboard.pendingMenuRequests"));
            rs = pstmt.executeQuery();
            if (rs.next()) stats.put("pendingMenuRequests", rs.getInt(1));
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return stats;
    }
}