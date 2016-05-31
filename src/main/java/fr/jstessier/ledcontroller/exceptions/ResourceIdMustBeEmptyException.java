package fr.jstessier.ledcontroller.exceptions;

public class ResourceIdMustBeEmptyException extends ResourceException {

    private static final long serialVersionUID = -5969123983599817622L;

    public ResourceIdMustBeEmptyException(String resourceId, String resourceType) {
        super(resourceId, resourceType);
    }

    public ResourceIdMustBeEmptyException(String resourceId, String resourceType, String message) {
        super(resourceId, resourceType, message);
    }

    public ResourceIdMustBeEmptyException(String resourceId, String resourceType, String message, Throwable cause) {
        super(resourceId, resourceType, message, cause);
    }

    public ResourceIdMustBeEmptyException(String resourceId, String resourceType, Throwable cause) {
        super(resourceId, resourceType, cause);
    }

}
