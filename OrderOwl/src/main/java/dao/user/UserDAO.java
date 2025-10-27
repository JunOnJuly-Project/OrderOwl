package dao.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dto.UserDTO;
import dto.MenuDTO;
import dto.OrderDTO;
import dto.QrcodeDTO;
import dto.StoreDTO;
import dto.StoreTableDTO;

public interface UserDAO {
	/*
	 * 메뉴 관련
	 */
	int insertMenu(MenuDTO menuDto) throws SQLException;
	
	int updateMenu(MenuDTO menuDto) throws SQLException;

	int deleteMenu(int menuId) throws SQLException;
	
	MenuDTO selectById(int menuId) throws SQLException;
	
	List<MenuDTO> selectAllMenu(int userId) throws SQLException;
	
	/*
	 * 주문 관련
	 */
	List<OrderDTO> selectAllOrder(int ownerId) throws SQLException;
	
	List<OrderDTO> selectOrderByState(int ownerId, String state) throws SQLException;
	
	int updateOrder(int orderId, String state) throws SQLException;
	
	int updateOrderStatus(int orderId) throws SQLException;
	
	/*
	 * 매장 관련
	 */
	int joinStore(StoreDTO storeDto) throws SQLException;
	
	int updateStore(StoreDTO storeDto) throws SQLException;
	
	int quitStore(int userId) throws SQLException;
	
	StoreDTO selectStore(int ownerId) throws SQLException;
	/*
	 * 매출 관련
	 */
	Map<Integer, Integer> selectSales(int ownerId, String state) throws SQLException;
	
	/*
	 * 인증 관련
	 */
	StoreDTO auth(int ownerId) throws SQLException;
	
	int account(UserDTO user) throws SQLException;
	
	UserDTO login(String userEmail, String password) throws SQLException;
	
	/*
	 * 테이블 관련
	 */
	StoreTableDTO createTable(int storeId, String tableNo) throws SQLException;
	
	int countTable(int storeId) throws SQLException;
	
	List<StoreTableDTO> selectTableAll(int storeId) throws SQLException;
	
	StoreTableDTO selectTable(int tableId) throws SQLException;
	
	/*
	 * qr 관련
	 */
	int createQr(int tableId, String qrcodeData, String qrImgSrc) throws SQLException;
	
	List<QrcodeDTO> selectAllQr(int storeId) throws SQLException;
	
	QrcodeDTO selectQr(int qrcodeId) throws SQLException;
}
