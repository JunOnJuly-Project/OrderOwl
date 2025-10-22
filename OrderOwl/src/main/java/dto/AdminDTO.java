package dto;

import java.time.LocalDate;
import java.util.List;

/**
 * 관리자 기능 관련 DTO 통합 파일
 */
public class AdminDTO {
    
    // ==================== StoreDTO ====================
    public static class StoreDTO {
        private long storeId;
        private long ownerId;
        private String storeName;
        private String businessNumber;
        private String address;
        private String region;
        private String phoneNumber;
        private String description;
        private String imgSrc;
        private String qrPath;
        private String status;
        private boolean businessVerified;
        
        // 통계 정보
        private int menuCount;
        private int totalOrders;
        private long totalSales;
        
        public StoreDTO() {}
        
        // Getters and Setters
        public long getStoreId() { return storeId; }
        public void setStoreId(long storeId) { this.storeId = storeId; }
        public long getOwnerId() { return ownerId; }
        public void setOwnerId(long ownerId) { this.ownerId = ownerId; }
        public String getStoreName() { return storeName; }
        public void setStoreName(String storeName) { this.storeName = storeName; }
        public String getBusinessNumber() { return businessNumber; }
        public void setBusinessNumber(String businessNumber) { this.businessNumber = businessNumber; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getImgSrc() { return imgSrc; }
        public void setImgSrc(String imgSrc) { this.imgSrc = imgSrc; }
        public String getQrPath() { return qrPath; }
        public void setQrPath(String qrPath) { this.qrPath = qrPath; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public boolean isBusinessVerified() { return businessVerified; }
        public void setBusinessVerified(boolean businessVerified) { this.businessVerified = businessVerified; }
        public int getMenuCount() { return menuCount; }
        public void setMenuCount(int menuCount) { this.menuCount = menuCount; }
        public int getTotalOrders() { return totalOrders; }
        public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }
        public long getTotalSales() { return totalSales; }
        public void setTotalSales(long totalSales) { this.totalSales = totalSales; }
    }
    
    // ==================== UserDTO ====================
    public static class UserDTO {
        private long userId;
        private String name;
        private String email;
        private String role;
        private String status;
        
        public UserDTO() {}
        
        // Getters and Setters
        public long getUserId() { return userId; }
        public void setUserId(long userId) { this.userId = userId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
    
    // ==================== MenuDTO ====================
    public static class MenuDTO {
        private long menuId;
        private long storeId;
        private String menuName;
        private int price;
        private String category;
        private String description;
        private String imgSrc;
        
        public MenuDTO() {}
        
        // Getters and Setters
        public long getMenuId() { return menuId; }
        public void setMenuId(long menuId) { this.menuId = menuId; }
        public long getStoreId() { return storeId; }
        public void setStoreId(long storeId) { this.storeId = storeId; }
        public String getMenuName() { return menuName; }
        public void setMenuName(String menuName) { this.menuName = menuName; }
        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getImgSrc() { return imgSrc; }
        public void setImgSrc(String imgSrc) { this.imgSrc = imgSrc; }
    }
    
    // ==================== MenuRequestDTO ====================
    public static class MenuRequestDTO {
        private long requestId;
        private long menuId;
        private long storeId;
        private long ownerId;
        private String requestType;  // ADD, UPDATE, DELETE
        private String menuName;
        private int price;
        private String category;
        private String description;
        private String status;
        private String createdAt;  // 생성 날짜/시간
        
        public MenuRequestDTO() {}
        
        // Getters and Setters
        public long getRequestId() { return requestId; }
        public void setRequestId(long requestId) { this.requestId = requestId; }
        public long getMenuId() { return menuId; }
        public void setMenuId(long menuId) { this.menuId = menuId; }
        public long getStoreId() { return storeId; }
        public void setStoreId(long storeId) { this.storeId = storeId; }
        public long getOwnerId() { return ownerId; }
        public void setOwnerId(long ownerId) { this.ownerId = ownerId; }
        public String getRequestType() { return requestType; }
        public void setRequestType(String requestType) { this.requestType = requestType; }
        public String getMenuName() { return menuName; }
        public void setMenuName(String menuName) { this.menuName = menuName; }
        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }
    
    // ==================== StoreRequestDTO ====================
    public static class StoreRequestDTO {
        private long requestId;
        private long storeId;
        private long ownerId;
        private String storeName;
        private String businessNumber;
        private String address;
        private String phoneNumber;
        private String requestType;  // ADD, UPDATE
        private String status;
        private String createdAt;  // 생성 날짜/시간
        
        public StoreRequestDTO() {}
        
        // Getters and Setters
        public long getRequestId() { return requestId; }
        public void setRequestId(long requestId) { this.requestId = requestId; }
        public long getStoreId() { return storeId; }
        public void setStoreId(long storeId) { this.storeId = storeId; }
        public long getOwnerId() { return ownerId; }
        public void setOwnerId(long ownerId) { this.ownerId = ownerId; }
        public String getStoreName() { return storeName; }
        public void setStoreName(String storeName) { this.storeName = storeName; }
        public String getBusinessNumber() { return businessNumber; }
        public void setBusinessNumber(String businessNumber) { this.businessNumber = businessNumber; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getRequestType() { return requestType; }
        public void setRequestType(String requestType) { this.requestType = requestType; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }
    
    // ==================== SalesReportDTO ====================
    public static class SalesReportDTO {
        private long storeId;
        private LocalDate startDate;
        private LocalDate endDate;
        private long totalSales;
        private int totalOrders;
        private long averageOrderAmount;
        private List<DailySalesDTO> dailySales;
        private List<MenuSalesDTO> menuSales;
        
        public SalesReportDTO() {}
        
        // Getters and Setters
        public long getStoreId() { return storeId; }
        public void setStoreId(long storeId) { this.storeId = storeId; }
        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
        public long getTotalSales() { return totalSales; }
        public void setTotalSales(long totalSales) { this.totalSales = totalSales; }
        public int getTotalOrders() { return totalOrders; }
        public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }
        public long getAverageOrderAmount() { return averageOrderAmount; }
        public void setAverageOrderAmount(long averageOrderAmount) { this.averageOrderAmount = averageOrderAmount; }
        public List<DailySalesDTO> getDailySales() { return dailySales; }
        public void setDailySales(List<DailySalesDTO> dailySales) { this.dailySales = dailySales; }
        public List<MenuSalesDTO> getMenuSales() { return menuSales; }
        public void setMenuSales(List<MenuSalesDTO> menuSales) { this.menuSales = menuSales; }
    }
    
    // ==================== DailySalesDTO ====================
    public static class DailySalesDTO {
        private LocalDate saleDate;
        private long dailyTotal;
        
        public DailySalesDTO() {}
        
        // Getters and Setters
        public LocalDate getSaleDate() { return saleDate; }
        public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }
        public long getDailyTotal() { return dailyTotal; }
        public void setDailyTotal(long dailyTotal) { this.dailyTotal = dailyTotal; }
    }
    
    // ==================== MenuSalesDTO ====================
    public static class MenuSalesDTO {
        private String menuName;
        private int totalQuantity;
        private long totalSales;
        
        public MenuSalesDTO() {}
        
        // Getters and Setters
        public String getMenuName() { return menuName; }
        public void setMenuName(String menuName) { this.menuName = menuName; }
        public int getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }
        public long getTotalSales() { return totalSales; }
        public void setTotalSales(long totalSales) { this.totalSales = totalSales; }
    }
}