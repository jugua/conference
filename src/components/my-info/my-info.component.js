import template from './my-info.html';
import controller from './my-info.controller';

const myInfoComponent = {
  bindings: {
    user: '='
  },
  template,
  controller
};

export default myInfoComponent;
