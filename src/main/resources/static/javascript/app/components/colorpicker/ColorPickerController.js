export default class ColorPickerController {

    static $inject = ['$scope', '$log', '$window'];

    constructor($scope, $log, $window) {
        this.$scope = $scope;
        this.$log = $log;

        this.hexColorRgb = this.colorRgbToHex(this.colorRgb);

        $window.hexColorRgb = this.hexColorRgb;
    }

    colorRgbToHex(colorRgb) {
        if (colorRgb) {
            return '#' + this.componentToHex(colorRgb.red) + this.componentToHex(colorRgb.green) + this.componentToHex(colorRgb.blue);
        }
        else {
            return null;
        }
    }

    componentToHex(color) {
        if (color) {
            var hex = color.toString(16).toUpperCase();
            return hex.length == 1 ? '0' + hex : hex;
        }
        else {
            return '00';
        }
    }

    updateColor(event) {
        let colorElement = angular.element(event.event.target);

        let values = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(colorElement.attr('fill'));
        this.colorRgb.red = parseInt(values[1], 16);
        this.colorRgb.green = parseInt(values[2], 16);
        this.colorRgb.blue = parseInt(values[3], 16);

        this.updateSelectedColorElement(colorElement);
    }

    updateSelectedColorElement(colorElement) {
        let colorPickerElement = colorElement.parent().parent();
        let selectedColorElement = $(".selectedColor");
        if (selectedColorElement) {
            selectedColorElement.remove();
        }
        selectedColorElement = colorElement.clone(true);
        selectedColorElement.attr("class", "selectedColor");
        selectedColorElement.attr("fill-opacity", "0");
        selectedColorElement.attr("stroke", "grey");
        selectedColorElement.attr("stroke-width", "3");
        colorPickerElement.append(selectedColorElement);
    }

}
