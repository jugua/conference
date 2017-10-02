import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import Form from './forgot-password-form';
import Message from
  '../../components/ForgotPassword/forgot-password-message';
import * as pageActions from '../../actions/forgot-password';
import actions from '../../constants/actions-types';

class ForgotPassword extends Component {
  componentWillUnmount() {
    const { pageActions: { showSuccessMessage } } = this.props;
    const { HIDE_FORGOT_MESSAGE } = actions;
    showSuccessMessage(HIDE_FORGOT_MESSAGE);
  }
  render() {
    const { pageActions: { showSuccessMessage, showError } } = this.props;
    const { forgotPassword } = this.props;
    const { HIDE_EMAIL_ERROR, SHOW_FORGOT_MESSAGE, EMAIL_IS_EMPTY } = actions;
    return (
      <div className="pop-up-wrapper">
        {forgotPassword || <Form
          showSuccessMessage={showSuccessMessage}
          showError={showError}
          HIDE_EMAIL_ERROR={HIDE_EMAIL_ERROR}
          SHOW_FORGOT_MESSAGE={SHOW_FORGOT_MESSAGE}
          EMAIL_IS_EMPTY={EMAIL_IS_EMPTY}
        />}
        {forgotPassword && (
          <Message />
        )}
      </div>
    );
  }
}
function mapStateToProps(state) {
  return {
    forgotPassword: state.forgotPassword,
  };
}
function mapDispatchToProps(dispatch) {
  return {
    pageActions: bindActionCreators(pageActions, dispatch),
  };
}

ForgotPassword.propTypes = {
  forgotPassword: PropTypes.bool.isRequired,
  pageActions: PropTypes.shape({
    showSuccessMessage: PropTypes.func.isRequired,
    showError: PropTypes.func.isRequired,
  }).isRequired };

export default connect(mapStateToProps, mapDispatchToProps)(ForgotPassword);
