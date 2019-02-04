package com.ibh.pocketpassword.service;

import java.util.List;

public interface BaseService<T, U> extends BaseReadOnlyService<T, U> {
	List<T> getData();
	T fromVM(U vm);
	U fromEntity(T entity);
	T save(U vm);
	void delete(U vm);
}
