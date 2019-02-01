package com.ibh.pocketpassword.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;
import com.ibh.pocketpassword.viewmodel.AuthUserPwdVM;

public interface AuthenticatonRepository extends CrudRepository<Authentication, Long> {

	@Query("select a.id, a.title, a.webUrl, a.description, a.validFrom, a.category from Authentication a")
	public List<AuthLimitedVM> getAuthLimited();

	@Query("select a.userName as userName, a.password as password from Authentication a where a.id = :id")
	public AuthUserPwdVM getAuthUserPwd(@Param("id") Long id);

}
