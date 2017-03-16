import template from './newtalk-popup.html';
import controller from './newtalk-popup.controller';

const newtalkComponent = {
  bindings: {
    onClose: '&',
    onSubmit: '&',
    conferenceId: '<',
  },
  template,
  controller
};

export default newtalkComponent;
