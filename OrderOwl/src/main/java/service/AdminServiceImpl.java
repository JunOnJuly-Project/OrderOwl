package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.AdminDAO;
import dto.AdminDTO.MenuDTO;
import dto.AdminDTO.MenuRequestDTO;
import dto.AdminDTO.SalesReportDTO;
import dto.AdminDTO.StoreDTO;
import dto.AdminDTO.StoreRequestDTO;
import dto.AdminDTO.UserDTO;

/**
 * 관리자 서비스 구현체 - 비즈니스 로직 처리
 */
public class AdminServiceImpl implements AdminService {

	private AdminDAO adminDAO;

	public AdminServiceImpl() {
		this.adminDAO = new AdminDAO();
	}

	// ==================== 전체 관리 ====================

	@Override
	public boolean addStore(StoreDTO store) {
		try {
			if (store == null || store.getStoreName() == null || store.getStoreName().trim().isEmpty()) {
				throw new IllegalArgumentException("매장 정보가 유효하지 않습니다.");
			}

			if (adminDAO.existsByBusinessNumber(store.getBusinessNumber())) {
				throw new IllegalArgumentException("이미 등록된 사업자 번호입니다.");
			}

			store.setStatus("PENDING");
			int result = adminDAO.insertStore(store);

			if (result > 0) {
				sendNotification(store.getOwnerId(), "매장 가입 요청이 접수되었습니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteStore(long storeId) {
		try {
			StoreDTO store = adminDAO.selectStoreById(storeId);
			if (store == null) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			int pendingOrders = adminDAO.countPendingOrders(storeId);
			if (pendingOrders > 0) {
				throw new IllegalStateException("진행 중인 주문이 있어 삭제 요청을 할 수 없습니다.");
			}

			// ✅ 즉시 삭제 대신 DELETE_PENDING 상태로 변경
			int result = adminDAO.updateStoreStatus(storeId, "DELETE_PENDING");

			if (result > 0) {
				sendNotification(store.getOwnerId(), "매장 삭제 요청이 접수되었습니다. 관리자 승인 후 삭제됩니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<StoreDTO> getDeletePendingStores() {
		try {
			return adminDAO.selectDeletePendingStores();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean approveStoreDeletion(long storeId) {
		try {
			StoreDTO store = adminDAO.selectStoreById(storeId);
			if (store == null) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			if (!"DELETE_PENDING".equals(store.getStatus())) {
				throw new IllegalStateException("삭제 대기 상태가 아닙니다.");
			}

			int result = adminDAO.deleteStore(storeId);

			if (result > 0) {
				sendNotification(store.getOwnerId(), "매장이 최종 삭제되었습니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancelStoreDeletion(long storeId) {
		try {
			StoreDTO store = adminDAO.selectStoreById(storeId);
			if (store == null) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			if (!"DELETE_PENDING".equals(store.getStatus())) {
				throw new IllegalStateException("삭제 대기 상태가 아닙니다.");
			}

			int result = adminDAO.updateStoreStatus(storeId, "ACTIVE");

			if (result > 0) {
				sendNotification(store.getOwnerId(), "매장 삭제가 취소되었습니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean forceDeleteUser(long userId, String reason) {
		try {
			UserDTO user = adminDAO.selectUserById(userId);
			if (user == null) {
				throw new IllegalArgumentException("존재하지 않는 유저입니다.");
			}

			int result = adminDAO.updateUserStatus(userId, "FORCE_DELETED");

			if (result > 0) {
				sendNotification(userId, "계정이 관리자에 의해 강제 탈퇴 처리되었습니다. 사유: " + reason);
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ==================== 매장 관리 ====================

	@Override
	public String createStoreQR(long storeId) {
		try {
			if (!adminDAO.existsStoreById(storeId)) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			String qrPath = "https://orderowl.com/order/" + storeId;
			adminDAO.updateStoreQRPath(storeId, qrPath);

			return qrPath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean approveMenuAddRequest(long requestId) {
		try {
			MenuRequestDTO request = adminDAO.selectMenuRequestById(requestId);
			if (request == null) {
				throw new IllegalArgumentException("존재하지 않는 요청입니다.");
			}

			if (request.getMenuName() == null || request.getMenuName().trim().isEmpty() || request.getPrice() <= 0) {
				throw new IllegalArgumentException("유효하지 않은 메뉴 정보입니다.");
			}

			MenuDTO newMenu = new MenuDTO();
			newMenu.setStoreId(request.getStoreId());
			newMenu.setMenuName(request.getMenuName());
			newMenu.setPrice(request.getPrice());
			newMenu.setCategory(request.getCategory());
			newMenu.setDescription(request.getDescription());

			adminDAO.insertMenu(newMenu);
			int result = adminDAO.approveMenuRequest(requestId, "APPROVED");

			if (result > 0) {
				sendNotification(request.getOwnerId(), "메뉴 추가 요청이 승인되었습니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean approveMenuUpdateRequest(long requestId) {
		try {
			MenuRequestDTO request = adminDAO.selectMenuRequestById(requestId);
			if (request == null) {
				throw new IllegalArgumentException("존재하지 않는 요청입니다.");
			}

			if (request.getMenuName() == null || request.getMenuName().trim().isEmpty() || request.getPrice() <= 0) {
				throw new IllegalArgumentException("유효하지 않은 메뉴 정보입니다.");
			}

			MenuDTO menu = new MenuDTO();
			menu.setMenuId(request.getMenuId());
			menu.setMenuName(request.getMenuName());
			menu.setPrice(request.getPrice());
			menu.setCategory(request.getCategory());
			menu.setDescription(request.getDescription());

			adminDAO.updateMenu(menu);
			int result = adminDAO.approveMenuRequest(requestId, "APPROVED");

			if (result > 0) {
				sendNotification(request.getOwnerId(), "메뉴 수정 요청이 승인되었습니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean approveMenuDeleteRequest(long requestId) {
		try {
			MenuRequestDTO request = adminDAO.selectMenuRequestById(requestId);
			if (request == null) {
				throw new IllegalArgumentException("존재하지 않는 요청입니다.");
			}

			boolean hasActiveOrders = adminDAO.hasActiveOrdersWithMenu(request.getMenuId());
			if (hasActiveOrders) {
				throw new IllegalStateException("진행 중인 주문에 포함된 메뉴는 삭제할 수 없습니다.");
			}

			adminDAO.deleteMenu(request.getMenuId());
			int result = adminDAO.approveMenuRequest(requestId, "APPROVED");

			if (result > 0) {
				sendNotification(request.getOwnerId(), "메뉴 삭제 요청이 승인되었습니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public StoreDTO getStoreInfo(long storeId) {
		try {
			StoreDTO store = adminDAO.selectStoreById(storeId);
			if (store == null) {
				return null;
			}

			store.setMenuCount(adminDAO.countStoreMenus(storeId));
			store.setTotalOrders(adminDAO.countStoreOrders(storeId));
			store.setTotalSales(adminDAO.sumStoreSales(storeId));

			return store;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SalesReportDTO getStoreSalesInfo(long storeId, LocalDate startDate, LocalDate endDate) {
		try {
			if (!adminDAO.existsStoreById(storeId)) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			if (startDate.isAfter(endDate)) {
				throw new IllegalArgumentException("시작일이 종료일보다 늦을 수 없습니다.");
			}

			SalesReportDTO report = new SalesReportDTO();
			report.setStoreId(storeId);
			report.setStartDate(startDate);
			report.setEndDate(endDate);

			long totalSales = adminDAO.sumSalesByPeriod(storeId, startDate, endDate);
			int totalOrders = adminDAO.countOrdersByPeriod(storeId, startDate, endDate);

			report.setTotalSales(totalSales);
			report.setTotalOrders(totalOrders);
			report.setAverageOrderAmount(totalOrders > 0 ? totalSales / totalOrders : 0);
			report.setDailySales(adminDAO.selectDailySales(storeId, startDate, endDate));
			report.setMenuSales(adminDAO.selectMenuSales(storeId, startDate, endDate));

			return report;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean approveStoreInfoAddRequest(long requestId) {
		try {
			StoreRequestDTO request = adminDAO.selectStoreRequestById(requestId);
			if (request == null) {
				throw new IllegalArgumentException("존재하지 않는 요청입니다.");
			}

			if (request.getStoreName() == null || request.getStoreName().trim().isEmpty()) {
				throw new IllegalArgumentException("유효하지 않은 매장 정보입니다.");
			}

			// ✅ 현지 시간으로 승인 처리
			int result = adminDAO.updateStoreRequestWithTimestamp(requestId, "APPROVED");

			if (result > 0) {
				StoreDTO newStore = createStoreFromRequest(request);
				adminDAO.insertStore(newStore);
				sendNotification(request.getOwnerId(), "매장 등록이 승인되었습니다.");
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean approveStoreInfoUpdateRequest(long requestId) {
		try {
			StoreRequestDTO request = adminDAO.selectStoreRequestById(requestId);
			if (request == null) {
				throw new IllegalArgumentException("존재하지 않는 요청입니다.");
			}

			if (request.getStoreName() == null || request.getStoreName().trim().isEmpty()) {
				throw new IllegalArgumentException("유효하지 않은 매장 정보입니다.");
			}

			if (request.getStoreId() > 0) {
				StoreDTO originalStore = adminDAO.selectStoreById(request.getStoreId());
				if (originalStore == null) {
					throw new IllegalArgumentException("원본 매장 정보를 찾을 수 없습니다.");
				}

				if (!originalStore.getBusinessNumber().equals(request.getBusinessNumber())) {
					throw new IllegalArgumentException("사업자 번호는 변경할 수 없습니다.");
				}

				StoreDTO updatedStore = createStoreFromRequest(request);
				updatedStore.setStoreId(request.getStoreId());
				adminDAO.updateStore(updatedStore);
			}

			// ✅ 현지 시간으로 승인 처리
			int result = adminDAO.updateStoreRequestWithTimestamp(requestId, "APPROVED");

			if (result > 0) {
				sendNotification(request.getOwnerId(), "매장 정보 수정이 승인되었습니다.");
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ==================== 매장 요청 거절 기능 ====================

	@Override
	public boolean rejectStoreInfoAddRequest(long requestId, String reason) {
		try {
			StoreRequestDTO request = adminDAO.selectStoreRequestById(requestId);
			if (request == null) {
				throw new IllegalArgumentException("존재하지 않는 요청입니다.");
			}

			// ✅ 현지 시간으로 거절 처리
			int result = adminDAO.updateStoreRequestWithTimestamp(requestId, "REJECTED");

			if (result > 0) {
				sendNotification(request.getOwnerId(), 
					"매장 등록 요청이 거절되었습니다. 사유: " + reason);
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean rejectStoreInfoUpdateRequest(long requestId, String reason) {
		try {
			StoreRequestDTO request = adminDAO.selectStoreRequestById(requestId);
			if (request == null) {
				throw new IllegalArgumentException("존재하지 않는 요청입니다.");
			}

			// ✅ 현지 시간으로 거절 처리
			int result = adminDAO.updateStoreRequestWithTimestamp(requestId, "REJECTED");

			if (result > 0) {
				sendNotification(request.getOwnerId(), 
					"매장 정보 수정 요청이 거절되었습니다. 사유: " + reason);
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<StoreDTO> getStoreList() {
		try {
			return adminDAO.selectAllStores();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<StoreRequestDTO> getStoreRequests() {
		try {
			return adminDAO.selectPendingStoreRequests();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<MenuDTO> getStoreMenus(long storeId) {
		try {
			return adminDAO.selectMenusByStoreId(storeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addMenuDirect(MenuDTO menu) {
		try {
			if (menu == null || menu.getMenuName() == null || menu.getMenuName().trim().isEmpty()) {
				throw new IllegalArgumentException("메뉴 정보가 유효하지 않습니다.");
			}

			if (menu.getPrice() <= 0) {
				throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
			}

			int result = adminDAO.insertMenu(menu);

			if (result > 0) {
				sendNotification(menu.getStoreId(), "새 메뉴가 추가되었습니다: " + menu.getMenuName());
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateMenuDirect(MenuDTO menu) {
		try {
			if (menu == null || menu.getMenuName() == null || menu.getMenuName().trim().isEmpty()) {
				throw new IllegalArgumentException("메뉴 정보가 유효하지 않습니다.");
			}

			if (menu.getPrice() <= 0) {
				throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
			}

			MenuDTO existingMenu = adminDAO.selectMenuById(menu.getMenuId());
			if (existingMenu == null) {
				throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
			}

			int result = adminDAO.updateMenu(menu);

			if (result > 0) {
				sendNotification(menu.getStoreId(), "메뉴가 수정되었습니다: " + menu.getMenuName());
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteMenuDirect(long menuId) {
		try {
			MenuDTO menu = adminDAO.selectMenuById(menuId);
			if (menu == null) {
				throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
			}

			// 활성 주문에 포함된 메뉴인지 확인
			boolean inActiveOrders = adminDAO.isMenuInActiveOrders(menuId);
			if (inActiveOrders) {
				throw new IllegalStateException("진행 중인 주문에 포함된 메뉴는 삭제할 수 없습니다.");
			}

			int result = adminDAO.deleteMenu(menuId);

			if (result > 0) {
				sendNotification(menu.getStoreId(), "메뉴가 삭제되었습니다: " + menu.getMenuName());
			}

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<StoreDTO> getAllStoresWithQR() {
		try {
			return adminDAO.selectAllStores();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean regenerateStoreQR(long storeId) {
		try {
			if (!adminDAO.existsStoreById(storeId)) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			String qrPath = "https://orderowl.com/order/" + storeId + "?v=" + System.currentTimeMillis();
			int result = adminDAO.updateStoreQRPath(storeId, qrPath);

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateStoreQRPath(long storeId, String qrPath) {
		try {
			if (!adminDAO.existsStoreById(storeId)) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			if (qrPath == null || qrPath.trim().isEmpty()) {
				throw new IllegalArgumentException("QR 경로가 유효하지 않습니다.");
			}

			int result = adminDAO.updateStoreQRPath(storeId, qrPath);
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateStoreInfo(StoreDTO store) {
		try {
			if (store == null) {
				throw new IllegalArgumentException("매장 정보가 유효하지 않습니다.");
			}

			StoreDTO existingStore = adminDAO.selectStoreById(store.getStoreId());
			if (existingStore == null) {
				throw new IllegalArgumentException("존재하지 않는 매장입니다.");
			}

			int result = adminDAO.updateStore(store);
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UserDTO> getUserList() {
		try {
			return adminDAO.selectAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ==================== 승인/거절 히스토리 ====================

	@Override
	public Map<String, Object> getApprovalHistory(String type, String sortOrder, int page, int pageSize) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> historyList = new ArrayList<>();

		try {
			int offset = (page - 1) * pageSize;

			// ✅ Store 요청 히스토리만 처리 (메뉴 부분 제거)
			if ("STORE".equals(type)) {
				List<StoreRequestDTO> storeRequests = adminDAO.selectStoreRequestHistory(sortOrder, offset, pageSize);
				for (StoreRequestDTO req : storeRequests) {
					if ("APPROVED".equals(req.getStatus()) || "REJECTED".equals(req.getStatus())) {
						Map<String, Object> item = new HashMap<>();
						item.put("type", "STORE");
						item.put("name", req.getStoreName());
						item.put("requestType", req.getRequestType());
						item.put("status", req.getStatus());
						item.put("createdAt", req.getCreatedAt());
						item.put("processedAt", req.getCreatedAt()); // 실제 처리 시간 사용
						historyList.add(item);
					}
				}
			}

			// ✅ 정렬
			if ("DESC".equals(sortOrder)) {
				historyList.sort((a, b) -> {
					String dateA = (String) a.get("processedAt");
					String dateB = (String) b.get("processedAt");
					return dateB.compareTo(dateA);
				});
			} else {
				historyList.sort((a, b) -> {
					String dateA = (String) a.get("processedAt");
					String dateB = (String) b.get("processedAt");
					return dateA.compareTo(dateB);
				});
			}

			// ✅ 전체 카운트 계산 (메뉴 부분 제거)
			int totalCount = adminDAO.countStoreRequestHistory();

			result.put("data", historyList);
			result.put("totalCount", totalCount);
			result.put("success", true);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "히스토리 조회 중 오류가 발생했습니다.");
		}

		return result;
	}

	// ==================== Helper Methods ====================

	private void sendNotification(long userId, String message) {
		System.out.println("📢 알림 전송 [User " + userId + "]: " + message);
	}

	private StoreDTO createStoreFromRequest(StoreRequestDTO request) {
		StoreDTO store = new StoreDTO();
		store.setOwnerId(request.getOwnerId());
		store.setStoreName(request.getStoreName());
		store.setBusinessNumber(request.getBusinessNumber());
		store.setAddress(request.getAddress() != null ? request.getAddress() : "");
		store.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : "");
		store.setStatus("ACTIVE");
		store.setBusinessVerified(true);
		return store;
	}
}