package fr.jstessier.ledcontroller.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class LedRgb implements Serializable {

    private static final long serialVersionUID = -3555966988997667498L;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @JsonProperty("id")
    private final Integer id;

    @NotNull
    @Size(min = 0, max = 20)
    @JsonProperty("label")
    private final String label;

    @NotNull
    @Min(1)
    @Max(509)
    @JsonProperty("address")
    private final Integer address;

    @NotNull
    @Valid
    @JsonProperty("colorRgb")
    private final ColorRgb colorRgb;

    @JsonCreator
    public LedRgb(@JsonProperty("id") Integer id,
            @JsonProperty("label") String label,
            @JsonProperty("address") Integer address,
            @JsonProperty("colorRgb") ColorRgb colorRgb) {
        this.id = id;
        this.address = address;
        this.label = label;
        this.colorRgb = colorRgb;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LedRgb{");
        sb.append("id=").append(id);
        sb.append(", label='").append(label).append('\'');
        sb.append(", address=").append(address);
        sb.append(", colorRgb=").append(colorRgb);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedRgb)) return false;
        LedRgb ledRgb = (LedRgb) o;
        return Objects.equals(getId(), ledRgb.getId())
                && Objects.equals(getLabel(), ledRgb.getLabel())
                && Objects.equals(getAddress(), ledRgb.getAddress())
                && Objects.equals(getColorRgb(), ledRgb.getColorRgb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLabel(), getAddress(), getColorRgb());
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Integer getAddress() {
        return address;
    }

    public ColorRgb getColorRgb() {
        return colorRgb;
    }

}
