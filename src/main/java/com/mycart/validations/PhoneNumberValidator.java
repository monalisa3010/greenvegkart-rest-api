package com.mycart.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mycart.model.UserDetails;

public class PhoneNumberValidator implements ConstraintValidator<Phone, UserDetails> {

	@Override
	public void initialize(Phone constraintAnnotation) {

	}

	@Override
	public boolean isValid(UserDetails userDetails, ConstraintValidatorContext context) {

		if (String.valueOf(userDetails.getContact()).length() != 10) {
			return false;
		}
		return true;
	}
}
