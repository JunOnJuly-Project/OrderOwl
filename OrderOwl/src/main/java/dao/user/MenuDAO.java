package dao.user;

import java.util.List;

import dto.MenuDTO;

public interface MenuDAO {
	
	public List<MenuDTO> selectAllMenu();
	
	public MenuDTO selectMenuById();

}
