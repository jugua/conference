import template from './<%= kebabName %>.html';
import controller from './<%= kebabName %>.controller';
import './<%= kebabName %>.sass';

// This is the Directive Definition Object function seen in a traditional Angular setup.
// In this example it is abstracted as a shell and used in the home.js.
const <%= camelName %>Component = function <%= camelName %>() {
  return {
    restrict: 'EA',
    scope: {},
    template: template,
    controller: controller,
    controllerAs: '<%= camelName %>Ctrl',
    bindToController: true
  };
};

export default <%= camelName %>Component;
