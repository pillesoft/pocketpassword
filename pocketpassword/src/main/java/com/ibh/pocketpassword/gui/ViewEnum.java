package com.ibh.pocketpassword.gui;

public enum ViewEnum {
	Login("loginView"),
	AuthListView("AuthListView"),
	CategoryListView("CategoryListView"),
	AuthDetailsView("AuthDetailsView"),
	AuthCrudView("AuthCRUDView"),
	ChangePwdView("ChangePwdView");
	
	private final String viewFile;

	public String getViewFile() {
		return viewFile;
	}

	ViewEnum(String viewFile) {
		this.viewFile = viewFile;
	}

}
