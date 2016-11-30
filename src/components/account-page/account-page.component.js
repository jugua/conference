import template from './account-page.html';
import controller from './account-page.controller';

const accountPageComponent = {
  bindings: {
    user: '='
  },
  template,
  controller
};

export default accountPageComponent;
