import React from 'react';
import PropTypes from 'prop-types';
import actions from '../../constants/actions-types';

const ErrorText = ({ data }) => {
  switch (data) {
  case actions.EMAIL_IS_EMPTY:
    return (
      <span
        className="field-error error-title_pop-up"
      >
        Please enter your registered email
      </span>
    );
  case actions.EMAIL_NOT_FOUND:
    return (
      <span
        className="field-error field-error_show error-title_pop-up"
      >
        We can not find an account with that email address
      </span>
    );
  default:
    return false;
  }
};

ErrorText.propTypes = { data: PropTypes.string.isRequired };

// ErrorText.propTypes = { data: PropTypes.string.isRequired };

export default ErrorText;
