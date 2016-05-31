package fr.jstessier.patch.annotations;

import fr.jstessier.patch.validators.PatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {PatchValidator.class})
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Patch {

    String message() default "Invalid Patch";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
