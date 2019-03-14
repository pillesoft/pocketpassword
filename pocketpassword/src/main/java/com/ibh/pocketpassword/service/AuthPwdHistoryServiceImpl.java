package com.ibh.pocketpassword.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibh.pocketpassword.helper.CryptHelper;
import com.ibh.pocketpassword.model.AuthPwdHistory;
import com.ibh.pocketpassword.model.AuthPwdHistoryRepository;
import com.ibh.pocketpassword.model.Authentication;
import com.ibh.pocketpassword.viewmodel.AuthPwdHistoryVM;

@Service
public class AuthPwdHistoryServiceImpl implements AuthPwdHistoryService {

	@Autowired
	private AuthPwdHistoryRepository authPwdHistoryRepository;

	@Override
	public List<AuthPwdHistoryVM> getVMData() {
		return null;
	}

	@Override
	public List<AuthPwdHistoryVM> getVMDataByAuth(Authentication auth) {
		List<AuthPwdHistoryVM> vmlist = new ArrayList<>();
		authPwdHistoryRepository.findByAuthenticationOrderByExpiredDesc(auth).forEach(a->{
			vmlist.add(fromEntity(a));
		}); 
		return vmlist;
	}
	
	@Override
	public AuthPwdHistoryVM getVMById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthPwdHistory> getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthPwdHistory fromVM(AuthPwdHistoryVM vm) {
		return new AuthPwdHistory.Builder().create(vm.getAuthentication().get(), CryptHelper.encrypt(vm.getPassword().get()));
	}

	@Override
	public AuthPwdHistoryVM fromEntity(AuthPwdHistory entity) {
		return new AuthPwdHistoryVM(entity.getExpired(), CryptHelper.decrypt(entity.getPassword()));
	}

	@Override
	public AuthPwdHistory save(AuthPwdHistoryVM vm) {
		AuthPwdHistory inst = fromVM(vm); 
		return authPwdHistoryRepository.save(inst);
	}

	@Override
	public void delete(AuthPwdHistoryVM vm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AuthPwdHistory getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
