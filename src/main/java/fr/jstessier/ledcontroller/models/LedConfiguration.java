package fr.jstessier.ledcontroller.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LedConfiguration implements Serializable {

    private static final long serialVersionUID = 4648284312195392097L;

    @NotNull
    @Valid
    private final List<LedRgb> ledRgbs;

    @NotNull
    @Valid
    private final List<LedRgbGroup> groups;

    @JsonCreator
    public LedConfiguration(@JsonProperty("ledRgbs") final List<LedRgb> ledRgbs,
            @JsonProperty("groups") final List<LedRgbGroup> groups) {
        if (ledRgbs == null) {
            this.ledRgbs = Collections.unmodifiableList(Collections.emptyList());
        }
        else {
            this.ledRgbs = Collections.unmodifiableList(ledRgbs);
        }
        if (groups == null) {
            this.groups = Collections.unmodifiableList(Collections.emptyList());
        }
        else {
            this.groups = Collections.unmodifiableList(groups);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LedConfiguration{");
        sb.append("ledRgbs=").append(ledRgbs);
        sb.append(", groups=").append(groups);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedConfiguration)) return false;
        LedConfiguration that = (LedConfiguration) o;
        return Objects.equals(getLedRgbs(), that.getLedRgbs()) &&
                Objects.equals(getGroups(), that.getGroups());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLedRgbs(), getGroups());
    }

    public List<LedRgb> getLedRgbs() {
        return ledRgbs;
    }

    public List<LedRgbGroup> getGroups() {
        return groups;
    }

}
