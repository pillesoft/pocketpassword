package com.ibh.pocketpassword.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibh.pocketpassword.model.Authentication;
import com.ibh.pocketpassword.model.AuthenticatonRepository;
import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;

@Service
public class AuthenticationServcieImpl implements AuthenticationService {

	@Autowired
	private transient AuthenticatonRepository authRepository;
	
	@Override
	public List<Authentication> getData() {
		return (List<Authentication>)authRepository.findAll();
	}

	@Override
	public List<AuthLimitedVM> getVMData() {
		List<AuthLimitedVM> vmlist = new ArrayList<>();
		getData().forEach(vm -> {
			vmlist.add(fromModel(vm));
		});
		return vmlist;	
	}

	@Override
	public AuthLimitedVM getVMById(Long id) {
		return fromModel(authRepository.findById(id).get());
	}

	@Override
	public Authentication fromVM(AuthLimitedVM vm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthLimitedVM fromModel(Authentication model) {
		return new AuthLimitedVM(model.getId(), model.getTitle(), model.getCategory().getName(), model.getWeburl(), model.getDescription(), model.getValidfrom(), model.getCategory().getColor());
	}
	
	@Override
	public Authentication save(AuthLimitedVM vm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(AuthLimitedVM vm) {
		// TODO Auto-generated method stub
		
	}

}
