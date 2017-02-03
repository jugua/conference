import template from './add-user-popup.html';
import controller from './add-user-popup.controller';

const component = {
  bindings: {
    onHideAddPopup: '&',
    updateUsers: '='
  },
  template,
  controller
};

export default component;