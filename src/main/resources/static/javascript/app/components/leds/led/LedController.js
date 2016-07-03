export default class LedController {

    static $inject = ['$scope', '$log', 'LedControllerService'];

    constructor($scope, $log, LedControllerService) {
        this.$scope = $scope;
        this.$log = $log;
        this.LedControllerService = LedControllerService;
    }

}
