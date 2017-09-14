import template from './newtalk.html';
import controller from './newtalk.controller';

const component = {
  bindings: {
    fwdState: '@',
  },
  template,
  controller,
};

export default component;
