import template from './login.html';
import controller from './login.controller';

// This is the Directive Definition Object function seen in a traditional Angular setup.
// In this example it is abstracted as a shell and used in the home.js.
let loginComponent = function () {
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
