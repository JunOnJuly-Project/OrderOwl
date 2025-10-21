package dao.user;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import dto.StoreDTO;
import util.DbUtil;

public class OrderDAOImpl implements OrderDAO {
	private Properties proFile = new Properties();
	
	
	public OrderDAOImpl() {
	
	try {
		
		InputStream is = getClass().getClassLoader().getResourceAsStream("dbQuery.properties");
		proFile.load(is);
		
		System.out.println("query.select = " +proFile.getProperty("query.order.selectBystoreId"));
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	
	}


	@Override
	public StoreDTO selectStoreById() throws SQLException {
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		StoreDTO store = null;
		String sql= proFile.getProperty("query.order.selectBystoreId");
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				store = 
				new StoreDTO(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
						rs.getString(8),null,null);
				
			}
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DbUtil.dbClose(rs, ps, con);
		}
		return store;

	}

	}

