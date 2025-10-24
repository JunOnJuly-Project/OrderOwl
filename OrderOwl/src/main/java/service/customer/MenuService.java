package service.customer;


import java.util.List;

import dao.customer.MenuDAOImpl;
import dto.MenuDTO;

public class MenuService {

	
	public List<MenuDTO> selectMenuByStoreId(int key) {
		
		return new MenuDAOImpl().selectAllMenu(key);
	}

}
