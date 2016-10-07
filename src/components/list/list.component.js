import template from './list.html';
import controller from './list.controller';

// This is the Directive Definition Object function seen in a traditional Angular setup.
// In this example it is abstracted as a shell and used in the home.js.
let listComponent = function () {
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
