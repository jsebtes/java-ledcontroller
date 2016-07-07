import 'babel-polyfill';
import svgBaseFix from 'angular-svg-base-fix';
import * as routeConfig from './appRoutes';
import Leds from './components/leds/Leds';
import Groups from './components/groups/Groups';
import Services from './services/Services';
import Constants from './constants/Constants'
import CssColorRgb from './filters/CssColorRgb'

export default angular
    .module('ledcontroller', [svgBaseFix, 'ui.slider', 'ui.router', Leds, Groups.name, Services.name, Constants.name])
    .filter('cssColorRgb', CssColorRgb)
    .config(routeConfig.routing)
    .run(routeConfig.statechange)
    .controller('HeaderController', ['$scope', '$location', function($scope, $location) {
        $scope.isActive = function (viewLocation) {
            return $location.path() ? $location.path().startsWith(viewLocation) : false;
        };
    }]);
