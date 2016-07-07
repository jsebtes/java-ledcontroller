import ColorPicker from '../../colorpicker/ColorPicker';
import LedComponent from './LedComponent';

export default angular.module('ledcontroller.led', [ColorPicker.name])
    .component('led', LedComponent);