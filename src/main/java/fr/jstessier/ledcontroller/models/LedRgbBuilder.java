package fr.jstessier.ledcontroller.models;

public final class LedRgbBuilder {

    private Integer id;
    private String label;
    private Integer address;
    private ColorRgb colorRgb;

    private LedRgbBuilder() {
        // NOP
    }

    public static LedRgbBuilder aLedRgb() {
        return new LedRgbBuilder();
    }

    public LedRgbBuilder from(LedRgb ledRgb) {
        if (ledRgb != null) {
            this.id = ledRgb.getId();
            this.label = ledRgb.getLabel();
            this.address = ledRgb.getAddress();
            this.colorRgb = ledRgb.getColorRgb();
        }
        return this;
    }

    public LedRgbBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public LedRgbBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    public LedRgbBuilder withAddress(Integer address) {
        this.address = address;
        return this;
    }

    public LedRgbBuilder withColorRgb(ColorRgb colorRgb) {
        this.colorRgb = colorRgb;
        return this;
    }

    public LedRgbBuilder but() {
        return aLedRgb().withId(id).withLabel(label).withAddress(address).withColorRgb(colorRgb);
    }

    public LedRgb build() {
        LedRgb ledRgb = new LedRgb(id, label, address, colorRgb);
        return ledRgb;
    }

}
