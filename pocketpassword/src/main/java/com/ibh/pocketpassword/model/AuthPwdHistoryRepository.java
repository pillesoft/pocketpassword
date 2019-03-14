package com.ibh.pocketpassword.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ibh.pocketpassword.viewmodel.AuthPwdHistoryVM;

public interface AuthPwdHistoryRepository extends CrudRepository<AuthPwdHistory, Long> {
//	@Query(value = "select new com.ibh.pocketpassword.viewmodel.AuthPwdHistoryVM(aph.expired, aph.password) from AuthPwdHistory aph order by aph.expired desc")
//	List<AuthPwdHistoryVM> getPwdHistoryVM();
	
	List<AuthPwdHistory> findByAuthenticationOrderByExpiredDesc(Authentication auth);
	
}
