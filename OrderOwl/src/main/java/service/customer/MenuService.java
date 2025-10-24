package service.customer;


import java.util.ArrayList;
import java.util.List;

import dao.customer.MenuDAOImpl;
import dto.MenuDTO;

public class MenuService {

	
	public List<MenuDTO> selectMenuByStoreId(int key) {
		
		return new MenuDAOImpl().selectAllMenu(key);
	}
	
	public List<MenuDTO> selectMenuByCategory(int key){
		List<MenuDTO> findMenuList = new ArrayList<MenuDTO>();
		String[] menuList = new MenuDAOImpl().menuListByCategory(key).split(",");
		for(int i = 0 ; i < menuList.length ; i++) {
			
			findMenuList.add(new MenuDAOImpl().selectMenuById(Integer.parseInt(menuList[i])));
			
			
		}
		
		return findMenuList;
	}

}
