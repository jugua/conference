import React from 'react';
import PropTypes from 'prop-types';
import actions from '../../constants/actions-types';

const ErrorText = ({ data }) => {
  let res;
  if (data === actions.EMAIL_IS_EMPTY) {
    res =
        (<span
          className="field-error error-title_pop-up"
        >
        Please enter your registered email
        </span>);
  } else if (data === actions.EMAIL_NOT_FOUND) {
    res =
        (<span
          className="field-error field-error_show error-title_pop-up"
        >
        We can not find an account with that email address
        </span>);
  } else {
    res = null;
  }
  return res;
};

ErrorText.propTypes = { data: PropTypes.string.isRequired };

export default ErrorText;
