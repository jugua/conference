import template from './newtalk.html';
import controller from './newtalk.controller';

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
