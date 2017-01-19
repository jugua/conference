import template from './user-info-popup.html';
import controller from './user-info-popup.controller';

const component = {
  bindings: {
    user: '<',
    onHideUserInfoPopup: '&',
  },
  template,
  controller,
};

export default component;
