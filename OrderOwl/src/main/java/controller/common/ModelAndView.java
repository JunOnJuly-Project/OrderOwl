package controller.common;

public class ModelAndView {
	private String viewName;
	private boolean isRedirect=false;
	
	
	public ModelAndView(String viewName, boolean isRedirect) {

		this.viewName = viewName;
		this.isRedirect = isRedirect;
	}
	public ModelAndView(String viewName) {
		this.viewName = viewName;
	
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	
	
	

}
