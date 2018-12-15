package com.ibh.pocketpassword.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibh.pocketpassword.model.Category;
import com.ibh.pocketpassword.model.CategoryRepository;
import com.ibh.pocketpassword.viewmodel.CategoryVM;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categRepository;

	@Override
	public List<Category> getData() {
		return (List<Category>) categRepository.findAll();
	}

	@Override
	public List<CategoryVM> getVMData() {
		List<CategoryVM> vmlist = new ArrayList<>();
		categRepository.findAll().forEach(a -> {
			vmlist.add(CategoryVM.fromModel(a));
		});
		return vmlist;
	}

	@Override
	public Category fromVM(CategoryVM vm) {
		return new Category.Builder()
				.id(vm.getId())
				.name(vm.getName())
				.color(vm.getColor().toString())
				.create();
	}
	
	@Override
	public Category save(CategoryVM vm) {
		vm.validateModel();
		
		Category inst = fromVM(vm);
		
		return categRepository.save(inst);
	}

	@Override
	public void delete(CategoryVM vm) {
		Category inst = fromVM(vm);
		
		categRepository.delete(inst);
		
	}

	
}
