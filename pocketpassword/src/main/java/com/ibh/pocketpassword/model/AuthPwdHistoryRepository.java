package com.ibh.pocketpassword.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ibh.pocketpassword.viewmodel.AuthLimitedVM;
import com.ibh.pocketpassword.viewmodel.AuthUserPwdVM;

public interface AuthPwdHistoryRepository extends CrudRepository<AuthPwdHistory, Long> {

}
