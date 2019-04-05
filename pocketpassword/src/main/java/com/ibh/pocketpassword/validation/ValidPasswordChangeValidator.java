package com.ibh.pocketpassword.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.ibh.pocketpassword.viewmodel.LoginVM;

public class ValidPasswordChangeValidator implements ConstraintValidator<ValidPasswordChange, LoginVM> {

	@Override
	public boolean isValid(LoginVM vm, ConstraintValidatorContext ctx) {
		boolean isValid = false;
		List<String> msg = new ArrayList<>();
		if(vm == null) {
			return true;
		}
		
		if(vm.getPasswordChange().get()) {
			if(StringUtils.isEmpty(vm.getPasswordNew().get())) {
				msg.add("New Password is obligatory");
			}
			if(vm.getPasswordNew().get().length() < 6) {
				msg.add("Too short. Minimum length is 6 characters");
			}
			if(vm.getPassword().get().equals(vm.getPasswordNew().get())) {
				msg.add("Password and New Password cannot be the same");
			}
			isValid = msg.isEmpty();
		} else {
			isValid = true;
		}
	  
		if ( !isValid ) {
      ctx.disableDefaultConstraintViolation();
      ctx.buildConstraintViolationWithTemplate(String.join(System.lineSeparator(), msg))
      .addPropertyNode("passwordNew")
      .addConstraintViolation();
  }
	  
		return isValid;
	}

}
