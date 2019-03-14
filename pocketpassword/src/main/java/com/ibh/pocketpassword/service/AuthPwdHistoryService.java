package com.ibh.pocketpassword.service;


import java.util.List;

import com.ibh.pocketpassword.model.AuthPwdHistory;
import com.ibh.pocketpassword.model.Authentication;
import com.ibh.pocketpassword.viewmodel.AuthPwdHistoryVM;

public interface AuthPwdHistoryService extends BaseService<AuthPwdHistory, AuthPwdHistoryVM> {
	List<AuthPwdHistoryVM> getVMDataByAuth(Authentication auth);
}
