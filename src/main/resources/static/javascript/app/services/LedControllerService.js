export default class LedControllerService {

    static $inject = ['$http', '$log', 'constantsUrl'];

    constructor($http, $log, constantsUrl) {
        this.$http = $http;
        this.$log = $log;
        this.constantsUrl = constantsUrl;
    }

    getLeds() {
        return this.$http({
            method: 'GET',
            url: this.constantsUrl.LEDCONTROLLER_URL + `/leds`
        }).then((response) => response.data);
    }

    getGroups() {
        return this.$http({
            method: 'GET',
            url: this.constantsUrl.LEDCONTROLLER_URL + `/groups`
        }).then((response) => response.data);
    }

    updateGroupColorRgb(id, colorRgb) {

    }

}

