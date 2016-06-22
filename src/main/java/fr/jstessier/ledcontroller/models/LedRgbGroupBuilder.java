package fr.jstessier.ledcontroller.models;

import java.util.List;

public final class LedRgbGroupBuilder {

    private Integer id;
    private String label;
    private List<Integer> ledRgbIds;
    private ColorRgb colorRgb;

    private LedRgbGroupBuilder() {
        // NOP
    }

    public static LedRgbGroupBuilder aLedRgbGroup() {
        return new LedRgbGroupBuilder();
    }

    public LedRgbGroupBuilder from(LedRgbGroup ledRgbGroup) {
        if (ledRgbGroup != null) {
            this.id = ledRgbGroup.getId();
            this.label = ledRgbGroup.getLabel();
            this.ledRgbIds = ledRgbGroup.getLedRgbIds();
            this.colorRgb = ledRgbGroup.getColorRgb();
        }
        return this;
    }

    public LedRgbGroupBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public LedRgbGroupBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    public LedRgbGroupBuilder withLedRgbIds(List<Integer> ledRgbIds) {
        this.ledRgbIds = ledRgbIds;
        return this;
    }

    public LedRgbGroupBuilder withColorRgb(ColorRgb colorRgb) {
        this.colorRgb = colorRgb;
        return this;
    }

    public LedRgbGroupBuilder but() {
        return aLedRgbGroup().withId(id).withLabel(label).withLedRgbIds(ledRgbIds).withColorRgb(colorRgb);
    }

    public LedRgbGroup build() {
        LedRgbGroup ledRgbGroup = new LedRgbGroup(id, label, ledRgbIds, colorRgb);
        return ledRgbGroup;
    }

}
