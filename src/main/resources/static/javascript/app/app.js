import angular from 'angular';
import Leds from './components/leds/Leds';
import Groups from './components/groups/Groups';
import Services from './services/Services';
import Constants from './constants/Constants'

export default angular.module('ledcontroller', [Leds.name, Groups.name, Services.name, Constants.name]);
