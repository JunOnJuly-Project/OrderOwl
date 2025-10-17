package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * DB 연동을 위한 로드, 연결, 닫기 기능 클래스
 */
public class DbUtil {
    static DataSource ds;
    
    /**
     * DataSource 로드
     */
    static {
        try {
            Context initContext = new InitialContext();
            ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/orderowl");
            
            if (ds != null) {
                System.out.println("✅ DataSource 로드 성공!");
            } else {
                System.err.println("❌ DataSource가 null입니다!");
            }
            
        } catch (Exception e) {
            System.err.println("❌ DataSource 초기화 실패!");
            System.err.println("확인사항:");
            System.err.println("1. context.xml에 jdbc/orderowl 리소스가 정의되어 있는가?");
            System.err.println("2. MySQL 서버가 실행 중인가?");
            System.err.println("3. OrderOwl 데이터베이스가 생성되었는가?");
            System.err.println("4. MySQL Connector JAR가 WEB-INF/lib에 있는가?");
            e.printStackTrace();
        }
    }
    
    /**
     * DB 연결
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    /**
     * 리소스 닫기 - SELECT 쿼리용
     */
    public static void dbClose(Connection con, Statement st, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            dbClose(con, st);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 리소스 닫기 - DML/DDL 쿼리용
     */
    public static void dbClose(Connection con, Statement st) {
        try {
            if (st != null) st.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}