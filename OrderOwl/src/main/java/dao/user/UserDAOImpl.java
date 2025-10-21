package dao.user;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import dto.MenuDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import dto.StoreDTO;
import util.DbUtil;

public class UserDAOImpl implements UserDAO {
	private Properties proFile = new Properties();
	
	public UserDAOImpl() {
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("dbQuery.properties");
			proFile.load(is);
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insertMenu(MenuDTO menuDto) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.menu.insert");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuDto.getStoreId());
			ps.setString(2, menuDto.getMenuName());
			ps.setInt(3, menuDto.getPrice());
			ps.setString(4, menuDto.getDescription());
			ps.setString(5, menuDto.getImgSrc());
			ps.setInt(6, menuDto.getCategory1Code());
			ps.setInt(7, menuDto.getCategory2Code());
			ps.setString(8, menuDto.getCheckRec());
			ps.setString(9, menuDto.getOrderRequest());
			ps.setTime(10, Time.valueOf(menuDto.getCloseTime()));
			ps.setString(11, menuDto.getSoldOut());
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public int updateMenu(MenuDTO menuDto) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.menu.update");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setString(1, menuDto.getMenuName());
			ps.setInt(2, menuDto.getPrice());
			ps.setString(3, menuDto.getDescription());
			ps.setString(4, menuDto.getImgSrc());
			ps.setInt(5, menuDto.getCategory1Code());
			ps.setInt(6, menuDto.getCategory2Code());
			ps.setString(7, menuDto.getCheckRec());
			ps.setString(8, menuDto.getOrderRequest());
			ps.setTime(9, Time.valueOf(menuDto.getCloseTime()));
			ps.setString(10, menuDto.getSoldOut());
			ps.setInt(11, menuDto.getStoreId());
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public int deleteMenu(int menuId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.menu.delete");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuId);
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public List<MenuDTO> selectAllMenu(int storeId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MenuDTO> list = new ArrayList<>();
		
		String sql= proFile.getProperty("user.menu.selectAll");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, storeId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				MenuDTO menuDto = new MenuDTO(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getInt(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(7),
						rs.getInt(8),
						rs.getString(9),
						rs.getString(10),
						Optional.ofNullable(rs.getTime(11))
                        .map(Time::toLocalTime)
                        .orElse(null),
						rs.getString(12)
					);
				
			   list.add(menuDto);
			}
		}
		
		finally {
			DbUtil.dbClose(con, ps, rs);
		}
		
		return list;
	}

	@Override
	public List<OrderDTO> selectAllOrder(int storeId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<OrderDTO> list = new ArrayList<>();
		OrderDTO curOrder = null;
		
		String sql= proFile.getProperty("user.order.selectAll");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, storeId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				OrderDTO orderDto = new OrderDTO(
					rs.getInt(1),
					rs.getInt(2),
					rs.getInt(3),
					rs.getTimestamp(4).toLocalDateTime(),
					rs.getString(5),
					rs.getInt(6),
					rs.getInt(7),
					rs.getString(8)
				);
				
				if (curOrder.equals(orderDto) == false) {
					if (curOrder != null) {
						list.add(curOrder);
					}
					
					curOrder = orderDto;
				}
				
				curOrder.addOrderDetail(
					new OrderDetailDTO(
						rs.getInt(9), 
						orderDto.getOrderId(),
						rs.getInt(10), 
						rs.getInt(11), 
						rs.getInt(12)
					)
				);
			}
		}
		
		finally {
			DbUtil.dbClose(con, ps, rs);
		}
		
		return list;
	}

	@Override
	public List<OrderDTO> selectOrderByState(int storeId, String state) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<OrderDTO> list = new ArrayList<>();
		OrderDTO curOrder = null;
		
		String sql= proFile.getProperty("user.order.selectByState");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, storeId);
			ps.setString(2, state);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				OrderDTO orderDto = new OrderDTO(
					rs.getInt(1),
					rs.getInt(2),
					rs.getInt(3),
					rs.getTimestamp(4).toLocalDateTime(),
					rs.getString(5),
					rs.getInt(6),
					rs.getInt(7),
					rs.getString(8)
				);
				
				if (curOrder.equals(orderDto) == false) {
					if (curOrder != null) {
						list.add(curOrder);
					}
					
					curOrder = orderDto;
				}
				
				curOrder.addOrderDetail(
					new OrderDetailDTO(
						rs.getInt(9), 
						orderDto.getOrderId(),
						rs.getInt(10), 
						rs.getInt(11), 
						rs.getInt(12)
					)
				);
			}
		}
		
		finally {
			DbUtil.dbClose(con, ps, rs);
		}
		
		return list;
	}

	@Override
	public int updateOrder(int orderId, String state) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.order.update");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setString(1, state);
			ps.setInt(2, orderId);
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public int joinStore(StoreDTO storeDto) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.store.join");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, storeDto.getOwnerId());
			ps.setString(2, storeDto.getStoreName());
			ps.setString(3, storeDto.getAddress());
			ps.setString(4, storeDto.getRegion());
			ps.setString(5, storeDto.getPhoneNumber());
			ps.setString(6, storeDto.getDescription());
			ps.setString(7, storeDto.getImgSrc());
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public int updateStore(StoreDTO storeDto) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.store.update");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setString(1, storeDto.getStoreName());
			ps.setString(2, storeDto.getAddress());
			ps.setString(3, storeDto.getRegion());
			ps.setString(4, storeDto.getPhoneNumber());
			ps.setString(5, storeDto.getDescription());
			ps.setString(6, storeDto.getImgSrc());
			ps.setInt(7, storeDto.getStoreId());
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public int quitStore(int storeId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.store.quit");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, storeId);
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public Map<Integer, Integer> selectSales(int storeId, String state) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Map<Integer, Integer> sales = new HashMap<>();
		
		String sql= proFile.getProperty("user.sales.select." + state);
		
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				sales.put(rs.getInt(1), rs.getInt(2));
			}
		}
		
		finally {
			DbUtil.dbClose(con, ps, rs);
		}
		
		return sales;
	}
}
