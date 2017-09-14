import template from './review-talk-popup.html';
import controller from './review-talk-popup.controller';

const component = {
  bindings: {
    talk: '<',
    onHideReviewPopup: '&',
  },
  template,
  controller
};

export default component;
