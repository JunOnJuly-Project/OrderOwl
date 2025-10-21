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
	public void insertMenu(MenuDTO menuDTO) throws SQLException {
		userDao.insertMenu(menuDTO);
	}

	@Override
	public void updateMenu(MenuDTO menuDTO) throws SQLException {

	}

	@Override
	public void deleteMenu(int menuId) throws SQLException {

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
	public void updateOrder(int orderId, String state) throws SQLException {

	}

	@Override
	public StoreDTO createInform(int ownerId, String storeName, String address, String region, String phoneNumber,
			String description, String imgSrc, LocalDateTime createdAt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joinStore(StoreDTO storeDto) throws SQLException {

	}

	@Override
	public void updateStore(StoreDTO storeDto) throws SQLException {

	}

	@Override
	public void quitStore(int storeId) throws SQLException {

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
