package com.ibh.pocketpassword.service;

import java.util.List;

public interface BaseService<T, U> {
	List<T> getData();
	List<U> getVMData();
	T fromVM(U vm);
	T save(U vm);
	void delete(U vm);
}
