import template from './fill-info-popup.html';
import controller from './fill-info-popup.controller';

const component = {
  bindings: {
    onClose: '&',
    fwdState: '<',
  },
  template,
  controller,
};

export default component;
