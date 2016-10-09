import template from './list.html';
import controller from './list.controller';

const listComponent = function list() {
  return {
    restrict: 'EA',
    scope: {},
    template: template,
    controller: controller,
    controllerAs: 'listCtrl',
    bindToController: true
  };
};

export default listComponent;
