import React from 'react';
import PropTypes from 'prop-types';

const SignUpPopUp = props => (
  <div className="pop-up-wrapper">
    <div className="pop-up">
      <h3 className="pop-up__title">
        You&#39;ve successfully registered.
      </h3>
      <p className="pop-up__notification">
        <span className="pop-up__notification-user">{props.userName}</span>,
          go to
        <span className="pop-up__notification-user">{props.mail}</span>
    to complete the sign-up process</p>
      <button className="btn pop-up__button">OK</button>
    </div>
  </div>
);

SignUpPopUp.propTypes = {
  userName: PropTypes.string.isRequired,
  mail: PropTypes.string.isRequired,
};

export default SignUpPopUp;
