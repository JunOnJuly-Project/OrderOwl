package dao.user;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import dto.OrderDetailDTO;
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
	public StoreDTO selectStoreById(int key) throws SQLException {
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		StoreDTO store = null;
		String sql= proFile.getProperty("query.order.selectBystoreId");
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, key);
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
	@Override
public void insertOrderDetail(List<OrderDetailDTO> list,int orderId) throws SQLException {
		
		Connection con=null;
		PreparedStatement ps=null;
	
		String sql= proFile.getProperty("query.order.insertOrderDetail");
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			for(int i=0 ; i < list.size() ; i++) {
				
		
			ps.setInt(1, orderId);
			ps.setInt(2, list.get(i).getMenuId());
			ps.setInt(3, list.get(i).getQuantity());
			ps.setInt(4, list.get(i).getPrice());
			ps.executeUpdate();
		
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DbUtil.dbClose( ps, con);
		}


	}



@Override
public String canOrderCheck(int tableNo) throws SQLException {
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	String res="";
	String sql= proFile.getProperty("query.order.canOrderCheck");
	try {
		con = DbUtil.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, tableNo);
		rs = ps.executeQuery();
		if(rs.next()) {
			
			res = rs.getString(1);
			
		}
		
	}
	
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		DbUtil.dbClose(rs, ps, con);
	}
	return res;

}


@Override
public int findLastOrderId(int tableNo) throws SQLException {


	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	int res=0;
	String sql= proFile.getProperty("query.order.findLastOrderId");
	try {
		con = DbUtil.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, tableNo);
		rs = ps.executeQuery();
		if(rs.next()) {
			
			res = rs.getInt(1);
			
			
		}
		
	
		
	}
	
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		DbUtil.dbClose(rs, ps, con);
	}
	return res;
}


@Override
public void insertNewOrder(int tableNo, int StoreId) throws SQLException {
	Connection con=null;
	PreparedStatement ps=null;

	String sql= proFile.getProperty("query.order.insertNewOrder");
	try {
		con = DbUtil.getConnection();
		ps = con.prepareStatement(sql);
			
	
		ps.setInt(1, StoreId);
		ps.setInt(2, tableNo);
		
		ps.executeUpdate();
	
		
	}
	
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		DbUtil.dbClose( ps, con);
	}
	
}


@Override
public int findOrderTotalPrice(int orderId) throws SQLException {
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	int res=0;
	String sql= proFile.getProperty("query.order.findOrderTotalPrice");
	try {
		con = DbUtil.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, orderId);
		rs = ps.executeQuery();
		if(rs.next()) {
			
			res = rs.getInt(1);
			
		}
		
	}
	
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		DbUtil.dbClose(rs, ps, con);
	}
	return res;
	
}


@Override
public void updateOrderTotalPrice(int totalPrice, int orderId) throws SQLException {
	Connection con=null;
	PreparedStatement ps=null;

	String sql= proFile.getProperty("query.order.updateOrderTotalPrice");
	try {
		con = DbUtil.getConnection();
		ps = con.prepareStatement(sql);
			
	
		ps.setInt(1, totalPrice);
		ps.setInt(2, orderId);
		
		ps.executeUpdate();
	
		
	}
	
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		DbUtil.dbClose( ps, con);
	}
	
}



	

	}

