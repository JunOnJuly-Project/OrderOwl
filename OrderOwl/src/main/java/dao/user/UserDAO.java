package dao.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dto.MenuDTO;
import dto.OrderDTO;
import dto.StoreDTO;

public interface UserDAO {
	/*
	 * 메뉴 관련
	 */
	int insertMenu(MenuDTO menuDto) throws SQLException;
	
	int updateMenu(MenuDTO menuDto) throws SQLException;

	int deleteMenu(int menuId) throws SQLException;
	
	MenuDTO selectById(int menuId) throws SQLException;
	
	List<MenuDTO> selectAllMenu(int storeId) throws SQLException;
	
	/*
	 * 주문 관련
	 */
	List<OrderDTO> selectAllOrder(int storeId) throws SQLException;
	
	List<OrderDTO> selectOrderByState(int storeId, String state) throws SQLException;
	
	int updateOrder(int orderId, String state) throws SQLException;
	
	/*
	 * 매장 관련
	 */
	int joinStore(StoreDTO storeDto) throws SQLException;
	
	int updateStore(StoreDTO storeDto) throws SQLException;
	
	int quitStore(int storeId) throws SQLException;
	
	/*
	 * 매출 관련
	 */
	Map<Integer, Integer> selectSales(int storeId, String state) throws SQLException;
}
