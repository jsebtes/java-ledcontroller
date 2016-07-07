import Led from './led/Led'
import LedsComponent from './LedsComponent';

const LEDS = 'ledcontroller.leds';

angular.module(LEDS, [Led.name])
    .component('leds', LedsComponent);

export default LEDS;