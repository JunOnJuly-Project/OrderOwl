package service.user;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import dto.MenuDTO;
import dto.OrderDTO;
import dto.StoreDTO;
import dto.UserDTO;

public interface UserService {
	/*
	 * 메뉴 관련
	 */
	MenuDTO createMenu(int storeId, String menuName,
			int price, String description, String imgSrc,
			int category1Code, int category2Code, String checkRec,
			String orderRequest, LocalTime closeTime, String soldOut);
	
	MenuDTO createMenu(int menuId, int storeId, String menuName,
			int price, String description, String imgSrc,
			int category1Code, int category2Code, String checkRec,
			String orderRequest, LocalTime closeTime, String soldOut);
	
	int insertMenu(MenuDTO menuDTO) throws SQLException;
	
	int updateMenu(MenuDTO menuDTO) throws SQLException;
	
	int deleteMenu(int menuId) throws SQLException;
	
	MenuDTO selectById(int menuId) throws SQLException;
	
	List<MenuDTO> selectAllMenu(int userId) throws SQLException;
	
	/*
	 * 주문 관련
	 */
	List<OrderDTO> selectAllOrder(int storeId) throws SQLException;
	
	List<OrderDTO> selectOrderByState(int storeId, String state) throws SQLException;

	int updateOrder(int orderId, String state) throws SQLException;
	
	/*
	 * 매장 관련
	 */
	StoreDTO createInform(int ownerId, String storeName,
			String address, String region, String phoneNumber,
			String description, String imgSrc, LocalDateTime createdAt);
	
	int joinStore(StoreDTO storeDto) throws SQLException;
	
	int updateStore(StoreDTO storeDto) throws SQLException;
	
	int quitStore(int storeId) throws SQLException;
	
	StoreDTO selectStore(int userId) throws SQLException;
	/*
	 * 매출 관련
	 */
	Map<Integer, Integer> selectSales(int ownerId, String state) throws SQLException;
	
	/*
	 * 인증
	 */
	boolean auth(int storeId, int ownerId) throws SQLException;
	
	UserDTO login(String userEmail, String password) throws SQLException;
	
	UserDTO account(UserDTO user) throws SQLException;
	
	UserDTO createUser(
			String username,
			String password,
			String email, 
			String role) throws SQLException;
}
