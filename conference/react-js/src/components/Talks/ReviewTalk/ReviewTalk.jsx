import React from 'react';
import PropTypes from 'prop-types';

const ReviewTalk = ({ close }) => (
  <div className="pop-up-wrapper">
    <div className="pop-up review-talk">
      <div className="review-talk__topic">
        JVM Languages and new programming paradigms
      </div>
      <div className="review-talk__type">
        Regular Talk:
      </div>
      <div className="review-talk__title">
        Why Java
      </div>
      <p className="review-talk__description">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
      </p>
      <p className="review-talk__language">
        Language: English
      </p>
      <p className="review-talk__level">
        Level: Begginer
      </p>
      <div className="review-talk__additional-info">
        <a className="additional-info__title link">
          Additional info
        </a>
        <p className="additional-info__text">
          Additional info Additional info Additional info Additional info
        </p>
      </div>
      <div className="review-talk__comments" />
      <div className="review-talk__button-group">
        <button className="review-talk__button">Approve</button>
        <button className="review-talk__button review-talk__button_reject">Reject</button>
        <button
          className="review-talk__button"
          onClick={close}
        >
          Cancel
        </button>
      </div>
    </div>
  </div>
);

ReviewTalk.propTypes = {
  close: PropTypes.func.isRequired,
};

export default ReviewTalk;
