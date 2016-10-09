import template from './login.html';
import controller from './login.controller';

// This is the Directive Definition Object function seen in a traditional Angular setup.
// In this example it is abstracted as a shell and used in the home.js.
const loginComponent = {
  template: template,
  controller: controller,
  controllerAs: 'loginCtrl'
};

export default loginComponent;
