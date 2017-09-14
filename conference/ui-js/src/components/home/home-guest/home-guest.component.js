import template from './home-guest.html';
import controller from './home-guest.controller';

const component = {
  bindings: {
    user: '<',
  },
  template,
  controller,
};

export default component;
