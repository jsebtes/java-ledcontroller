package fr.jstessier.ledcontroller.exceptions;

public class ResourceException extends RuntimeException {

    private static final long serialVersionUID = 2082323125867200534L;

    private final String resourceId;

    private final String resourceType;

    public ResourceException(String resourceId, String resourceType) {
        super();
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

    public ResourceException(String resourceId, String resourceType, String message) {
        super(message);
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

    public ResourceException(String resourceId, String resourceType, String message, Throwable cause) {
        super(message, cause);
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

    public ResourceException(String resourceId, String resourceType, Throwable cause) {
        super(cause);
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

}
