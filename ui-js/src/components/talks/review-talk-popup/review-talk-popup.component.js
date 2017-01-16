import template from './review-talk-popup.html';
import controller from './review-talk-popup.controller';

const reviewTalkPopupComponent = {
  bindings: {
    id: '<',
    onHideReviewPopup: '&'
  },
  template,
  controller
};

export default reviewTalkPopupComponent;
