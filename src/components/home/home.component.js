import template from './home.html';
import controller from './home.controller';

// This is the Directive Definition Object function seen in a traditional Angular setup.
// In this example it is abstracted as a shell and used in the home.js.
const homeComponent = {
  bindings: {
    'name': '='
  },

  template: template,
  controller: controller,
  controllerAs: 'homeCtrl',
  }

export default homeComponent;
