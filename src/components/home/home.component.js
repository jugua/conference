import template from './home.html';
import controller from './home.controller';

const homeComponent = function home() {
  return {
    restrict: 'EA',
    scope: {},
    template: template,
    controller: controller,
    controllerAs: 'homeCtrl',
    bindToController: true
  };
};

export default homeComponent;
