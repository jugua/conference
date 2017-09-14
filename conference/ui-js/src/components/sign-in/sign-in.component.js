import template from './sign-in.html';
import controller from './sign-in.controller';

const signInComponent = {
  bindings: {
    display: '@',
  },
  template,
  controller,
};

export default signInComponent;