package com.ibh.pocketpassword.gui;

import com.ibh.pocketpassword.viewmodel.CategoryVM;

public interface CategoryDetailsViewController {

	void refresh(CRUDEnum mode, CategoryVM vm);
}
