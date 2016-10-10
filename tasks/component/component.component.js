import template from './<%= kebabName %>.html';
import controller from './<%= kebabName %>.controller';

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
