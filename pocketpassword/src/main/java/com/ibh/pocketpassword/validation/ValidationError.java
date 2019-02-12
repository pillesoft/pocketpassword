package com.ibh.pocketpassword.validation;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Control;

public final class ValidationError {

	private final String fieldName;
//	private final Control boundedControl; 
	private final List<String> errorMessages;
	
	public ValidationError(String fieldName, String errorMessage) {
		super();
		this.fieldName = fieldName;
//		this.boundedControl = boundedControl;
		this.errorMessages = new ArrayList<>();
		this.errorMessages.add(errorMessage);
	}

	public void addErrorMessage(String errorMessage) {
		this.errorMessages.add(errorMessage);
	}
	
	public String getFieldName() {
		return fieldName;
	}

//	public Control getBoundedControl() {
//		return boundedControl;
//	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidationError other = (ValidationError) obj;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		return true;
	}
	
	
}
