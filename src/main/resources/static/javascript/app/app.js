import 'babel-polyfill';
import angular from 'angular';
import uirouter from 'angular-ui-router';
import uibootstrap from 'angular-ui-bootstrap';
import * as routeConfig from './appRoutes';
import Leds from './components/leds/Leds';
import Groups from './components/groups/Groups';
import Services from './services/Services';
import Constants from './constants/Constants'
import CssColorRgb from './filters/CssColorRgb'

export default angular
    .module('ledcontroller', [uirouter, uibootstrap, Leds.name, Groups.name, Services.name, Constants.name])
    .filter('cssColorRgb', CssColorRgb)
    .config(routeConfig.routing)
    .run(routeConfig.statechange);
