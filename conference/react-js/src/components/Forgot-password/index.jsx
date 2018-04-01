import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import ForgotPasswordWrapper from '../ForgotPassword/ForgotPasswordWrapper';
import forgotPasswordActions from '../../actions/forgot-password';

const ForgotPassword = ({
  visibility,
  message,
  dispatch,
}) => {
  const close = () => dispatch(
    forgotPasswordActions.setForgotPasswordVisibility(false),
  );

  return visibility
    ? <ForgotPasswordWrapper close={close} message={message} />
    : <div />;
};

ForgotPassword.defaultProps = {
  visibility: false,
  message: '',
};

ForgotPassword.propTypes = {
  dispatch: PropTypes.func.isRequired,
  visibility: PropTypes.bool,
  message: PropTypes.string,
};

export default connect(
  ({ forgotPassword: { visibility, message } }) => ({ visibility, message }),
)(ForgotPassword);
