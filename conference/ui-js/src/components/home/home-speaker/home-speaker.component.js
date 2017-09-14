import template from './home-speaker.html';
import controller from './home-speaker.controller';

const component = {
  bindings: {
    user: '<',
  },
  template,
  controller,
};

export default component;
