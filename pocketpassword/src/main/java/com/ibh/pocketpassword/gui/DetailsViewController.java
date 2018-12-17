package com.ibh.pocketpassword.gui;

public interface DetailsViewController<T> {
	void refresh(CRUDEnum mode, T vm);
}
