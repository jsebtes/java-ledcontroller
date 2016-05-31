package fr.jstessier.patch.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import fr.jstessier.patch.models.Patch;

import java.util.List;

public class PatchInvalidPathException extends PatchException {

    private static final long serialVersionUID = 4645111615627502516L;

    public PatchInvalidPathException(Patch patch, JsonNode resource) {
        super(patch, resource);
    }

    public PatchInvalidPathException(Patch patch, JsonNode resource, String message) {
        super(patch, resource, message);
    }

    public PatchInvalidPathException(Patch patch, JsonNode resource, String message, Throwable cause) {
        super(patch, resource, message, cause);
    }

    public PatchInvalidPathException(Patch patch, JsonNode resource, Throwable cause) {
        super(patch, resource, cause);
    }

    public PatchInvalidPathException(List<Patch> patches, JsonNode resource) {
        super(patches, resource);
    }

    public PatchInvalidPathException(List<Patch> patches, JsonNode resource, String message) {
        super(patches, resource, message);
    }

    public PatchInvalidPathException(List<Patch> patches, JsonNode resource, String message, Throwable cause) {
        super(patches, resource, message, cause);
    }

    public PatchInvalidPathException(List<Patch> patches, JsonNode resource, Throwable cause) {
        super(patches, resource, cause);
    }
}
