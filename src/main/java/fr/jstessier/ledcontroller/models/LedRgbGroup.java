package fr.jstessier.ledcontroller.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LedRgbGroup implements Serializable{

    private static final long serialVersionUID = 3989960271714872830L;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @JsonProperty("id")
    private final Integer id;

    @NotNull
    @Size(min = 0, max = 20)
    @JsonProperty("label")
    private final String label;

    @NotNull
    @Size(min = 0, max = 20)
    @JsonProperty("ledRgbIds")
    private final List<Integer> ledRgbIds;

    @NotNull
    @Valid
    @JsonProperty("colorRgb")
    private final ColorRgb colorRgb;

    @JsonCreator
    public LedRgbGroup(@JsonProperty("id") final Integer id,
            @JsonProperty("label") final String label,
            @JsonProperty("ledRgbIds") final List<Integer> ledRgbIds,
            @JsonProperty("colorRgb") final ColorRgb colorRgb) {
        this.id = id;
        this.label = label;
        if (ledRgbIds == null) {
            this.ledRgbIds = Collections.unmodifiableList(Collections.emptyList());
        }
        else {
            this.ledRgbIds = Collections.unmodifiableList(ledRgbIds);
        }
        this.colorRgb = colorRgb;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LedRgbGroup{");
        sb.append("id=").append(id);
        sb.append(", label='").append(label).append('\'');
        sb.append(", ledRgbIds=").append(ledRgbIds);
        sb.append(", colorRgb=").append(colorRgb);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedRgbGroup)) return false;
        LedRgbGroup that = (LedRgbGroup) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(label, that.label) &&
                Objects.equals(getLedRgbIds(), that.getLedRgbIds()) &&
                Objects.equals(getColorRgb(), that.getColorRgb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, getLedRgbIds(), getColorRgb());
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public List<Integer> getLedRgbIds() {
        return ledRgbIds;
    }

    public ColorRgb getColorRgb() {
        return colorRgb;
    }

}
