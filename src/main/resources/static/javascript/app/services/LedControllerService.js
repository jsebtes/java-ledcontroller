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

    getLed(id) {
        return this.$http({
            method: 'GET',
            url: this.constantsUrl.LEDCONTROLLER_URL + `/leds/` + id
        }).then((response) => response.data);
    }

    updateLedColorRgb(led) {
        return this.$http({
            method: 'PATCH',
            url: this.constantsUrl.LEDCONTROLLER_URL + `/leds/` + led.id,
            data: [
                {
                    op: "replace",
                    path: "/colorRgb",
                    value: led.colorRgb
                }
            ]
        });
    }

    getGroups() {
        return this.$http({
            method: 'GET',
            url: this.constantsUrl.LEDCONTROLLER_URL + `/groups`
        }).then((response) => response.data);
    }

    getGroup(id) {
        return this.$http({
            method: 'GET',
            url: this.constantsUrl.LEDCONTROLLER_URL + `/groups/` + id
        }).then((response) => response.data);
    }

    updateGroupColorRgb(group) {
        return this.$http({
            method: 'PATCH',
            url: this.constantsUrl.LEDCONTROLLER_URL + `/groups/` + group.id,
            data: [
                {
                    op: "replace",
                    path: "/colorRgb",
                    value: group.colorRgb
                }
            ]
        });
    }

}

