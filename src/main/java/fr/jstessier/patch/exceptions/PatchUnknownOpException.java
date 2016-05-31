package fr.jstessier.patch.exceptions;

import fr.jstessier.patch.models.Patch;

public class PatchUnknownOpException extends PatchException {

    private static final long serialVersionUID = -6968153388035842721L;

    private final String op;

    public PatchUnknownOpException(String op) {
        super((Patch) null, null);
        this.op = op;
    }

    public PatchUnknownOpException(String op, String message) {
        super((Patch) null, null, message);
        this.op = op;
    }

    public PatchUnknownOpException(String op, String message, Throwable cause) {
        super((Patch) null, null, message, cause);
        this.op = op;
    }

    public PatchUnknownOpException(String op, Throwable cause) {
        super((Patch) null, null, cause);
        this.op = op;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatchUnknownOpException{");
        sb.append("op='").append(op).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getOp() {
        return op;
    }
}
