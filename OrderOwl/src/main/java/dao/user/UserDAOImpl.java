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
import dto.UserDTO;
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
			DbUtil.dbClose(ps, con);
		}
		
		return result;
	}

	@Override
	public int updateMenu(MenuDTO menuDto) throws SQLException {
		System.out.println(menuDto);
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
			ps.setInt(12, menuDto.getMenuId());
			
			System.out.println(ps.toString());
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(ps, con);
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
			DbUtil.dbClose(ps, con);
		}
		
		return result;
	}

	public MenuDTO selectById(int menuId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MenuDTO menu = null;
		
		String sql= proFile.getProperty("user.menu.selectById");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuId);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				menu = new MenuDTO(
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
			}
		}
		
		finally {
			DbUtil.dbClose(rs, ps, con);
		}
		
		return menu;
	}
	
	@Override
	public List<MenuDTO> selectAllMenu(int userId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MenuDTO> list = new ArrayList<>();
		
		String sql= proFile.getProperty("user.menu.selectAll");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			
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
			DbUtil.dbClose(rs, ps, con);
		}
		
		return list;
	}

	@Override
	public List<OrderDTO> selectAllOrder(int ownerId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<OrderDTO> list = new ArrayList<>();
		OrderDTO curOrder = null;
		
		String sql= proFile.getProperty("user.order.selectAll");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, ownerId);
			System.out.println(ps.toString());
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
				
				if (curOrder == null) {
					curOrder = orderDto;
				}
				
				else if (curOrder.equals(orderDto) == false) {
					list.add(curOrder);
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
			DbUtil.dbClose(rs, ps, con);
		}
		
		return list;
	}

	@Override
	public List<OrderDTO> selectOrderByState(int ownerId, String state) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<OrderDTO> list = new ArrayList<>();
		OrderDTO curOrder = null;
		
		String sql= proFile.getProperty("user.order.selectByState");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, ownerId);
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
			DbUtil.dbClose(rs, ps, con);
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
			DbUtil.dbClose(ps, con);
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
			DbUtil.dbClose(ps, con);
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
			
			System.out.println(ps.toString());
			
			result = ps.executeUpdate();
		}
		
		finally {
			System.out.println(result);
			DbUtil.dbClose(ps, con);
		}
		
		return result;
	}

	@Override
	public int quitStore(int userId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.store.quit");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(ps, con);
		}
		
		return result;
	}

	@Override
	public Map<Integer, Integer> selectSales(int ownerId, String state) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Map<Integer, Integer> sales = new HashMap<>();
		
		String sql= proFile.getProperty("user.sales.select." + state);
		System.out.println(sql);
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, ownerId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				sales.put(rs.getInt(1), rs.getInt(2));
			}
		}
		
		finally {
			DbUtil.dbClose(rs, ps, con);
		}
		
		return sales;
	}
	
	@Override
	public int account(UserDTO user) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int result = 0;
		
		String sql= proFile.getProperty("user.auth.account");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());	
			ps.setString(4, user.getRole());
	
			result = ps.executeUpdate();
		}
		
		finally {
			DbUtil.dbClose(ps, con);
		}
		
		return result;
	}

	@Override
	public StoreDTO auth(int ownerId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		StoreDTO store = null;
		
		String sql= proFile.getProperty("user.auth");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, ownerId);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				store = new StoreDTO(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					rs.getString(7),
					rs.getString(8),
					rs.getTimestamp(9).toLocalDateTime(),
					null
				);
			}
		}
		
		finally {
			DbUtil.dbClose(rs, ps, con);
		}
		
		return store;
	}

	@Override
	public UserDTO login(String userEmail, String password) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		UserDTO user = null;
		
		String sql= proFile.getProperty("user.auth.login");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setString(1, userEmail);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				user = new UserDTO(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getTimestamp(6).toLocalDateTime()
				);
			}
		}
		
		finally {
			DbUtil.dbClose(rs, ps, con);
		}
		
		return user;
	}

	@Override
	public StoreDTO selectStore(int ownerId) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		StoreDTO store = null;
		
		String sql= proFile.getProperty("user.store.select");
		
		try {
			con = DbUtil.getConnection();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, ownerId);
			
			System.out.println(ps.toString());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				store = new StoreDTO(
					rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					rs.getString(7),
					rs.getString(8),
					rs.getTimestamp(9).toLocalDateTime()
				);
			}
		}
		
		finally {
			System.out.println(store);
			DbUtil.dbClose(rs, ps, con);
		}
		
		return store;
	}
}
