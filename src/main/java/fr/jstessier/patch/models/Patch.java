package fr.jstessier.patch.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import fr.jstessier.patch.exceptions.PatchUnknownOpException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * RFC 6902
 *
 */
@fr.jstessier.patch.annotations.Patch
public class Patch implements Serializable {

    private static final long serialVersionUID = -4477491542802892098L;

    @JsonProperty("op")
    private final Operation operation;

    @JsonProperty("from")
    private final String from;

    @JsonProperty("path")
    private final String path;

    @JsonProperty("value")
    private final JsonNode value;

    @JsonCreator
    public Patch(@JsonProperty("op") Operation operation,
            @JsonProperty("from") String from,
            @JsonProperty("path") String path,
            @JsonProperty("value") JsonNode value) {
        this.operation = operation;
        this.from = from;
        this.path = path;
        this.value = value;
    }

    @JsonIgnore
    public List<String> getPathAsList() {
        return Arrays.asList(path.substring(1).split("/"))
                .stream()
                .map(pathFragment ->
                        // See RFC6901 https://tools.ietf.org/html/rfc6901#section-4
                        pathFragment.replaceAll("~1", "/").replaceAll("~0", "~"))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Patch{");
        sb.append("operation=").append(operation);
        sb.append(", from='").append(from).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patch)) return false;
        Patch patch = (Patch) o;
        return getOperation() == patch.getOperation() &&
                Objects.equals(getFrom(), patch.getFrom()) &&
                Objects.equals(getPath(), patch.getPath()) &&
                Objects.equals(getValue(), patch.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getFrom(), getPath(), getValue());
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFrom() {
        return from;
    }

    public String getPath() {
        return path;
    }

    public JsonNode getValue() {
        return value;
    }

    public enum Operation {
        ADD("add"),
        REMOVE("remove"),
        REPLACE("replace"),
        MOVE("move"),
        COPY("copy"),
        TEST("test");

        private final String op;

        private Operation(String op) {
            this.op = op;
        }

        @JsonCreator
        public static Operation fromOp(String op) {
            return Arrays.stream(Operation.values())
                    .filter(value -> value.getOp().equals(op))
                    .findFirst()
                    .orElseThrow(() -> new PatchUnknownOpException(op, String.format("%s is not a valid operation", op)));
        }

        @JsonValue
        public String getOp() {
            return op;
        }

    }

}
