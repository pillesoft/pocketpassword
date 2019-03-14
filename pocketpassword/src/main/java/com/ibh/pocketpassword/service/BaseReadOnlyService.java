package com.ibh.pocketpassword.service;

import java.util.List;

public interface BaseReadOnlyService<T, U> {
	List<U> getVMData();
	U getVMById(Long id);
	T getById(Long id);
}
