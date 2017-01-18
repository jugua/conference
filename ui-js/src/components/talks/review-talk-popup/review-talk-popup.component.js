import template from './review-talk-popup.html';
import controller from './review-talk-popup.controller';

const reviewTalkPopupComponent = {
  bindings: {
    id: '<',
    status: '<',
    onHideReviewPopup: '&'
  },
  template,
  controller
};

export default reviewTalkPopupComponent;
