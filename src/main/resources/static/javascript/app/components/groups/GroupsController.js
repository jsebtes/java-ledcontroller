export default class GroupsController {

    static $inject = ['$scope', '$log', 'LedControllerService'];

    constructor($scope, $log, LedControllerService) {
        this.$scope = $scope;
        this.$log = $log;
        this.LedControllerService = LedControllerService;

        this.LedControllerService.getGroups().then((groups) =>
            this.groups = groups
        );
    }


    colorFromColorRgb(colorRgb) {
        return 'rgb(' + colorRgb.red + ', ' + colorRgb.green + ', ' + colorRgb.blue + ')';
    }


}
