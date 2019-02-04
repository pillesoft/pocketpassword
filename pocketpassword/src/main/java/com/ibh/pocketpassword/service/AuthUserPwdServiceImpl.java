package com.ibh.pocketpassword.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibh.pocketpassword.model.AuthenticationRepository;
import com.ibh.pocketpassword.viewmodel.AuthUserPwdVM;


@Service
public class AuthUserPwdServiceImpl implements AuthUserPwdService {

	@Autowired
	private transient AuthenticationRepository authRepository;

	@Override
	public List<AuthUserPwdVM> getVMData() {
		return authRepository.getAuthUserPwdList();
	}

	@Override
	public AuthUserPwdVM getVMById(Long id) {
		return authRepository.getAuthUserPwd(id);
	}



}
