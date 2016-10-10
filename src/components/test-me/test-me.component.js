import template from './test-me.html';
import controller from './test-me.controller';

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
