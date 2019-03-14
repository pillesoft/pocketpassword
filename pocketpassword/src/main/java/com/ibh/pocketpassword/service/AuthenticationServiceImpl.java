package com.ibh.pocketpassword.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibh.pocketpassword.helper.CryptHelper;
import com.ibh.pocketpassword.model.AuthPwdHistory;
import com.ibh.pocketpassword.model.AuthPwdHistoryRepository;
import com.ibh.pocketpassword.model.Authentication;
import com.ibh.pocketpassword.model.AuthenticationRepository;
import com.ibh.pocketpassword.viewmodel.AuthPwdHistoryVM;
import com.ibh.pocketpassword.viewmodel.AuthenticationVM;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationRepository authRepository;
	
	@Autowired
	private AuthPwdHistoryService authPwdHistoryService;
	
	@Override
	public List<Authentication> getData() {
		return (List<Authentication>) authRepository.findAll();
	}

	@Override
	public List<AuthenticationVM> getVMData() {
		List<AuthenticationVM> vmlist = new ArrayList<>();
		authRepository.findAll().forEach(vm -> {
			vmlist.add(fromEntity(vm));
		});
		return vmlist;
	}

	@Override
	public AuthenticationVM getVMById(Long id) {
		return fromEntity(getById(id));
	}

	@Override
	public Authentication fromVM(AuthenticationVM vm) {
		return new Authentication.Builder()
				.id(vm.getId().get())
				.title(vm.getTitle().get())
				.userName(CryptHelper.encrypt(vm.getUserName().get()))
				.password(CryptHelper.encrypt(vm.getPassword().get()))
				.category(vm.getCategory().get())
				.webUrl(vm.getWebUrl().get())
				.validFrom(vm.getValidFrom().get())
				.description(vm.getDescription().get())
				.create();
	}
	
	@Override
	public AuthenticationVM fromEntity(Authentication entity) {
		return new AuthenticationVM(entity.getId(), entity.getTitle(), entity.getCategory(), 
				CryptHelper.decrypt(entity.getUsername()), 
				CryptHelper.decrypt(entity.getPassword()), 
				entity.getWeburl(), entity.getDescription(), entity.getValidfrom());
	}
	
	@Override
	public Authentication save(AuthenticationVM vm) {
		Authentication inst = fromVM(vm);
		inst.validateModel();

		Long id = vm.getId().get();
		if (id != 0) {
			AuthenticationVM orig = getVMById(id);
			if (!orig.getPassword().get().equals(vm.getPassword().get())) {
				authPwdHistoryService.save(new AuthPwdHistoryVM(inst, orig.getPassword().get()));
			}
		}
		return authRepository.save(inst);
	}

	@Override
	public void delete(AuthenticationVM vm) {
		Authentication inst = fromVM(vm);
		
		authRepository.delete(inst);
		
	}

	@Override
	public Authentication getById(Long id) {
		return authRepository.findById(id).get();
	}

	
}
