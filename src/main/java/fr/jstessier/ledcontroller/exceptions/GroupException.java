package fr.jstessier.ledcontroller.exceptions;

public class GroupException extends RuntimeException {

    public GroupException() {
        super();
    }

    public GroupException(String message) {
        super(message);
    }

    public GroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupException(Throwable cause) {
        super(cause);
    }

}
