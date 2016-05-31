package fr.jstessier.ledcontroller.models;

public final class ColorRgbBuilder {

    private Integer red;
    private Integer green;
    private Integer blue;

    private ColorRgbBuilder() {
        // NOP
    }

    public static ColorRgbBuilder aColorRgb() {
        return new ColorRgbBuilder();
    }

    public ColorRgbBuilder from(ColorRgb colorRgb) {
        if (colorRgb != null) {
            this.red = colorRgb.getRed();
            this.green = colorRgb.getGreen();
            this.blue = colorRgb.getBlue();
        }
        return this;
    }

    public ColorRgbBuilder withRed(Integer red) {
        this.red = red;
        return this;
    }

    public ColorRgbBuilder withGreen(Integer green) {
        this.green = green;
        return this;
    }

    public ColorRgbBuilder withBlue(Integer blue) {
        this.blue = blue;
        return this;
    }

    public ColorRgbBuilder but() {
        return aColorRgb().withRed(red).withGreen(green).withBlue(blue);
    }

    public ColorRgb build() {
        ColorRgb colorRgb = new ColorRgb(red, green, blue);
        return colorRgb;
    }

}
