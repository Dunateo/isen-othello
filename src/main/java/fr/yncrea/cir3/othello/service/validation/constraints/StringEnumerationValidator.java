package fr.yncrea.cir3.othello.service.validation.constraints;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringEnumerationValidator implements ConstraintValidator<StringEnumeration, String> {
	private Set<String> allowedValues;
	
	public void initialize(StringEnumeration targetEnum) {
		allowedValues = new HashSet<>();
		Class<? extends Enum<?>> enumClass = targetEnum.enumClass();
		Enum<?>[] enums = enumClass.getEnumConstants();
		
		for (int cpt = 0; cpt < enums.length; cpt++) {
			allowedValues.add(enums[cpt].name());
		}
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		
		return allowedValues.contains(value);
	}
}
