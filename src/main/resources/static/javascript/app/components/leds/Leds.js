import angular from 'angular';
import Led from './led/Led'
import LedsComponent from './LedsComponent';

export default angular.module('ledcontroller.leds', [Led.name])
    .component('leds', LedsComponent);