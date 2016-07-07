package fr.jstessier.ledcontroller.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class ColorRgb implements Serializable {

    private static final long serialVersionUID = 8189431405159656270L;

    @NotNull
    @Min(0)
    @Max(255)
    @JsonProperty("red")
    private final Integer red;

    @NotNull
    @Min(0)
    @Max(255)
    @JsonProperty("green")
    private final Integer green;

    @NotNull
    @Min(0)
    @Max(255)
    @JsonProperty("blue")
    private final Integer blue;

    @NotNull
    @Min(0)
    @Max(100)
    @JsonProperty("intensity")
    private final Integer intensity;

    @JsonCreator
    public ColorRgb(@JsonProperty("red") final Integer red,
            @JsonProperty("green") final Integer green,
            @JsonProperty("blue") final Integer blue,
            @JsonProperty("intensity") final Integer intensity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.intensity = intensity;
    }

    public byte[] toRgbArray() {
        final byte[] rgbArray = new byte[3];
        rgbArray[0] = (byte) (red * intensity / 100);
        rgbArray[1] = (byte) (green * intensity / 100);
        rgbArray[2] = (byte) (blue * intensity / 100);
        return rgbArray;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ColorRgb{");
        sb.append("red=").append(red);
        sb.append(", green=").append(green);
        sb.append(", blue=").append(blue);
        sb.append(", intensity=").append(intensity);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorRgb)) return false;
        ColorRgb colorRgb = (ColorRgb) o;
        return Objects.equals(getRed(), colorRgb.getRed())
                && Objects.equals(getGreen(), colorRgb.getGreen())
                && Objects.equals(getBlue(), colorRgb.getBlue())
                && Objects.equals(getIntensity(), colorRgb.getIntensity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRed(), getGreen(), getBlue(), getIntensity());
    }

    public Integer getRed() {
        return red;
    }

    public Integer getGreen() {
        return green;
    }

    public Integer getBlue() {
        return blue;
    }

    public Integer getIntensity() {
        return intensity;
    }

}
