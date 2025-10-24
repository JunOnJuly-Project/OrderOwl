package dao.customer;

import java.util.List;

import dto.MenuDTO;

public interface MenuDAO {
	
	public List<MenuDTO> selectAllMenu(int key);
	
	public MenuDTO selectMenuById(int key);
	public String menuListByCategory(int key);

}
