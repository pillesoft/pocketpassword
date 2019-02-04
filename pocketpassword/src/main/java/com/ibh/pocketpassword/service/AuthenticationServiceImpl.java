package com.ibh.pocketpassword.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibh.pocketpassword.model.Authentication;
import com.ibh.pocketpassword.model.AuthenticationRepository;
import com.ibh.pocketpassword.viewmodel.AuthenticationVM;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationRepository authRepository;

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
		return fromEntity(authRepository.findById(id).get());
	}

	@Override
	public Authentication fromVM(AuthenticationVM vm) {
		return new Authentication.Builder()
				.id(vm.getId().get())
				.title(vm.getTitle().get())
				.userName(vm.getUserName().get())
				.password(vm.getPassword().get())
				.category(vm.getCategory().get())
				.webUrl(vm.getWebUrl().get())
				.validFrom(vm.getValidFrom().get())
				.description(vm.getDescription().get())
				.create();
	}
	
	@Override
	public AuthenticationVM fromEntity(Authentication entity) {
		return new AuthenticationVM(entity.getId(), entity.getTitle(), entity.getCategory(), entity.getUsername(), entity.getPassword(), entity.getWeburl(), entity.getDescription(), entity.getValidfrom());
	}
	
	@Override
	public Authentication save(AuthenticationVM vm) {
		Authentication inst = fromVM(vm);
		inst.validateModel();
		
		return authRepository.save(inst);
	}

	@Override
	public void delete(AuthenticationVM vm) {
		Authentication inst = fromVM(vm);
		
		authRepository.delete(inst);
		
	}

	
}
