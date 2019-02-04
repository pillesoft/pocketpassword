package com.ibh.pocketpassword.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;
import com.ibh.pocketpassword.viewmodel.AuthUserPwdVM;

public interface AuthenticationRepository extends CrudRepository<Authentication, Long> {

	@Query("select new com.ibh.pocketpassword.viewmodel.AuthLimitedVM(a.id, a.title, a.webUrl, a.description, a.validFrom, a.category.name, a.category.color) from Authentication a")
	public List<AuthLimitedVM> getAuthLimitedList();

	@Query("select new com.ibh.pocketpassword.viewmodel.AuthLimitedVM(a.id, a.title, a.webUrl, a.description, a.validFrom, a.category.name, a.category.color) from Authentication a where a.id = :id")
	public AuthLimitedVM getAuthLimited(@Param("id") Long id);

	@Query("select a.userName as userName, a.password as password from Authentication a where a.id = :id")
	public AuthUserPwdVM getAuthUserPwd(@Param("id") Long id);

	@Query("select a.userName as userName, a.password as password from Authentication a")
	public List<AuthUserPwdVM> getAuthUserPwdList();

}
