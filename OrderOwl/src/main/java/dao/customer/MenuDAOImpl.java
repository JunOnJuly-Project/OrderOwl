package dao.customer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dto.MenuDTO;
import dto.StoreDTO;
import util.DbUtil;

public class MenuDAOImpl implements MenuDAO {
	
private Properties proFile = new Properties();
	
	
	public MenuDAOImpl() {
	
	try {
		
		InputStream is = getClass().getClassLoader().getResourceAsStream("dbQuery.properties");
		proFile.load(is);
		
		System.out.println("query.select = " +proFile.getProperty("query.order.selectBystoreId"));
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	
	}

	@Override
	public List<MenuDTO> selectAllMenu(int key) {

		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MenuDTO> list = new ArrayList<MenuDTO>();
		MenuDTO menu = null;
		String sql= proFile.getProperty("query.order.selectAllMenuBystoreId");
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, key);
			rs = ps.executeQuery();
			while(rs.next()) {
				
				menu = 
				new MenuDTO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), null, rs.getString(10));
				
				list.add(menu);
				
			}
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DbUtil.dbClose(rs, ps, con);
		}
		return list;
	}

	@Override
	public MenuDTO selectMenuById() {
	
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MenuDTO menu = null;
		String sql= proFile.getProperty("query.order.selectMenuBystoreId");
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				menu = 
				new MenuDTO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), null, rs.getString(10));
				
			}
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DbUtil.dbClose(rs, ps, con);
		}
		return menu;
	}

}
