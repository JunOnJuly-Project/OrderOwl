package listener;

import controller.common.Controller;
import controller.admin.AdminController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

/**
 * 핸들러 매핑 리스너
 */
@WebListener
public class HandlerMappingListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("\n====================================");
        System.out.println("🦉 OrderOwl 애플리케이션 시작");
        System.out.println("====================================");
        
        ServletContext ctx = sce.getServletContext();
        
        // 컨트롤러 매핑
        Map<String, Controller> mappings = new HashMap<>();
        
        // AdminController 등록 - Controller 인터페이스로 캐스팅
        Controller adminController = new AdminController();
        mappings.put("admin", adminController);
        
        ctx.setAttribute("map", mappings);
        
        System.out.println("\n📋 등록된 Controller:");
        for (String key : mappings.keySet()) {
            System.out.println("   - " + key + " : " + mappings.get(key).getClass().getSimpleName());
        }
        
        // DB 연결 테스트 (기존 코드 유지)
        testDatabaseConnection();
        
        System.out.println("====================================");
        System.out.println("🦉 초기화 완료");
        System.out.println("====================================\n");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\n====================================");
        System.out.println("🦉 OrderOwl 애플리케이션 종료");
        System.out.println("====================================\n");
    }
    
    /**
     * 데이터베이스 연결 테스트
     */
    private void testDatabaseConnection() {
        System.out.println("\n🔍 DB 연결 테스트 시작...\n");
        
        try {
            java.sql.Connection conn = util.DbUtil.getConnection();
            
            if (conn != null) {
                System.out.println("✅ DataSource 로드 성공!");
                System.out.println("✅ DB 연결 성공!");
                
                java.sql.DatabaseMetaData meta = conn.getMetaData();
                System.out.println("   - DB URL: " + meta.getURL());
                System.out.println("   - DB 이름: " + conn.getCatalog());
                System.out.println("   - 사용자: " + meta.getUserName());
                System.out.println("   - JDBC Driver: " + meta.getDriverName());
                
                // 테이블 목록 조회
                System.out.println("\n📋 테이블 목록:");
                java.sql.ResultSet rs = meta.getTables(null, null, "%", new String[]{"TABLE"});
                int tableCount = 0;
                while (rs.next()) {
                    tableCount++;
                    System.out.println("   " + tableCount + ". " + rs.getString("TABLE_NAME"));
                }
                System.out.println("\n✅ 총 " + tableCount + "개 테이블 확인");
                
                // User 테이블 샘플 데이터 조회
                try {
                    java.sql.Statement stmt = conn.createStatement();
                    java.sql.ResultSet userRs = stmt.executeQuery("SELECT * FROM User LIMIT 5");
                    System.out.println("\n👤 User 테이블:");
                    while (userRs.next()) {
                        System.out.println("   - ID:" + userRs.getLong("user_id") + 
                                         " | " + userRs.getString("name") + 
                                         " | " + userRs.getString("email") + 
                                         " | " + userRs.getString("role"));
                    }
                    userRs.close();
                    stmt.close();
                } catch (Exception e) {
                    System.out.println("⚠️  User 테이블 조회 실패 (테이블이 없을 수 있음)");
                }
                
                // Store 테이블 샘플 데이터 조회
                try {
                    java.sql.Statement stmt = conn.createStatement();
                    java.sql.ResultSet storeRs = stmt.executeQuery("SELECT * FROM Store LIMIT 5");
                    System.out.println("\n🏪 Store 테이블:");
                    while (storeRs.next()) {
                        System.out.println("   - ID:" + storeRs.getLong("store_id") + 
                                         " | " + storeRs.getString("store_name") + 
                                         " | " + storeRs.getString("address") + 
                                         " | " + storeRs.getString("phone_number"));
                    }
                    storeRs.close();
                    stmt.close();
                } catch (Exception e) {
                    System.out.println("⚠️  Store 테이블 조회 실패 (테이블이 없을 수 있음)");
                }
                
                // Menu 테이블 샘플 데이터 조회
                try {
                    java.sql.Statement stmt = conn.createStatement();
                    java.sql.ResultSet menuRs = stmt.executeQuery("SELECT * FROM Menu LIMIT 5");
                    System.out.println("\n🍽️  Menu 테이블:");
                    while (menuRs.next()) {
                        System.out.println("   - ID:" + menuRs.getLong("menu_id") + 
                                         " | " + menuRs.getString("menu_name") + 
                                         " | " + menuRs.getLong("price") + "원");
                    }
                    menuRs.close();
                    stmt.close();
                } catch (Exception e) {
                    System.out.println("⚠️  Menu 테이블 조회 실패 (테이블이 없을 수 있음)");
                }
                
                rs.close();
                conn.close();
            } else {
                System.out.println("❌ DB 연결 실패!");
            }
        } catch (Exception e) {
            System.out.println("❌ DB 연결 테스트 중 오류 발생!");
            e.printStackTrace();
        }
    }
}