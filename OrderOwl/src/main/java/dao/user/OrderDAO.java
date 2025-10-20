package dao.user;

import java.sql.SQLException;

import dto.StoreDTO;

public interface OrderDAO {

	public StoreDTO selectStoreById() throws SQLException;
	
	
	
}
