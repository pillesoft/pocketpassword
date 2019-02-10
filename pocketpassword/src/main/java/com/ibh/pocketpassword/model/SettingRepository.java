package com.ibh.pocketpassword.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SettingRepository extends CrudRepository<Setting, Long> {

	Optional<Setting> findByName(String name);

}
