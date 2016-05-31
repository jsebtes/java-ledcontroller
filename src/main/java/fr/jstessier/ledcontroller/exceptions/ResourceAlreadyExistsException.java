package fr.jstessier.ledcontroller.exceptions;

public class ResourceAlreadyExistsException extends ResourceException {

    private static final long serialVersionUID = 7910680820082501876L;

    public ResourceAlreadyExistsException(String resourceId, String resourceType) {
        super(resourceId, resourceType);
    }

    public ResourceAlreadyExistsException(String resourceId, String resourceType, String message) {
        super(resourceId, resourceType, message);
    }

    public ResourceAlreadyExistsException(String resourceId, String resourceType, String message, Throwable cause) {
        super(resourceId, resourceType, message, cause);
    }

    public ResourceAlreadyExistsException(String resourceId, String resourceType, Throwable cause) {
        super(resourceId, resourceType, cause);
    }

}
