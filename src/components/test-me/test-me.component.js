import template from './test-me.html';
import controller from './test-me.controller';
import './test-me.sass';

// This is the Directive Definition Object function seen in a traditional Angular setup.
// In this example it is abstracted as a shell and used in the home.js.
const testMeComponent = function testMe() {
  return {
    restrict: 'EA',
    scope: {},
    template: template,
    controller: controller,
    controllerAs: 'testMeCtrl',
    bindToController: true
  };
};

export default testMeComponent;
