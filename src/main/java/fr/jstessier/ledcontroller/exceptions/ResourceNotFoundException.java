package fr.jstessier.ledcontroller.exceptions;

public class ResourceNotFoundException extends ResourceException {

    private static final long serialVersionUID = 4469246453749181009L;

    public ResourceNotFoundException(String resourceId, String resourceType) {
        super(resourceId, resourceType);
    }

    public ResourceNotFoundException(String resourceId, String resourceType, String message) {
        super(resourceId, resourceType, message);
    }

    public ResourceNotFoundException(String resourceId, String resourceType, String message, Throwable cause) {
        super(resourceId, resourceType, message, cause);
    }

    public ResourceNotFoundException(String resourceId, String resourceType, Throwable cause) {
        super(resourceId, resourceType, cause);
    }

}
