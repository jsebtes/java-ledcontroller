import ledTemplate from './led-template.html';
import ledController from './LedController';

export default {
    bindings: {
        led: '<'
    },
    template: ledTemplate,
    controller: ledController
};
