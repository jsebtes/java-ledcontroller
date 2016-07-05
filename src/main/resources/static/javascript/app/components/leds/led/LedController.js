export default class LedController {

    static $inject = ['$state', 'LedControllerService'];

    constructor($state, LedControllerService) {
        this.$state = $state;
        this.LedControllerService = LedControllerService;
    }

    cancel() {
        this.$state.go('leds');
    }

    updateLedColorRgb(led) {
        this.LedControllerService.updateLedColorRgb(led)
            .then(() => {
                this.$state.go('leds');
            });
    }

}
