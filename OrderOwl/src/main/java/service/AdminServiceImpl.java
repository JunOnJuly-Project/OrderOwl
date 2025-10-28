package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.AdminDAO;
import dto.MenuDTO;
import dto.StoreDTO;
import dto.UserDTO;

/**
 * 관리자 서비스 구현체 - 비즈니스 로직 처리
 * OrderOwl.sql 스키마 기준 리팩토링
 */
public class AdminServiceImpl implements AdminService {

    private AdminDAO adminDAO;

    public AdminServiceImpl() {
        this.adminDAO = new AdminDAO();
    }

    // ==================== 매장 관리 ====================

    @Override
    public boolean addStore(StoreDTO store) {
        try {
            if (store == null || store.getStoreName() == null || store.getStoreName().trim().isEmpty()) {
                throw new IllegalArgumentException("매장 정보가 유효하지 않습니다.");
            }

            int result = adminDAO.insertStore(store);

            if (result > 0) {
                sendNotification(store.getOwnerId(), "매장이 등록되었습니다.");
            }

            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStore(int storeId) {
        try {
            StoreDTO store = adminDAO.selectStoreById(storeId);
            if (store == null) {
                throw new IllegalArgumentException("존재하지 않는 매장입니다.");
            }

            int pendingOrders = adminDAO.countPendingOrders(storeId);
            if (pendingOrders > 0) {
                throw new IllegalStateException("진행 중인 주문이 있어 삭제할 수 없습니다.");
            }

            int result = adminDAO.deleteStore(storeId);

            if (result > 0) {
                sendNotification(store.getOwnerId(), "매장이 삭제되었습니다.");
            }

            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public StoreDTO getStoreInfo(int storeId) {
        try {
            StoreDTO store = adminDAO.selectStoreById(storeId);
            if (store == null) {
                return null;
            }

            // 매장 통계 정보 추가
            // 메뉴 개수
            List<MenuDTO> menus = adminDAO.selectMenusByStoreId(storeId);
            if (menus != null) {
                store.setMenu(menus);
            }

            return store;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            
            if (result > 0) {
                sendNotification(store.getOwnerId(), "매장 정보가 수정되었습니다.");
            }
            
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<StoreDTO> getStoreListByOwner(int ownerId) {
        try {
            return adminDAO.selectStoresByOwnerId(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==================== 메뉴 관리 ====================

    @Override
    public List<MenuDTO> getStoreMenus(int storeId) {
        try {
            return adminDAO.selectMenusByStoreId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addMenu(MenuDTO menu) {
        try {
            if (menu == null || menu.getMenuName() == null || menu.getMenuName().trim().isEmpty()) {
                throw new IllegalArgumentException("메뉴 정보가 유효하지 않습니다.");
            }

            if (menu.getPrice() <= 0) {
                throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
            }

            int result = adminDAO.insertMenu(menu);

            if (result > 0) {
                sendNotification(menu.getStoreId(), "메뉴가 추가되었습니다: " + menu.getMenuName());
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
    public boolean deleteMenuDirect(int menuId) {
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
    public MenuDTO getMenuInfo(int menuId) {
        try {
            return adminDAO.selectMenuById(menuId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==================== 유저 관리 ====================

    @Override
    public List<UserDTO> getUserList() {
        try {
            return adminDAO.selectAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserDTO getUserInfo(int userId) {
        try {
            return adminDAO.selectUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean forceDeleteUser(int userId, String reason) {
        try {
            UserDTO user = adminDAO.selectUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("존재하지 않는 유저입니다.");
            }

            // 유저 소유 매장 확인
            List<StoreDTO> userStores = adminDAO.selectStoresByOwnerId(userId);
            if (userStores != null && !userStores.isEmpty()) {
                // 매장이 있는 경우 모든 매장 삭제 처리
                for (StoreDTO store : userStores) {
                    adminDAO.deleteStore(store.getStoreId());
                }
            }

            int result = adminDAO.deleteUser(userId);

            if (result > 0) {
                sendNotification(userId, "계정이 관리자에 의해 강제 탈퇴 처리되었습니다. 사유: " + reason);
            }

            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== 매출 정보 ====================

    @Override
    public Map<String, Object> getStoreSalesInfo(int storeId, LocalDate startDate, LocalDate endDate) {
        try {
            Map<String, Object> salesReport = new HashMap<>();
            
            // 총 매출 조회
            long totalSales = adminDAO.sumStoreSales(storeId, startDate, endDate);
            
            // 총 주문 수 조회
            int totalOrders = adminDAO.countStoreOrders(storeId, startDate, endDate);
            
            // 평균 주문 금액 계산
            long averageOrderAmount = totalOrders > 0 ? totalSales / totalOrders : 0;
            
            // 일별 매출 조회
            List<Map<String, Object>> dailySales = adminDAO.selectDailySales(storeId, startDate, endDate);
            
            // 메뉴별 매출 조회
            List<Map<String, Object>> menuSales = adminDAO.selectMenuSales(storeId, startDate, endDate);
            
            salesReport.put("storeId", storeId);
            salesReport.put("startDate", startDate);
            salesReport.put("endDate", endDate);
            salesReport.put("totalSales", totalSales);
            salesReport.put("totalOrders", totalOrders);
            salesReport.put("averageOrderAmount", averageOrderAmount);
            salesReport.put("dailySales", dailySales);
            salesReport.put("menuSales", menuSales);
            
            return salesReport;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==================== 테이블별 QR 관리 ====================

    @Override
    public List<Map<String, Object>> getStoreTables(int storeId) {
        try {
            return adminDAO.selectStoreTables(storeId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean generateTableQRCode(int tableId) {
        try {
            Map<String, Object> table = adminDAO.selectTableById(tableId);
            if (table == null) {
                throw new IllegalArgumentException("존재하지 않는 테이블입니다.");
            }
            
            int storeId = (Integer) table.get("storeId");
            String tableNo = (String) table.get("tableNo");
            String storeName = (String) table.get("storeName");
            
            // QR 코드 데이터 생성 (테이블별 고유 URL)
            String qrPath = "https://yourapp.com/order?store=" + storeId + "&table=" + tableId;
            String qrImgSrc = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=" + 
                             encodeURIComponent(qrPath);
            
            int result = adminDAO.upsertTableQRCode(tableId, qrPath, qrImgSrc);
            
            if (result > 0) {
                System.out.println("✅ QR 코드 생성: " + storeName + " - 테이블 " + tableNo);
            }
            
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> getTableInfo(int tableId) {
        try {
            return adminDAO.selectTableById(tableId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getQRCodeByTableId(int tableId) {
        try {
            return adminDAO.selectQRCodeByTableId(tableId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteTableQRCode(int tableId) {
        try {
            // QR 코드가 존재하는지 확인
            if (!adminDAO.existsQRCode(tableId)) {
                throw new IllegalArgumentException("해당 테이블에 QR 코드가 존재하지 않습니다.");
            }
            
            int result = adminDAO.deleteTableQRCode(tableId);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== Helper Methods ====================

    private void sendNotification(int userId, String message) {
        System.out.println("📢 알림 전송 [User " + userId + "]: " + message);
    }

    private String encodeURIComponent(String component) {
        try {
            return java.net.URLEncoder.encode(component, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");
        } catch (Exception e) {
            return component;
        }
    }
}