package com.ibh.pocketpassword.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface ViewModelValidation {
  void validateModel() throws ValidationException;
  boolean isValid();
  Set<ValidationError> getValidationErrors();
}
