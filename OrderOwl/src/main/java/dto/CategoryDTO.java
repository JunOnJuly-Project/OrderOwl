package dto;

public class CategoryDTO {
int categoryId;
String categoryName;
int storeId;
String hasMenuId;
public CategoryDTO(int categoryId, String categoryName, int storeId, String hasMenuId) {
	super();
	this.categoryId = categoryId;
	this.categoryName = categoryName;
	this.storeId = storeId;
	this.hasMenuId = hasMenuId;
}
public int getCategoryId() {
	return categoryId;
}
public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}
public String getCategoryName() {
	return categoryName;
}
public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}
public int getStoreId() {
	return storeId;
}
public void setStoreId(int storeId) {
	this.storeId = storeId;
}
public String getHasMenuId() {
	return hasMenuId;
}
public void setHasMenuId(String hasMenuId) {
	this.hasMenuId = hasMenuId;
}



}
