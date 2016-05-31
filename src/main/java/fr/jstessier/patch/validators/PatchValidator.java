package fr.jstessier.patch.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatchValidator implements ConstraintValidator<fr.jstessier.patch.annotations.Patch, fr.jstessier.patch.models.Patch> {

    @Override
    public void initialize(fr.jstessier.patch.annotations.Patch constraintAnnotation) {
        // NOP
    }

    @Override
    public boolean isValid(fr.jstessier.patch.models.Patch value, ConstraintValidatorContext context) {
        if (value == null || value.getOperation() == null) {
            return false;
        }
        switch (value.getOperation()) {
            case ADD:
                return isValidAddOrReplaceOrTest(value, context);
            case REMOVE:
                return  isValidRemove(value, context);
            case REPLACE:
                return isValidAddOrReplaceOrTest(value, context);
            case MOVE:
                return isValidCopyOrMove(value, context);
            case COPY:
                return isValidCopyOrMove(value, context);
            case TEST:
                return isValidAddOrReplaceOrTest(value, context);
            default:
                throw new UnsupportedOperationException();
        }
    }

    protected boolean isValidRemove(fr.jstessier.patch.models.Patch value, ConstraintValidatorContext context) {
        // From and Value must be absent
        if (value.getFrom() != null || value.getValue() != null) {
            return false;
        }
        return isValidNodePath(value.getPath());
    }

    protected boolean isValidAddOrReplaceOrTest(fr.jstessier.patch.models.Patch value, ConstraintValidatorContext context) {
        // From must be absent
        if (value.getFrom() != null) {
            return false;
        }
        return isValidNodePath(value.getPath()) && value.getValue() != null;
    }

    protected boolean isValidCopyOrMove(fr.jstessier.patch.models.Patch value, ConstraintValidatorContext context) {
        // Value must be absent
        if (value.getValue() != null) {
            return false;
        }
        return isValidNodePath(value.getFrom()) && isValidNodePath(value.getPath());
    }

    protected boolean isValidNodePath(String nodePath) {
        if (nodePath == null || nodePath.isEmpty() || nodePath.length() > 100 || !nodePath.startsWith("/")) {
            return false;
        }
        return true;
    }

}
