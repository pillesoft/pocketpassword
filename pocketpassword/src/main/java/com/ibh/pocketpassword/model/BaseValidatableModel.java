package com.ibh.pocketpassword.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.internal.engine.path.PathImpl;

import com.ibh.pocketpassword.validation.VMValidation;
import com.ibh.pocketpassword.validation.ValidationException;

public abstract class BaseValidatableModel<T> extends BaseModel implements VMValidation {
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, List<String>> validationErrors;

	  public BaseValidatableModel() {
	    validationErrors = new HashMap<>();
	  }
	  
	  @Override
	  public HashMap<String, List<String>> getValidationErrors() {
	    return validationErrors;
	  }

	  @Override
	  public boolean isValid() {
	    return validationErrors.isEmpty();
	  }

	  protected void validate() throws ValidationException {
	    validationErrors = new HashMap<>();
	    
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
	      if (validationErrors.containsKey(propname)) {
	        validationErrors.get(propname).add(err.getMessage());
	      } else {
	        List<String> v = new ArrayList<>();
	        v.add(err.getMessage());
	        validationErrors.put(propname, v);
	      }
	    });
	  }

}
