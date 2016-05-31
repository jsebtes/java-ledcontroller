package fr.jstessier.patch.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import fr.jstessier.patch.models.Patch;

import java.util.List;

public class PatchMissingNodeException extends PatchException {

    private static final long serialVersionUID = -2348764801681691027L;

    public PatchMissingNodeException(Patch patch, JsonNode resource) {
        super(patch, resource);
    }

    public PatchMissingNodeException(Patch patch, JsonNode resource, String message) {
        super(patch, resource, message);
    }

    public PatchMissingNodeException(Patch patch, JsonNode resource, String message, Throwable cause) {
        super(patch, resource, message, cause);
    }

    public PatchMissingNodeException(Patch patch, JsonNode resource, Throwable cause) {
        super(patch, resource, cause);
    }

    public PatchMissingNodeException(List<Patch> patches, JsonNode resource) {
        super(patches, resource);
    }

    public PatchMissingNodeException(List<Patch> patches, JsonNode resource, String message) {
        super(patches, resource, message);
    }

    public PatchMissingNodeException(List<Patch> patches, JsonNode resource, String message, Throwable cause) {
        super(patches, resource, message, cause);
    }

    public PatchMissingNodeException(List<Patch> patches, JsonNode resource, Throwable cause) {
        super(patches, resource, cause);
    }

}
