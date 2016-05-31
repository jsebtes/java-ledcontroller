package fr.jstessier.ledcontroller.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class LedConfiguration implements Serializable {

    private static final long serialVersionUID = 4648284312195392097L;

    @NotNull
    @Valid
    private List<LedRgb> ledRgbs;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LedConfiguration{");
        sb.append("ledRgbs=").append(ledRgbs);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedConfiguration)) return false;
        LedConfiguration that = (LedConfiguration) o;
        return Objects.equals(getLedRgbs(), that.getLedRgbs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLedRgbs());
    }

    public List<LedRgb> getLedRgbs() {
        return ledRgbs;
    }

    public void setLedRgbs(List<LedRgb> ledRgbs) {
        this.ledRgbs = ledRgbs;
    }

}
