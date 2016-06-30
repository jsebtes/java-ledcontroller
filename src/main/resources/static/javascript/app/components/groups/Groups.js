import angular from 'angular';
import Group from './group/Group';
import GroupsComponent from './GroupsComponent';

export default angular.module('ledcontroller.groups', [Group.name])
    .component('groups', GroupsComponent);