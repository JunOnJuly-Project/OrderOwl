package service;

import dto.AdminDTO.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 관리자 서비스 인터페이스
 */
public interface AdminService {
    
    // ==================== 전체 관리 ====================
    
    /**
     * 매장 추가
     */
    boolean addStore(StoreDTO store);
    
    /**
     * 매장 삭제 요청 (DELETE_PENDING 상태로 변경)
     * 실제 삭제는 approveStoreDeletion()에서 처리
     */
    boolean deleteStore(long storeId);
    
    /**
     * 삭제 대기 매장 목록 조회
     * DELETE_PENDING 상태인 매장들을 반환
     */
    List<StoreDTO> getDeletePendingStores();
    
    /**
     * 삭제 대기 매장 최종 승인
     * DELETE_PENDING 상태의 매장을 DB에서 완전히 삭제
     */
    boolean approveStoreDeletion(long storeId);
    
    /**
     * 삭제 대기 취소
     * DELETE_PENDING 상태를 ACTIVE로 복구
     */
    boolean cancelStoreDeletion(long storeId);
    
    /**
     * 유저 강제 탈퇴
     */
    boolean forceDeleteUser(long userId, String reason);
    
    // ==================== 매장 관리 ====================
    
    /**
     * 매장 QR 코드 생성
     */
    String createStoreQR(long storeId);
    
    /**
     * 메뉴 추가 요청 승인
     */
    boolean approveMenuAddRequest(long requestId);
    
    /**
     * 메뉴 수정 요청 승인
     */
    boolean approveMenuUpdateRequest(long requestId);
    
    /**
     * 메뉴 삭제 요청 승인
     */
    boolean approveMenuDeleteRequest(long requestId);
    
    /**
     * 매장 상세 정보 조회
     */
    StoreDTO getStoreInfo(long storeId);
    
    /**
     * 매장 매출 정보 조회
     */
    SalesReportDTO getStoreSalesInfo(long storeId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 매장 등록 요청 승인
     */
    boolean approveStoreInfoAddRequest(long requestId);
    
    /**
     * 매장 정보 수정 요청 승인
     */
    boolean approveStoreInfoUpdateRequest(long requestId);
    
    /**
     * 매장 등록 요청 거절
     */
    boolean rejectStoreInfoAddRequest(long requestId, String reason);
    
    /**
     * 매장 정보 수정 요청 거절
     */
    boolean rejectStoreInfoUpdateRequest(long requestId, String reason);
    
    /**
     * 전체 매장 목록 조회
     */
    List<StoreDTO> getStoreList();
    
    /**
     * 매장 요청 목록 조회
     */
    List<StoreRequestDTO> getStoreRequests();
    
    /**
     * 매장별 메뉴 목록 조회
     */
    List<MenuDTO> getStoreMenus(long storeId);

    /**
     * 메뉴 직접 수정 (요청 없이)
     */
    boolean updateMenuDirect(MenuDTO menu);

    /**
     * 메뉴 직접 삭제 (요청 없이)
     */
    boolean deleteMenuDirect(long menuId);

    /**
     * 메뉴 직접 추가 (요청 없이)
     */
    boolean addMenuDirect(MenuDTO menu);
    
    /**
     * 모든 매장의 QR 정보 조회
     */
    List<StoreDTO> getAllStoresWithQR();

    /**
     * 매장 QR 코드 재생성
     */
    boolean regenerateStoreQR(long storeId);
    
    /**
     * 매장 QR 경로 업데이트
     */
    boolean updateStoreQRPath(long storeId, String qrPath);
    
    /**
     * 매장 정보 직접 수정
     */
    boolean updateStoreInfo(StoreDTO store);
    
    /**
     * 전체 유저 목록 조회
     */
    List<UserDTO> getUserList();
    
    // ==================== 승인/거절 히스토리 ====================
    
    /**
     * 승인/거절 히스토리 조회 (페이징 지원)
     * @param type 타입 필터 (ALL, STORE, MENU)
     * @param sortOrder 정렬 순서 (ASC, DESC)
     * @param page 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 크기
     * @return 히스토리 목록과 전체 개수
     */
    Map<String, Object> getApprovalHistory(String type, String sortOrder, int page, int pageSize);
}