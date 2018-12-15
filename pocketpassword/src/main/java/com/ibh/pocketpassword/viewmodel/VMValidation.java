package com.ibh.pocketpassword.viewmodel;

import java.util.HashMap;
import java.util.List;


public interface VMValidation {
  void validateModel() throws ValidationException;
  boolean isValid();
  HashMap<String, List<String>> getValidationErrors();
}
