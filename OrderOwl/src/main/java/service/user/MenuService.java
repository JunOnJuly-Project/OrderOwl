package service.user;


import java.util.List;

import dao.user.MenuDAOImpl;
import dto.MenuDTO;

public class MenuService {

	
	public List<MenuDTO> selectMenuByStoreId(int key) {
		
		return new MenuDAOImpl().selectAllMenu(key);
	}

}
