import groupTemplate from './group-template.html';
import groupController from './GroupController';

export default {
    bindings: {
        group: '<'
    },
    template: groupTemplate,
    controller: groupController
};
