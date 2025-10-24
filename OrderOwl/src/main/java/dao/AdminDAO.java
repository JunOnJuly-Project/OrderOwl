package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;

import dto.StoreDTO;
import dto.UserDTO;
import dto.MenuDTO;
import util.DbUtil;

/**
 * 관리자 DAO - 데이터베이스 접근
 * OrderOwl.sql 스키마 기준 리팩토링
 * SQL 쿼리 properties 파일에서 관리
 */
public class AdminDAO {
    
    private Properties queryProps;
    
    public AdminDAO() {
        queryProps = loadQueryProperties();
    }
    
    /**
     * 쿼리 프로퍼티 파일 로드
     */
    private Properties loadQueryProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("dbQuery.properties")) {
            if (input == null) {
                System.out.println("⚠️ dbQuery.properties 파일을 찾을 수 없습니다. 기본 SQL을 사용합니다.");
                return props;
            }
            props.load(input);
            System.out.println("✅ dbQuery.properties 로드 성공!");
        } catch (Exception ex) {
            System.out.println("❌ dbQuery.properties 로드 실패: " + ex.getMessage());
            ex.printStackTrace();
        }
        return props;
    }
    
    private String getQuery(String key) {
        String query = queryProps.getProperty(key);
        if (query == null) {
            System.err.println("⚠️ 쿼리 키를 찾을 수 없음: " + key);
        }
        return query;
    }
    
    // ==================== 매장 관리 ====================
    
    /**
     * 매장 등록
     */
    public int insertStore(StoreDTO store) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("store.insert");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, store.getOwnerId());
            pstmt.setString(2, store.getStoreName());
            pstmt.setString(3, store.getAddress());
            pstmt.setString(4, store.getRegion());
            pstmt.setString(5, store.getPhoneNumber());
            pstmt.setString(6, store.getDescription());
            pstmt.setString(7, store.getImgSrc());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 매장 수정
     */
    public int updateStore(StoreDTO store) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("store.update");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, store.getStoreName());
            pstmt.setString(2, store.getAddress());
            pstmt.setString(3, store.getRegion());
            pstmt.setString(4, store.getPhoneNumber());
            pstmt.setString(5, store.getDescription());
            pstmt.setString(6, store.getImgSrc());
            pstmt.setInt(7, store.getStoreId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 매장 삭제 (CASCADE로 관련 데이터 자동 삭제)
     */
    public int deleteStore(int storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("store.delete");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 매장 ID로 조회
     */
    public StoreDTO selectStoreById(int storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("store.selectById");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new StoreDTO(
                    rs.getInt("store_id"),
                    rs.getInt("owner_id"),
                    rs.getString("store_name"),
                    rs.getString("address"),
                    rs.getString("region"),
                    rs.getString("phone_number"),
                    rs.getString("description"),
                    rs.getString("img_src"),
                    rs.getTimestamp("created_at") != null ? 
                        rs.getTimestamp("created_at").toLocalDateTime() : null,
                    null // menu list는 별도 조회
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 전체 매장 목록 조회
     */
    public List<StoreDTO> selectAllStores() {
        List<StoreDTO> stores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("store.selectAll");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                StoreDTO store = new StoreDTO(
                    rs.getInt("store_id"),
                    rs.getInt("owner_id"),
                    rs.getString("store_name"),
                    rs.getString("address"),
                    rs.getString("region"),
                    rs.getString("phone_number"),
                    rs.getString("description"),
                    rs.getString("img_src"),
                    rs.getTimestamp("created_at") != null ? 
                        rs.getTimestamp("created_at").toLocalDateTime() : null,
                    null
                );
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
     * 업주 ID로 매장 목록 조회
     */
    public List<StoreDTO> selectStoresByOwnerId(int ownerId) {
        List<StoreDTO> stores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("store.selectByOwnerId");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ownerId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                StoreDTO store = new StoreDTO(
                    rs.getInt("store_id"),
                    rs.getInt("owner_id"),
                    rs.getString("store_name"),
                    rs.getString("address"),
                    rs.getString("region"),
                    rs.getString("phone_number"),
                    rs.getString("description"),
                    rs.getString("img_src"),
                    rs.getTimestamp("created_at") != null ? 
                        rs.getTimestamp("created_at").toLocalDateTime() : null,
                    null
                );
                stores.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return stores;
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
            String sql = getQuery("menu.insert");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menu.getStoreId());
            pstmt.setString(2, menu.getMenuName());
            pstmt.setInt(3, menu.getPrice());
            pstmt.setString(4, menu.getDescription());
            pstmt.setString(5, menu.getImgSrc());
            pstmt.setInt(6, menu.getCategory1Code());
            pstmt.setInt(7, menu.getCategory2Code());
            pstmt.setString(8, menu.getCheckRec() != null ? menu.getCheckRec() : "N");
            pstmt.setString(9, menu.getOrderRequest());
            pstmt.setString(10, menu.getSoldOut() != null ? menu.getSoldOut() : "N");
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 메뉴 수정
     */
    public int updateMenu(MenuDTO menu) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("menu.update");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2, menu.getPrice());
            pstmt.setString(3, menu.getDescription());
            pstmt.setString(4, menu.getImgSrc());
            
            // category1_code - 0이면 NULL로 설정
            if (menu.getCategory1Code() > 0) {
                pstmt.setInt(5, menu.getCategory1Code());
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            // category2_code - 0이면 NULL로 설정
            if (menu.getCategory2Code() > 0) {
                pstmt.setInt(6, menu.getCategory2Code());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            
            pstmt.setString(7, menu.getCheckRec() != null ? menu.getCheckRec() : "N");
            pstmt.setString(8, menu.getOrderRequest());
            pstmt.setString(9, menu.getSoldOut() != null ? menu.getSoldOut() : "N");
            pstmt.setInt(10, menu.getMenuId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 메뉴 삭제
     */
    public int deleteMenu(int menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("menu.delete");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menuId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 메뉴 ID로 조회
     */
    public MenuDTO selectMenuById(int menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("menu.selectById");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menuId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setStoreId(rs.getInt("store_id"));
                menu.setMenuName(rs.getString("menu_name"));
                menu.setPrice(rs.getInt("price"));
                menu.setDescription(rs.getString("description"));
                menu.setImgSrc(rs.getString("img_src"));
                menu.setCategory1Code(rs.getInt("category1_code"));
                menu.setCategory2Code(rs.getInt("category2_code"));
                menu.setCheckRec(rs.getString("check_rec"));
                menu.setOrderRequest(rs.getString("order_request"));
                menu.setSoldOut(rs.getString("sold_out"));
                if (rs.getTime("close_time") != null) {
                    menu.setCloseTime(rs.getTime("close_time").toLocalTime());
                }
                return menu;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 매장별 메뉴 목록 조회
     */
    public List<MenuDTO> selectMenusByStoreId(int storeId) {
        List<MenuDTO> menus = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("menu.selectByStoreId");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setStoreId(rs.getInt("store_id"));
                menu.setMenuName(rs.getString("menu_name"));
                menu.setPrice(rs.getInt("price"));
                menu.setDescription(rs.getString("description"));
                menu.setImgSrc(rs.getString("img_src"));
                menu.setCategory1Code(rs.getInt("category1_code"));
                menu.setCategory2Code(rs.getInt("category2_code"));
                menu.setCheckRec(rs.getString("check_rec"));
                menu.setOrderRequest(rs.getString("order_request"));
                menu.setSoldOut(rs.getString("sold_out"));
                if (rs.getTime("close_time") != null) {
                    menu.setCloseTime(rs.getTime("close_time").toLocalTime());
                }
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return menus;
    }
    
    /**
     * 활성 주문에 포함된 메뉴인지 확인
     */
    public boolean isMenuInActiveOrders(int menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("menu.checkActiveOrders");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menuId);
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
    
    // ==================== 유저 관리 ====================
    
    /**
     * 유저 삭제
     */
    public int deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("user.delete");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 유저 ID로 조회
     */
    public UserDTO selectUserById(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("user.selectById");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at") != null ? 
                        rs.getTimestamp("created_at").toLocalDateTime() : null
                );
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
            String sql = getQuery("user.selectAll");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                UserDTO user = new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at") != null ? 
                        rs.getTimestamp("created_at").toLocalDateTime() : null
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return users;
    }
    
    // ==================== 주문/매출 관리 ====================
    
    /**
     * 진행 중인 주문 개수
     */
    public int countPendingOrders(int storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("order.countPending");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
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
     * 기간별 주문 개수
     */
    public int countStoreOrders(int storeId, LocalDate startDate, LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("order.countByPeriod");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
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
     * 기간별 매출 합계
     */
    public long sumStoreSales(int storeId, LocalDate startDate, LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("sales.sumByPeriod");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
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
     * 일별 매출 조회
     */
    public List<Map<String, Object>> selectDailySales(int storeId, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> dailySales = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("sales.dailySales");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> sales = new HashMap<>();
                sales.put("saleDate", rs.getDate("sale_date").toLocalDate());
                sales.put("dailyTotal", rs.getLong("daily_total"));
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
    public List<Map<String, Object>> selectMenuSales(int storeId, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> menuSales = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("sales.menuSales");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> sales = new HashMap<>();
                sales.put("menuName", rs.getString("menu_name"));
                sales.put("totalQuantity", rs.getInt("total_quantity"));
                sales.put("totalSales", rs.getLong("total_sales"));
                menuSales.add(sales);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return menuSales;
    }
    
    // ==================== 테이블별 QR 관리 ====================
    
    /**
     * 매장 테이블 목록 조회 (QR 정보 포함)
     */
    public List<Map<String, Object>> selectStoreTables(int storeId) {
        List<Map<String, Object>> tables = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("table.selectStoreTables");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> table = new HashMap<>();
                table.put("tableId", rs.getInt("table_id"));
                table.put("tableNo", rs.getString("table_no"));
                table.put("storeId", rs.getInt("store_id"));
                table.put("storeName", rs.getString("store_name"));
                table.put("qrcodeData", rs.getString("qrcode_data"));
                table.put("qrImgSrc", rs.getString("qr_img_src"));
                table.put("createdAt", rs.getTimestamp("created_at"));
                tables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return tables;
    }
    
    /**
     * 테이블별 QR 코드 생성/업데이트
     */
    public int upsertTableQRCode(int tableId, String qrcodeData, String qrImgSrc) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("table.upsertQR");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tableId);
            pstmt.setString(2, qrcodeData);
            pstmt.setString(3, qrImgSrc);
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(conn, pstmt);
        }
    }
    
    /**
     * 테이블 정보 조회
     */
    public Map<String, Object> selectTableById(int tableId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("table.selectById");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tableId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> table = new HashMap<>();
                table.put("tableId", rs.getInt("table_id"));
                table.put("tableNo", rs.getString("table_no"));
                table.put("storeId", rs.getInt("store_id"));
                table.put("storeName", rs.getString("store_name"));
                return table;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 테이블별 QR 코드 조회
     */
    public Map<String, Object> selectQRCodeByTableId(int tableId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("table.selectQRByTableId");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tableId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> qrCode = new HashMap<>();
                qrCode.put("qrcodeId", rs.getInt("qrcode_id"));
                qrCode.put("tableId", rs.getInt("table_id"));
                qrCode.put("tableNo", rs.getString("table_no"));
                qrCode.put("storeId", rs.getInt("store_id"));
                qrCode.put("storeName", rs.getString("store_name"));
                qrCode.put("qrcodeData", rs.getString("qrcode_data"));
                qrCode.put("qrImgSrc", rs.getString("qr_img_src"));
                qrCode.put("createdAt", rs.getTimestamp("created_at"));
                return qrCode;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(conn, pstmt, rs);
        }
        return null;
    }
}