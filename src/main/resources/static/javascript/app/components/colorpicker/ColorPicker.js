import angular from 'angular';
import ColorPickerComponent from './ColorPickerComponent';

export default angular.module('ledcontroller.colorpicker', [])
    .component('colorPicker', ColorPickerComponent);