import ColorPicker from '../../colorpicker/ColorPicker';
import GroupComponent from './GroupComponent';

export default angular.module('ledcontroller.group', [ColorPicker.name])
    .component('group', GroupComponent);