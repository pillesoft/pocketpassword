package com.ibh.pocketpassword.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.internal.engine.path.PathImpl;

import com.ibh.pocketpassword.validation.ViewModelValidation;
import com.ibh.pocketpassword.validation.ValidationError;
import com.ibh.pocketpassword.validation.ValidationException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

public abstract class BaseViewModel<T> implements ViewModelValidation {

	private Set<ValidationError> validationErrors;
//	private Map<String, Control> boundedControls;

	public BaseViewModel() {
		validationErrors = new HashSet<>();
//		boundedControls = new HashMap<>();
	}

	@Override
	public Set<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	@Override
	public boolean isValid() {
		return validationErrors.isEmpty();
	}

	protected void validate() throws ValidationException {
		validationErrors = new HashSet<>();

		ValidatorFactory valfact = Validation.buildDefaultValidatorFactory();
		Validator validator = valfact.getValidator();
		Set<ConstraintViolation<BaseViewModel<T>>> errors = validator.validate(this);

		convertErrors(errors);
		if (!validationErrors.isEmpty()) {
			throw new ValidationException(validationErrors);
		}
	}

	private void convertErrors(Set<ConstraintViolation<BaseViewModel<T>>> errors) {
		errors.stream().forEach((err) -> {
			String propname = ((PathImpl) err.getPropertyPath()).getLeafNode().getName();
			ValidationError newve = new ValidationError(propname, err.getMessage());
			if (validationErrors.contains(newve)) {
				ValidationError ve = validationErrors.stream().filter((p) -> p.equals(newve)).findFirst().get();
				ve.addErrorMessage(err.getMessage());
				validationErrors.add(ve);
			} else {
				validationErrors.add(newve);
			}

//      if (validationErrors.containsKey(propname)) {
//        validationErrors.get(propname).add(err.getMessage());
//      } else {
//        List<String> v = new ArrayList<>();
//        v.add(err.getMessage());
//        validationErrors.put(propname, v);
//      }
		});
	}

}
