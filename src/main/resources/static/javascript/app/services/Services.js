import angular from 'angular';
import LedControllerService from './LedControllerService';

export default angular.module('ledController.services', [])
    .service('LedControllerService', LedControllerService);
