import angular from 'angular';
import LedComponent from './LedComponent';

export default angular.module('ledcontroller.led', [])
    .component('led', LedComponent);