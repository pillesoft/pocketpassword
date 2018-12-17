package com.ibh.pocketpassword.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;

public interface AuthenticatonRepository extends CrudRepository<Authentication, Long> {

	@Query("select a.id, a.title, a.webUrl, a.description, a.validFrom, a.category from Authentication a")
	public List<AuthLimitedVM> getAuthLimited();
}
