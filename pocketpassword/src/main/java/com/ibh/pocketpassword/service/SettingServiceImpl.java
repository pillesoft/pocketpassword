package com.ibh.pocketpassword.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibh.pocketpassword.model.Setting;
import com.ibh.pocketpassword.model.SettingRepository;
import com.ibh.pocketpassword.viewmodel.SettingVM;

@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private transient SettingRepository settRepository;
	
	@Override
	public List<Setting> getData() {
		return (List<Setting>)settRepository.findAll();
	}

	@Override
	public Setting fromVM(SettingVM vm) {
		return new Setting.Builder()
				.id(vm.getId().get())
				.name(vm.getName().get())
				.value(vm.getValue().get())
				.create();
	}

	@Override
	public SettingVM fromEntity(Setting entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Setting save(SettingVM vm) {
		Setting inst = fromVM(vm);
		inst.validateModel();
		
		return settRepository.save(inst);
	}

	@Override
	public void delete(SettingVM vm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SettingVM> getVMData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SettingVM getVMById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Setting> findByName(String name) {
		return settRepository.findByName(name);
	}

	@Override
	public String getDbCreateTimestamp() {
		Optional<Setting> optSett = findByName("DBCREATETIMESTAMP");
		
		if (optSett.isPresent()) {
			return optSett.get().getValue();
		} else {
			return save(new SettingVM("DBCREATETIMESTAMP", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))).getValue();
		}
		
	}

}
