import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Form from '../../containers/Forgot-password/forgot-password-form';
import Message from
  './forgot-password-message';
import actions from '../../constants/actions-types';

const ForgotPassword = ({ forgotPassword }) => {
  const { HIDE_SUCCESS_RESET_PASSWORD_MESSAGE,
    SHOW_SUCCESS_RESET_PASSWORD_MESSAGE,
    EMAIL_IS_EMPTY, HIDE_EMAIL_ERROR } = actions;
  return (
    <div className="pop-up-wrapper">
      {forgotPassword || <Form
        hide={HIDE_SUCCESS_RESET_PASSWORD_MESSAGE}
        show={SHOW_SUCCESS_RESET_PASSWORD_MESSAGE}
        EMAIL_IS_EMPTY={EMAIL_IS_EMPTY}
        HIDE_EMAIL_ERROR={HIDE_EMAIL_ERROR}
      />}
      {forgotPassword && (
        <Message />
      )}
    </div>);
};
ForgotPassword.propTypes = {
  forgotPassword: PropTypes.bool.isRequired,
};

const mapStateToProps = state => ({
  forgotPassword: state.forgotPassword,
});

export default connect(mapStateToProps)(ForgotPassword);
