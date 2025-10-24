package dao.user;

import java.util.List;

import dto.MenuDTO;

public interface MenuDAO {
	
	public List<MenuDTO> selectAllMenu(int key);
	
	public MenuDTO selectMenuById();

}
