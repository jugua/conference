import template from './test-me.html';
import controller from './test-me.controller';
import './test-me.sass';

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
