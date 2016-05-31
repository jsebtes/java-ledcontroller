package fr.jstessier.patch.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import fr.jstessier.patch.models.Patch;

import java.util.List;

public class PatchInvalidValueException extends PatchException {

    private static final long serialVersionUID = -78560357190444753L;

    private final Class<?> model;

    public PatchInvalidValueException(Patch patch, JsonNode resource, Class<?> model) {
        super(patch, resource);
        this.model = model;
    }

    public PatchInvalidValueException(Patch patch, JsonNode resource, Class<?> model, String message) {
        super(patch, resource, message);
        this.model = model;
    }

    public PatchInvalidValueException(Patch patch, JsonNode resource, Class<?> model, String message, Throwable cause) {
        super(patch, resource, message, cause);
        this.model = model;
    }

    public PatchInvalidValueException(Patch patch, JsonNode resource, Class<?> model, Throwable cause) {
        super(patch, resource, cause);
        this.model = model;
    }

    public PatchInvalidValueException(List<Patch> patches, JsonNode resource, Class<?> model) {
        super(patches, resource);
        this.model = model;
    }

    public PatchInvalidValueException(List<Patch> patches, JsonNode resource, Class<?> model, String message) {
        super(patches, resource, message);
        this.model = model;
    }

    public PatchInvalidValueException(List<Patch> patches, JsonNode resource, Class<?> model, String message, Throwable cause) {
        super(patches, resource, message, cause);
        this.model = model;
    }

    public PatchInvalidValueException(List<Patch> patches, JsonNode resource, Class<?> model, Throwable cause) {
        super(patches, resource, cause);
        this.model = model;
    }

}
