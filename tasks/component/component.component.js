import template from './<%= kebabName %>.html';
import controller from './<%= kebabName %>.controller';
import './<%= kebabName %>.sass';


const <%= camelName %>Component = {
  bindings: {

  },
  template,
  controller,
  controllerAs: '<%= camelName %>Ctrl'
};

export default <%= camelName %>Component;
