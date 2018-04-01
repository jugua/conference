import React from 'react';
import PropTypes from 'prop-types';

const ForgotPasswordMessage = ({ message, close }) => (
  <div>
    <p className="pop-up__notification">
      {message}
    </p>
    <button
      className="btn pop-up__button"
      onClick={close}
    >
      Close
    </button>
  </div>
);

ForgotPasswordMessage.propTypes = {
  message: PropTypes.string.isRequired,
  close: PropTypes.func.isRequired,
};

export default ForgotPasswordMessage;
