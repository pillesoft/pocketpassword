/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibh.pocketpassword.gui;

public enum ViewEnum {
	Login("loginView"),
	AuthListView("AuthListView"),
	CategoryListView("CategoryListView"),
	AuthDetailsView("AuthDetailsView"),
	AuthCrudView("AuthCRUDView");

	private final String viewFile;

	public String getViewFile() {
		return viewFile;
	}

	ViewEnum(String viewFile) {
		this.viewFile = viewFile;
	}

}
