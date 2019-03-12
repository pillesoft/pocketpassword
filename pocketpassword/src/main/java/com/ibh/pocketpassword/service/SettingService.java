package com.ibh.pocketpassword.service;

import java.util.Optional;

import com.ibh.pocketpassword.model.Setting;
import com.ibh.pocketpassword.viewmodel.SettingVM;

public interface SettingService extends BaseService<Setting, SettingVM> {

	Optional<Setting> findByName(String name);
	String getDbCreateTimestamp();
	void initDB();
}
