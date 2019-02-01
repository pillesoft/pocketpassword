package com.ibh.pocketpassword.service;

import com.ibh.pocketpassword.model.Authentication;
import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;
import com.ibh.pocketpassword.viewmodel.AuthUserPwdVM;

public interface AuthenticationService extends BaseService<Authentication, AuthLimitedVM> {
	AuthUserPwdVM getUserPwd(Long id);
}
