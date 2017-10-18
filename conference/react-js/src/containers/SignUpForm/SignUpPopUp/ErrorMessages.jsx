import React from 'react';
import PropTypes from 'prop-types';

const ErrorMessage = props => (
  <span className="field-error">{props.errorMessage}</span>
);

ErrorMessage.propTypes = {
  errorMessage: PropTypes.string.isRequired,
};

export default ErrorMessage;
