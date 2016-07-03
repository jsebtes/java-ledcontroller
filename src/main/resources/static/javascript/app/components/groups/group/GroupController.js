export default class GroupController {

    static $inject = ['$scope', '$log', 'LedControllerService'];

    constructor($scope, $log, LedControllerService) {
        this.$scope = $scope;
        this.$log = $log;
        this.LedControllerService = LedControllerService;
    }

    
    
    updateGroupColorRgb(group) {
        this.LedControllerService.updateGroupColorRgb(group)
    }
    
}
