package com.ibh.pocketpassword.model;

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

import javafx.scene.control.Control;

import com.ibh.pocketpassword.validation.ValidationError;
import com.ibh.pocketpassword.validation.ValidationException;

public abstract class BaseValidatableModel<T> extends BaseModel implements ViewModelValidation {
	
	private static final long serialVersionUID = 1L;
	private Set<ValidationError> validationErrors;
	  private Map<String, Control> boundedControls;

	  public BaseValidatableModel() {
	    validationErrors = new HashSet<ValidationError>();
	    boundedControls = new HashMap<>();
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
	    validationErrors = new HashSet<ValidationError>();
	    
	    ValidatorFactory valfact = Validation.buildDefaultValidatorFactory();
	    Validator validator = valfact.getValidator();
	    Set<ConstraintViolation<BaseValidatableModel<T>>> errors = validator.validate(this);

	    convertErrors(errors);
	    if (!validationErrors.isEmpty()) {
	      throw new ValidationException(validationErrors);
	    }
	  }

	  private void convertErrors(Set<ConstraintViolation<BaseValidatableModel<T>>> errors) {
	    errors.stream().forEach((err) -> {
	      String propname = ((PathImpl) err.getPropertyPath()).getLeafNode().getName();
	      
	      ValidationError ve = new ValidationError(propname, err.getMessage());
	      if (validationErrors.contains(ve)) {
	    	  ve.addErrorMessage(err.getMessage());
	      } else {
	    	  validationErrors.add(ve);
	      }
	      
//	      if (validationErrors.containsKey(propname)) {
//	        validationErrors.get(propname).add(err.getMessage());
//	      } else {
//	        List<String> v = new ArrayList<>();
//	        v.add(err.getMessage());
//	        validationErrors.put(propname, v);
//	      }
	    });
	  }

}
