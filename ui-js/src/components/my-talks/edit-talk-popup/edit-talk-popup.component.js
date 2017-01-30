import template from './edit-talk-popup.html';
import controller from './edit-talk-popup.controller';

const component = {
  bindings: {
    talk: '<',
    onHideEditPopup: '&',
  },
  template,
  controller
};

export default component;

