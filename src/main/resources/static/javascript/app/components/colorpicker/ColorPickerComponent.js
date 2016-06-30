import colorPickerTemplate from './colorpicker-template.html';
import colorPickerController from './ColorPickerController';

export default {
    bindings: {
        colorRgb: '='
    },
    template: colorPickerTemplate,
    controller: colorPickerController
};
