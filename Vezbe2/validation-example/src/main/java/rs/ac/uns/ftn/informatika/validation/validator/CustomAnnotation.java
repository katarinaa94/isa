package rs.ac.uns.ftn.informatika.validation.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CustomConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotation {

	String message() default "Field must contain at least 3 characters";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
