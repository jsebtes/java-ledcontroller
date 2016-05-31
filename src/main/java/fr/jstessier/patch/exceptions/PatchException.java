package fr.jstessier.patch.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import fr.jstessier.patch.models.Patch;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PatchException extends RuntimeException {

    private static final long serialVersionUID = -646666972762040740L;

    private final List<Patch> patches;

    private final JsonNode resource;

    public PatchException(Patch patch, JsonNode resource) {
        super();
        this.patches = Arrays.asList(patch);
        this.resource = resource;
    }

    public PatchException(Patch patch, JsonNode resource, String message) {
        super(message);
        this.patches = Arrays.asList(patch);
        this.resource = resource;
    }

    public PatchException(Patch patch, JsonNode resource, String message, Throwable cause) {
        super(message, cause);
        this.patches = Arrays.asList(patch);
        this.resource = resource;
    }

    public PatchException(Patch patch, JsonNode resource, Throwable cause) {
        super(cause);
        this.patches = Arrays.asList(patch);
        this.resource = resource;
    }

    public PatchException(List<Patch> patches, JsonNode resource) {
        super();
        this.patches = patches;
        this.resource = resource;
    }

    public PatchException(List<Patch> patches, JsonNode resource, String message) {
        super(message);
        this.patches = patches;
        this.resource = resource;
    }

    public PatchException(List<Patch> patches, JsonNode resource, String message, Throwable cause) {
        super(message, cause);
        this.patches = patches;
        this.resource = resource;
    }

    public PatchException(List<Patch> patches, JsonNode resource, Throwable cause) {
        super(cause);
        this.patches = patches;
        this.resource = resource;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatchException{");
        sb.append("patches=").append(patches);
        sb.append(", resource=").append(resource);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatchException)) return false;
        PatchException that = (PatchException) o;
        return Objects.equals(getPatches(), that.getPatches())
                && Objects.equals(getResource(), that.getResource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPatches(), getResource());
    }

    public List<Patch> getPatches() {
        return patches;
    }

    public JsonNode getResource() {
        return resource;
    }
}
