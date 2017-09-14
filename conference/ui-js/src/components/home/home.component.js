import template from './home.html';
import controller from './home.controller';

const homeComponent = {
  bindings: {
    user: '<'
  },
  template,
  controller,
};

export default homeComponent;
