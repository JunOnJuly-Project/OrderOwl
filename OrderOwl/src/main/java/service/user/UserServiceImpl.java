package service.user;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import dao.user.UserDAO;
import dao.user.UserDAOImpl;
import dto.MenuDTO;
import dto.OrderDTO;
import dto.StoreDTO;

public class UserServiceImpl implements UserService {
	UserDAO userDao = new UserDAOImpl();
	
	@Override
	public MenuDTO createMenu(int storeId, String menuName, int price, String description, String imgSrc,
			int category1Code, int category2Code, String checkRec, String orderRequest, LocalTime closeTime,
			String soldOut) {
		
		return new MenuDTO(
			storeId,
			menuName,
			price,
			description,
			imgSrc,
			category1Code,
			category2Code,
			checkRec,
			orderRequest,
			closeTime,
			soldOut
		);
	}

	@Override
	public int insertMenu(MenuDTO menuDTO) throws SQLException {
		return userDao.insertMenu(menuDTO);
	}

	@Override
	public int updateMenu(MenuDTO menuDTO) throws SQLException {
		return 0;
	}

	@Override
	public int deleteMenu(int menuId) throws SQLException {
		return userDao.deleteMenu(menuId);
	}

	@Override
	public MenuDTO selectById(int menuId) throws SQLException {
		return userDao.selectById(menuId);
	}
	
	@Override
	public List<MenuDTO> selectAllMenu(int storeId) throws SQLException {
		List<MenuDTO> menus = userDao.selectAllMenu(storeId);
		
		return menus;
	}

	@Override
	public List<OrderDTO> selectAllOrder(int storeId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> selectOrderByState(int storeId, String state) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateOrder(int orderId, String state) throws SQLException {
		return 0;
	}

	@Override
	public StoreDTO createInform(int ownerId, String storeName, String address, String region, String phoneNumber,
			String description, String imgSrc, LocalDateTime createdAt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int joinStore(StoreDTO storeDto) throws SQLException {
		return 0;
	}

	@Override
	public int updateStore(StoreDTO storeDto) throws SQLException {
		return 0;
	}

	@Override
	public int quitStore(int storeId) throws SQLException {
		return 0;
	}

	@Override
	public Map<Integer, Integer> selectSales(int storeId, String state) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authUser(int storeId, int ownerId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
