import template from './review-talk-popup.html';
import controller from './review-talk-popup.controller';

const reviewTalkPopupComponent = {
  bindings: {
    talk: '<',
    onHideReviewPopup: '&',
  },
  template,
  controller
};

export default reviewTalkPopupComponent;
