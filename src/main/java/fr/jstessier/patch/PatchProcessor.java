package fr.jstessier.patch;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.jstessier.patch.exceptions.PatchException;
import fr.jstessier.patch.exceptions.PatchInvalidPathException;
import fr.jstessier.patch.exceptions.PatchInvalidValueException;
import fr.jstessier.patch.exceptions.PatchMissingNodeException;
import fr.jstessier.patch.models.Patch;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class    PatchProcessor {

    private final ObjectMapper mapper;

    private final Validator validator;

    public PatchProcessor(final ObjectMapper mapper) {
        this.mapper = mapper;
        this.validator = null;
    }

    public PatchProcessor(final Validator validator) {
        this.mapper = new ObjectMapper();
        this.validator = validator;
    }

    public PatchProcessor(final ObjectMapper mapper, final Validator validator) {
        this.mapper = mapper;
        this.validator = validator;
    }

    public <T> T processPatches(final T model, final Class<T> modelType, final List<Patch> patches, final boolean validate) {

        final JsonNode jsonBefore = mapper.convertValue(model, JsonNode.class);
        final JsonNode jsonAfter = processPatches(jsonBefore, patches);

        T result = null;
        try {
            result = mapper.convertValue(jsonAfter, modelType);
        }
        catch (IllegalArgumentException | ClassCastException e) {
                throw new PatchInvalidValueException(patches, jsonAfter, modelType, "new value(s) is(are) not compatible with model", e);
        }

        // Bean validation
        if (validate) {
            if (validator != null) {
                final Set<ConstraintViolation<T>> violations = validator.validate(result);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
            }
            else {
                throw new IllegalStateException("A Validator is needed to validate");
            }
        }

        return result;
    }

    public JsonNode processPatches(final JsonNode jsonNode, final List<Patch> patches) {
        final JsonNode result = jsonNode.deepCopy();
        for (Patch patch : patches) {
            switch (patch.getOperation()) {
                case ADD:
                    add(result, patch);
                    break;
                case REMOVE:
                    remove(result, patch);
                    break;
                case REPLACE:
                    replace(result, patch);
                    break;
                case MOVE:
                    throw new UnsupportedOperationException();
                case COPY:
                    throw new UnsupportedOperationException();
                case TEST:
                    throw new UnsupportedOperationException();
            }
        }
        return result;
    }

    private void processPatch(final JsonNode jsonNode, final Patch patch, final BiConsumer<List<String>, JsonNode> biConsumer) {

        final List<String> pathAsList = patch.getPathAsList();
        if (pathAsList.isEmpty()) {
            throw new PatchInvalidPathException(patch, jsonNode, "root element does not have parent");
        }

        final JsonNode parentNodeToReplace = getNodeParent(jsonNode, pathAsList);
        if (parentNodeToReplace.isMissingNode()) {
            throw new PatchMissingNodeException(patch, jsonNode, "parent node does not exist in the resource");
        }

        biConsumer.accept(pathAsList, parentNodeToReplace);
    }

    private void add(final JsonNode jsonNode, final Patch patch) {
        processPatch(jsonNode, patch, (pathAsList, parentNodeToReplace) -> {
            final String fieldNameToReplace = pathAsList.get(pathAsList.size() - 1);
            if (parentNodeToReplace.isContainerNode()) {
                throw new PatchException(patch, jsonNode, "add operation is allowed only if parent node is a container");
            }
            else if (parentNodeToReplace.isObject()) {
                ((ObjectNode) parentNodeToReplace).set(fieldNameToReplace, patch.getValue());
            }
            else if (parentNodeToReplace.isArray()) {
                ((ArrayNode) parentNodeToReplace).add(patch.getValue());
            }
            else {
                throw new PatchException(patch, jsonNode, "parent node type not supported");
            }
        });
    }

    private void remove(final JsonNode jsonNode, final Patch patch) {
        processPatch(jsonNode, patch, (pathAsList, parentNodeToReplace) -> {
            final String fieldNameToReplace = pathAsList.get(pathAsList.size() - 1);
            if (parentNodeToReplace.isContainerNode()) {
                throw new PatchException(patch, jsonNode, "remove operation is allowed only if parent node is a container");
            }
            else if (parentNodeToReplace.isObject()) {
                ((ObjectNode) parentNodeToReplace).remove(fieldNameToReplace);
            }
            else if (parentNodeToReplace.isArray()) {
                int fieldToReplaceIndex;
                try {
                    fieldToReplaceIndex = Integer.parseInt(fieldNameToReplace);
                }
                catch (NumberFormatException e) {
                    throw new PatchInvalidPathException(patch, jsonNode, "field name to remove must be an integer", e);
                }
                if (parentNodeToReplace.get(fieldToReplaceIndex).isMissingNode()) {
                    throw new PatchMissingNodeException(patch, jsonNode, "field to remove does not exist");
                }
                try {
                    ((ArrayNode) parentNodeToReplace).remove(fieldToReplaceIndex);
                }
                catch (IndexOutOfBoundsException e) {
                    throw new PatchInvalidPathException(patch, jsonNode, "field index out of bounds", e);
                }
            }
            else {
                throw new PatchException(patch, jsonNode, "parent node type not supported");
            }
        });
    }

    private void replace(final JsonNode jsonNode, final Patch patch) {
        processPatch(jsonNode, patch, (pathAsList, parentNodeToReplace) -> {
            final String fieldNameToReplace = pathAsList.get(pathAsList.size() - 1);
            if (parentNodeToReplace.isObject()) {
                ((ObjectNode) parentNodeToReplace).replace(fieldNameToReplace, patch.getValue());
            }
            else if (parentNodeToReplace.isArray()) {
                int fieldToReplaceIndex;
                try {
                    fieldToReplaceIndex = Integer.parseInt(fieldNameToReplace);
                }
                catch (NumberFormatException e) {
                    throw new PatchInvalidPathException(patch, jsonNode, "field name to replace must be an integer", e);
                }
                if (parentNodeToReplace.get(fieldToReplaceIndex).isMissingNode()) {
                    throw new PatchMissingNodeException(patch, jsonNode, "field to replace does not exist");
                }
                try {
                    ((ArrayNode) parentNodeToReplace).set(fieldToReplaceIndex, patch.getValue());
                }
                catch (IndexOutOfBoundsException e) {
                    throw new PatchInvalidPathException(patch, jsonNode, "field index out of bounds", e);
                }
            }
            else {
                throw new PatchException(patch, jsonNode, "parent node type not supported");
            }
        });
    }

    private JsonNode getNodeParent(final JsonNode jsonNode, final List<String> pathAsList) {
        if (pathAsList == null || pathAsList.isEmpty()) {
            return MissingNode.getInstance();
        }
        return getNode(jsonNode, pathAsList.subList(0, pathAsList.size() - 1));
    }

    private JsonNode getNode(final JsonNode jsonNode, final List<String> pathAsList) {
        JsonNode currentNode = jsonNode;
        if (pathAsList != null) {
            for (String pathFragment : pathAsList) {
                currentNode = currentNode.path(pathFragment);
            }
        }
        return currentNode;
    }

}
