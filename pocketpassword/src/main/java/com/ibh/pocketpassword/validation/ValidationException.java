package com.ibh.pocketpassword.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  private final Set<ValidationError> validationError;

  public ValidationException(Set<ValidationError> validationError) {
    this.validationError = validationError;
  }

  public ValidationException(Set<ValidationError> validationError, String string) {
    super(string);
    this.validationError = validationError;
  }

  public ValidationException(Set<ValidationError> validationError, String string, Throwable thrwbl) {
    super(string, thrwbl);
    this.validationError = validationError;
  }

  public ValidationException(Set<ValidationError> validationError, Throwable thrwbl) {
    super(thrwbl);
    this.validationError = validationError;
  }

  public Set<ValidationError> getValidationError() {
    return validationError;
  }



}
