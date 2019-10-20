package rs.ac.uns.ftn.informatika.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomConstraintValidator implements ConstraintValidator<CustomAnnotation, String> {

	@Override
	public void initialize(CustomAnnotation string) {

	}

	@Override
	public boolean isValid(String customField, ConstraintValidatorContext ctx) {

		if (customField == null) {
			return false;
		}
		return customField.matches("[a-z0-9_-]{3,15}");
	}

}
