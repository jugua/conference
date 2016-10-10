import template from './login.html';
import controller from './login.controller';

const loginComponent = function login() {
  return {
    restrict: 'EA',
    scope: {},
    template: template,
    controller: controller,
    controllerAs: 'loginCtrl',
    bindToController: true
  };
};

export default loginComponent;
