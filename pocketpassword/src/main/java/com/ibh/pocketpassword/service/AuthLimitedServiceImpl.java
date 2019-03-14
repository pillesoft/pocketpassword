package com.ibh.pocketpassword.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibh.pocketpassword.model.Authentication;
import com.ibh.pocketpassword.model.AuthenticationRepository;
import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;

@Service
public class AuthLimitedServiceImpl implements AuthLimitedService {

	@Autowired
	private transient AuthenticationRepository authRepository;

	@Override
	public List<AuthLimitedVM> getVMData() {
		return authRepository.getAuthLimitedList();
	}

	@Override
	public AuthLimitedVM getVMById(Long id) {
		return authRepository.getAuthLimited(id);
	}

	@Override
	public Authentication getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
