package com.ibh.pocketpassword.viewmodel;

import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SettingVM extends BaseViewModel<SettingVM> {

	private LongProperty id;
	private StringProperty name;
	private StringProperty value;
	
	public SettingVM(Long id, String name, String value) {
		super();
		this.id = new SimpleLongProperty(null, "id", id);
		this.name = new SimpleStringProperty(null, "name", name);
		this.value = new SimpleStringProperty(null, "value", value);
	}

	public SettingVM(String name, String value) {
		super();
		this.id = new SimpleLongProperty(null, "id", 0);
		this.name = new SimpleStringProperty(null, "name", name);
		this.value = new SimpleStringProperty(null, "value", value);
	}
	

	public LongProperty getId() {
		return id;
	}



	public void setId(LongProperty id) {
		this.id = id;
	}



	public StringProperty getName() {
		return name;
	}



	public void setName(StringProperty name) {
		this.name = name;
	}



	public StringProperty getValue() {
		return value;
	}



	public void setValue(StringProperty value) {
		this.value = value;
	}



	@Override
	public void validateModel() throws ValidationException {
		super.validate();
	}

}
