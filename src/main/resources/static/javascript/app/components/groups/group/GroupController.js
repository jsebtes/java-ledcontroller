export default class GroupController {

    static $inject = ['$state', 'LedControllerService'];

    constructor($state, LedControllerService) {
        this.$state = $state;
        this.LedControllerService = LedControllerService;
    }

    cancel() {
        this.$state.go('groups');
    }
    
    updateGroupColorRgb(group) {
        this.LedControllerService.updateGroupColorRgb(group)
            .then(() => {
                this.$state.go('groups');
            });
    }
    
}
