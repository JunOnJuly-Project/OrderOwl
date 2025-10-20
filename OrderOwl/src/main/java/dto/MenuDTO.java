package dto;

import java.time.LocalTime;

public class MenuDTO {
	  private int menuId;
	    private int storeId;
	    private String menuName;
	    private int price;
	    private String description;
	    private String imgSrc;
	    private int category1Code;
	    private int category2Code;
	    private String checkRec;
	    private String orderRequest;
	    private LocalTime closeTime;
	    private String soldOut;
		public MenuDTO(int menuId, int storeId, String menuName, int price, String description, String imgSrc,
				int category1Code, int category2Code, String checkRec, String orderRequest, LocalTime closeTime,
				String soldOut) {
			super();
			this.menuId = menuId;
			this.storeId = storeId;
			this.menuName = menuName;
			this.price = price;
			this.description = description;
			this.imgSrc = imgSrc;
			this.category1Code = category1Code;
			this.category2Code = category2Code;
			this.checkRec = checkRec;
			this.orderRequest = orderRequest;
			this.closeTime = closeTime;
			this.soldOut = soldOut;
		}
		
		public MenuDTO(int menuId, int storeId, String menuName, int price, String description, String imgSrc,
				 String checkRec, String orderRequest, LocalTime closeTime,
				String soldOut) {
			super();
			this.menuId = menuId;
			this.storeId = storeId;
			this.menuName = menuName;
			this.price = price;
			this.description = description;
			this.imgSrc = imgSrc;
			this.checkRec = checkRec;
			this.orderRequest = orderRequest;
			this.closeTime = closeTime;
			this.soldOut = soldOut;
		}
		
		public MenuDTO(int menuId, int storeId, String menuName, int price, String description, String imgSrc,
				int category1Code, String checkRec, String orderRequest, LocalTime closeTime,
				String soldOut) {
			super();
			this.menuId = menuId;
			this.storeId = storeId;
			this.menuName = menuName;
			this.price = price;
			this.description = description;
			this.imgSrc = imgSrc;
			this.category1Code = category1Code;
			this.checkRec = checkRec;
			this.orderRequest = orderRequest;
			this.closeTime = closeTime;
			this.soldOut = soldOut;
		}
		
		public MenuDTO(int menuId, int storeId, String menuName, int price, String description, String imgSrc,
				 String checkRec, String orderRequest, LocalTime closeTime,int category2Code,
				String soldOut) {
			super();
			this.menuId = menuId;
			this.storeId = storeId;
			this.menuName = menuName;
			this.price = price;
			this.description = description;
			this.imgSrc = imgSrc;
			this.category2Code = category2Code;
			this.checkRec = checkRec;
			this.orderRequest = orderRequest;
			this.closeTime = closeTime;
			this.soldOut = soldOut;
		}
		
		
		public int getMenuId() {
			return menuId;
		}
		public void setMenuId(int menuId) {
			this.menuId = menuId;
		}
		public int getStoreId() {
			return storeId;
		}
		public void setStoreId(int storeId) {
			this.storeId = storeId;
		}
		public String getMenuName() {
			return menuName;
		}
		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getImgSrc() {
			return imgSrc;
		}
		public void setImgSrc(String imgSrc) {
			this.imgSrc = imgSrc;
		}
		public int getCategory1Code() {
			return category1Code;
		}
		public void setCategory1Code(int category1Code) {
			this.category1Code = category1Code;
		}
		public int getCategory2Code() {
			return category2Code;
		}
		public void setCategory2Code(int category2Code) {
			this.category2Code = category2Code;
		}
		public String getCheckRec() {
			return checkRec;
		}
		public void setCheckRec(String checkRec) {
			this.checkRec = checkRec;
		}
		public String getOrderRequest() {
			return orderRequest;
		}
		public void setOrderRequest(String orderRequest) {
			this.orderRequest = orderRequest;
		}
		public LocalTime getCloseTime() {
			return closeTime;
		}
		public void setCloseTime(LocalTime closeTime) {
			this.closeTime = closeTime;
		}
		public String getSoldOut() {
			return soldOut;
		}
		public void setSoldOut(String soldOut) {
			this.soldOut = soldOut;
		}
		@Override
		public String toString() {
			return "MenuDTO [menuId=" + menuId + ", storeId=" + storeId + ", menuName=" + menuName + ", price=" + price
					+ ", description=" + description + ", imgSrc=" + imgSrc + ", category1Code=" + category1Code
					+ ", category2Code=" + category2Code + ", checkRec=" + checkRec + ", orderRequest=" + orderRequest
					+ ", closeTime=" + closeTime + ", soldOut=" + soldOut + "]";
		}
		
}
