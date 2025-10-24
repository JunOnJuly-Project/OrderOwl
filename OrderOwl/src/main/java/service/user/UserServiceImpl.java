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
import dto.UserDTO;

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
	public MenuDTO createMenu(int menuId, int storeId, String menuName, int price, String description, String imgSrc,
			int category1Code, int category2Code, String checkRec, String orderRequest, LocalTime closeTime,
			String soldOut) {
		
		return new MenuDTO(
			menuId,
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
		return userDao.updateMenu(menuDTO);
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
	public List<MenuDTO> selectAllMenu(int userId) throws SQLException {
		List<MenuDTO> menus = userDao.selectAllMenu(userId);
		
		return menus;
	}

	@Override
	public List<OrderDTO> selectAllOrder(int ownerId) throws SQLException {
		
		return userDao.selectAllOrder(ownerId);
	}

	@Override
	public List<OrderDTO> selectOrderByState(int ownerId, String state) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateOrder(int orderId, String state) throws SQLException {
		return userDao.updateOrder(orderId, state);
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
		return userDao.updateStore(storeDto);
	}

	@Override
	public int quitStore(int userId) throws SQLException {
		return userDao.quitStore(userId);
	}

	@Override
	public Map<Integer, Integer> selectSales(int ownerId, String state) throws SQLException {
		return userDao.selectSales(ownerId, state);
	}
	
	@Override
	public boolean auth(int storeId, int ownerId) throws SQLException {
		return true;
	}
	
	@Override
	public UserDTO login(String userEmail, String password) throws SQLException {
		UserDTO user = userDao.login(userEmail, password);
		
		return user;
	}
	
	@Override
	public UserDTO account(UserDTO user) throws SQLException {
		System.out.println(user);
		int result = userDao.account(user);
		
		return result == 1 ? user : null;
	}

	@Override
	public UserDTO createUser(String username, String password, String email, String role)
			throws SQLException {
		return new UserDTO(
			username,
			password,
			email,
			role
		);
	}

	@Override
	public StoreDTO selectStore(int ownerId) throws SQLException {
		System.out.println("?");
		return userDao.selectStore(ownerId);
	}
	
	
}
