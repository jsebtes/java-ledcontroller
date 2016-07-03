export function routing($stateProvider, $urlRouterProvider, $locationProvider) {
    // use the HTML5 History API
    $locationProvider.html5Mode(true);

    $urlRouterProvider
        .otherwise('/groups');

    $stateProvider
        .state('leds', {
            url: '/leds',
            template: '<leds></leds>',
        })
        .state('editLedColor', {
            url: '/leds/:id',
            template: '<led led="led"></led>',
            controller: ($scope, led) => Object.assign($scope, { led }),
            resolve: {
                LedControllerService: 'LedControllerService',
                group: ['LedControllerService', '$stateParams', (LedControllerService, $stateParams) =>
                    LedControllerService.getLed($stateParams.id)],
            },
        })
        .state('groups', {
            url: '/groups',
            template: '<groups></groups>',
        })
        .state('editGroupColor', {
            url: '/groups/:id',
            template: '<group group="group"></group>',
            controller: ($scope, group) => Object.assign($scope, { group }),
            resolve: {
                LedControllerService: 'LedControllerService',
                group: ['LedControllerService', '$stateParams', (LedControllerService, $stateParams) =>
                    LedControllerService.getGroup($stateParams.id)],
            },
        });
}

routing.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];

export function statechange($rootScope, $log) {
    $rootScope.$on('$stateChangeError', (event, toState, toParams, fromState, fromParams, error) => {
        $log.debug(`Error while routing: ${error}`);
    });
}

statechange.$inject = ['$rootScope', '$log'];