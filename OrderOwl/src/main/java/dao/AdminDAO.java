package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import dto.MenuDTO;
import dto.StoreDTO;
import dto.UserDTO;
import util.DbUtil;

/**
 * 관리자 DAO - 데이터베이스 접근 계층
 * dbQuery.properties에서 SQL 쿼리 관리
 */
public class AdminDAO {
    
    private Properties queryProperties;
    
    public AdminDAO() {
        queryProperties = loadQueryProperties();
    }
    
    /**
     * SQL 쿼리 프로퍼티 파일 로드
     */
    private Properties loadQueryProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("dbQuery.properties")) {
            if (input == null) {
                System.err.println("dbQuery.properties 파일을 찾을 수 없습니다.");
                return props;
            }
            props.load(input);
        } catch (IOException e) {
            System.err.println("dbQuery.properties 파일 로드 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return props;
    }
    
    private String getQuery(String key) {
        return queryProperties.getProperty(key);
    }

    // ==================== 매장 관리 ====================

    /**
     * 매장 추가
     */
    public int insertStore(StoreDTO store) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.store.insert");
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
            DbUtil.dbClose(pstmt, conn);
        }
    }

    /**
     * 매장 삭제
     */
    public int deleteStore(int storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.store.delete");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(pstmt, conn);
        }
    }

    /**
     * 매장 정보 조회
     */
    public StoreDTO selectStoreById(int storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.store.selectById");
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
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return null;
    }

    /**
     * 전체 매장 목록 조회
     */
    public List<StoreDTO> selectAllStores() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<StoreDTO> stores = new ArrayList<>();
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.store.selectAll");
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
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                );
                stores.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return stores;
    }

    /**
     * 매장 정보 수정
     */
    public int updateStore(StoreDTO store) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.store.update");
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
            DbUtil.dbClose(pstmt, conn);
        }
    }

    /**
     * 업주별 매장 목록 조회
     */
    public List<StoreDTO> selectStoresByOwnerId(int ownerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<StoreDTO> stores = new ArrayList<>();
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.store.selectByOwnerId");
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
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                );
                stores.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return stores;
    }

    /**
     * 진행 중인 주문 수 조회
     */
    public int countPendingOrders(int storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.order.countPending");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return 0;
    }

    // ==================== 메뉴 관리 ====================

    /**
     * 매장별 메뉴 목록 조회
     */
    public List<MenuDTO> selectMenusByStoreId(int storeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MenuDTO> menus = new ArrayList<>();
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.menu.selectByStoreId");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                LocalTime closeTime = null;
                if (rs.getTime("close_time") != null) {
                    closeTime = rs.getTime("close_time").toLocalTime();
                }
                
                MenuDTO menu = new MenuDTO(
                    rs.getInt("menu_id"),
                    rs.getInt("store_id"),
                    rs.getString("menu_name"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getString("img_src"),
                    rs.getInt("category1_code"),
                    rs.getInt("category2_code"),
                    rs.getString("check_rec"),
                    rs.getString("order_request"),
                    closeTime,
                    rs.getString("sold_out")
                );
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return menus;
    }

    /**
     * 메뉴 추가
     */
    public int insertMenu(MenuDTO menu) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.menu.insert");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menu.getStoreId());
            pstmt.setString(2, menu.getMenuName());
            pstmt.setInt(3, menu.getPrice());
            pstmt.setString(4, menu.getDescription());
            pstmt.setString(5, menu.getImgSrc());
            pstmt.setInt(6, menu.getCategory1Code());
            pstmt.setInt(7, menu.getCategory2Code());
            pstmt.setString(8, menu.getCheckRec());
            pstmt.setString(9, menu.getOrderRequest());
            
            if (menu.getCloseTime() != null) {
                pstmt.setTime(10, java.sql.Time.valueOf(menu.getCloseTime()));
            } else {
                pstmt.setNull(10, java.sql.Types.TIME);
            }
            
            pstmt.setString(11, menu.getSoldOut());
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(pstmt, conn);
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
            String sql = getQuery("admin.menu.update");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, menu.getMenuName());
            pstmt.setInt(2, menu.getPrice());
            pstmt.setString(3, menu.getDescription());
            pstmt.setString(4, menu.getImgSrc());
            pstmt.setInt(5, menu.getCategory1Code());
            pstmt.setString(6, menu.getCheckRec());
            pstmt.setString(7, menu.getOrderRequest());
            
            if (menu.getCloseTime() != null) {
                pstmt.setTime(8, java.sql.Time.valueOf(menu.getCloseTime()));
            } else {
                pstmt.setNull(8, java.sql.Types.TIME);
            }
            
            pstmt.setString(9, menu.getSoldOut());
            pstmt.setInt(10, menu.getMenuId());
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(pstmt, conn);
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
            String sql = getQuery("admin.menu.delete");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menuId);
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(pstmt, conn);
        }
    }

    /**
     * 메뉴 정보 조회
     */
    public MenuDTO selectMenuById(int menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.menu.selectById");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menuId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                LocalTime closeTime = null;
                if (rs.getTime("close_time") != null) {
                    closeTime = rs.getTime("close_time").toLocalTime();
                }
                
                return new MenuDTO(
                    rs.getInt("menu_id"),
                    rs.getInt("store_id"),
                    rs.getString("menu_name"),
                    rs.getInt("price"),
                    rs.getString("description"),
                    rs.getString("img_src"),
                    rs.getInt("category1_code"),
                    rs.getInt("category2_code"),
                    rs.getString("check_rec"),
                    rs.getString("order_request"),
                    closeTime,
                    rs.getString("sold_out")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return null;
    }

    /**
     * 메뉴가 활성 주문에 포함되어 있는지 확인
     */
    public boolean isMenuInActiveOrders(int menuId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.menu.checkActiveOrders");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, menuId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return false;
    }

    // ==================== 유저 관리 ====================

    /**
     * 전체 유저 목록 조회
     */
    public List<UserDTO> selectAllUsers() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserDTO> users = new ArrayList<>();
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.user.selectAll");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                UserDTO user = new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return users;
    }

    /**
     * 유저 정보 조회
     */
    public UserDTO selectUserById(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.user.selectById");
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
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return null;
    }

    /**
     * 유저 삭제
     */
    public int deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.user.delete");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(pstmt, conn);
        }
    }

    // ==================== 매출 정보 ====================

    /**
     * 매장 총 매출 조회
     */
    public long sumStoreSales(int storeId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.sales.sumByPeriod");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            pstmt.setDate(3, java.sql.Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return 0;
    }

    /**
     * 매장 총 주문 수 조회
     */
    public int countStoreOrders(int storeId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.order.countByPeriod");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            pstmt.setDate(3, java.sql.Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return 0;
    }

    /**
     * 일별 매출 조회
     */
    public List<Map<String, Object>> selectDailySales(int storeId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> dailySales = new ArrayList<>();
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.sales.dailySales");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            pstmt.setDate(3, java.sql.Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> daily = new HashMap<>();
                daily.put("saleDate", rs.getDate("sale_date").toString());
                daily.put("dailyTotal", rs.getLong("daily_total"));
                dailySales.add(daily);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return dailySales;
    }

    /**
     * 메뉴별 매출 조회
     */
    public List<Map<String, Object>> selectMenuSales(int storeId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> menuSales = new ArrayList<>();
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.sales.menuSales");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            pstmt.setDate(3, java.sql.Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> menu = new HashMap<>();
                menu.put("menuName", rs.getString("menu_name"));
                menu.put("totalQuantity", rs.getInt("total_quantity"));
                menu.put("totalSales", rs.getLong("total_sales"));
                menuSales.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
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
            String sql = getQuery("admin.table.selectStoreTables");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> table = new HashMap<>();
                table.put("tableId", rs.getInt("table_id"));
                table.put("tableNo", rs.getString("table_no"));
                table.put("storeId", rs.getInt("store_id"));
                table.put("storeName", rs.getString("store_name"));
                table.put("qrcodeId", rs.getInt("qrcode_id"));
                table.put("qrcodeData", rs.getString("qrcode_data"));
                table.put("qrImgSrc", rs.getString("qr_img_src"));
                table.put("createdAt", rs.getTimestamp("created_at"));
                tables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
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
            String sql = getQuery("admin.table.upsertQR");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tableId);
            pstmt.setString(2, qrcodeData);
            pstmt.setString(3, qrImgSrc);
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(pstmt, conn);
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
            String sql = getQuery("admin.table.selectById");
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
            DbUtil.dbClose(rs, pstmt, conn);
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
            String sql = getQuery("admin.table.selectQRByTableId");
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
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return null;
    }

    /**
     * 테이블 QR 코드 삭제
     */
    public int deleteTableQRCode(int tableId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.table.deleteQR");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tableId);
            
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DbUtil.dbClose(pstmt, conn);
        }
    }

    /**
     * 테이블 QR 코드 존재 여부 확인
     */
    public boolean existsQRCode(int tableId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtil.getConnection();
            String sql = getQuery("admin.table.existsQR");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tableId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.dbClose(rs, pstmt, conn);
        }
        return false;
    }
}