package service.user;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import dto.MenuDTO;
import dto.OrderDTO;
import dto.StoreDTO;

public interface UserService {
	/*
	 * 메뉴 관련
	 */
	MenuDTO createMenu(int storeId, String menuName,
			int price, String description, String imgSrc,
			int category1Code, int category2Code, String checkRec,
			String orderRequest, LocalTime closeTime, String soldOut);
	
	void insertMenu(MenuDTO menuDTO) throws SQLException;
	
	void updateMenu(MenuDTO menuDTO) throws SQLException;
	
	void deleteMenu(int menuId) throws SQLException;
	
	List<MenuDTO> selectAllMenu(int storeId) throws SQLException;
	
	/*
	 * 주문 관련
	 */
	List<OrderDTO> selectAllOrder(int storeId) throws SQLException;
	
	List<OrderDTO> selectOrderByState(int storeId, String state) throws SQLException;

	void updateOrder(int orderId, String state) throws SQLException;
	
	/*
	 * 매장 관련
	 */
	StoreDTO createInform(int ownerId, String storeName,
			String address, String region, String phoneNumber,
			String description, String imgSrc, LocalDateTime createdAt);
	
	void joinStore(StoreDTO storeDto) throws SQLException;
	
	void updateStore(StoreDTO storeDto) throws SQLException;
	
	void quitStore(int storeId) throws SQLException;
	
	/*
	 * 매출 관련
	 */
	Map<Integer, Integer> selectSales(int storeId, String state) throws SQLException;
	
	/*
	 * 인증
	 */
	boolean authUser(int storeId, int ownerId) throws SQLException;
}
