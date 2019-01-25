package com.ibh.pocketpassword.service;

import java.util.List;

public interface BaseService<T, U> {
	List<T> getData();
	List<U> getVMData();
	U getVMById(Long id);
	T fromVM(U vm);
	U fromModel(T model);
	T save(U vm);
	void delete(U vm);
}
