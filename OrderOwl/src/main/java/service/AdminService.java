package service;

import dto.StoreDTO;
import dto.UserDTO;
import dto.MenuDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 관리자 서비스 인터페이스
 * OrderOwl.sql 스키마 기준 리팩토링
 */
public interface AdminService {
    
    // ==================== 매장 관리 ====================
    
    /**
     * 매장 추가
     */
    boolean addStore(StoreDTO store);
    
    /**
     * 매장 삭제
     */
    boolean deleteStore(int storeId);
    
    /**
     * 매장 상세 정보 조회
     */
    StoreDTO getStoreInfo(int storeId);
    
    /**
     * 전체 매장 목록 조회
     */
    List<StoreDTO> getStoreList();
    
    /**
     * 매장 정보 직접 수정
     */
    boolean updateStoreInfo(StoreDTO store);
    
    /**
     * 업주 ID로 매장 목록 조회
     */
    List<StoreDTO> getStoreListByOwner(int ownerId);
    
    // ==================== 메뉴 관리 ====================
    
    /**
     * 매장별 메뉴 목록 조회
     */
    List<MenuDTO> getStoreMenus(int storeId);
    
    /**
     * 메뉴 추가
     */
    boolean addMenu(MenuDTO menu);

    boolean updateMenuDirect(MenuDTO menu);
    
    /**
     * 메뉴 직접 삭제 (요청 없이)
     */
    boolean deleteMenuDirect(int menuId);
    
    /**
     * 메뉴 상세 정보 조회
     */
    MenuDTO getMenuInfo(int menuId);
    
    // ==================== 유저 관리 ====================
    
    /**
     * 전체 유저 목록 조회
     */
    List<UserDTO> getUserList();
    
    /**
     * 유저 상세 정보 조회
     */
    UserDTO getUserInfo(int userId);
    
    /**
     * 유저 강제 탈퇴
     */
    boolean forceDeleteUser(int userId, String reason);
    
    // ==================== 매출 정보 ====================
    
    /**
     * 매장 매출 정보 조회
     */
    Map<String, Object> getStoreSalesInfo(int storeId, LocalDate startDate, LocalDate endDate);
    
    // ==================== 테이블별 QR 관리 ====================
    
    /**
     * 매장 테이블 목록 조회
     */
    List<Map<String, Object>> getStoreTables(int storeId);
    
    /**
     * 테이블별 QR 코드 생성/업데이트
     */
    boolean generateTableQRCode(int tableId);
    
    /**
     * 테이블 정보 조회
     */
    Map<String, Object> getTableInfo(int tableId);
    
    /**
     * 테이블별 QR 코드 조회
     */
    Map<String, Object> getQRCodeByTableId(int tableId);
    
    /**
     * 테이블별 QR 코드 삭제
     */
    boolean deleteTableQRCode(int tableId);
}