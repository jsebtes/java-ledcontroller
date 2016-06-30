export default class LedsController {

    static $inject = ['$scope', '$log', 'LedControllerService'];

    constructor($scope, $log, LedControllerService) {
        this.$scope = $scope;
        this.$log = $log;
        this.LedControllerService = LedControllerService;

        this.LedControllerService.getLeds().then((leds) =>
            this.leds = leds
        );
    }

}
