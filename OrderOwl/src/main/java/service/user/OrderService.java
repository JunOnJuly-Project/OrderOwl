package service.user;

import java.sql.SQLException;

import dao.user.OrderDAOImpl;
import dto.StoreDTO;

public class OrderService {
	
	public StoreDTO selectStoreByStoreId() throws SQLException {
		StoreDTO dto = new OrderDAOImpl().selectStoreById();
		return new OrderDAOImpl().selectStoreById();
		
	}

}
