import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import Form from '../../containers/Forgot-password/forgot-password-form';
import Message from
  './forgot-password-message';
import actions from '../../constants/actions-types';
import changeVisibilityComponent from '../../actions/forgot-password';

class ForgotPassword extends Component {
  componentWillUnmount() {
    const { changeVisibilityComponent: changeVisibility } = this.props;
    const { HIDE_SUCCESS_RESET_PASSWORD_MESSAGE } = actions;
    changeVisibility(HIDE_SUCCESS_RESET_PASSWORD_MESSAGE);
  }

  render() {
    const {
      SHOW_SUCCESS_RESET_PASSWORD_MESSAGE,
      EMAIL_IS_EMPTY, HIDE_EMAIL_ERROR,
    } = actions;
    return (
      <div className="pop-up-wrapper">
        {this.props.forgotPassword || <Form
          show={SHOW_SUCCESS_RESET_PASSWORD_MESSAGE}
          EMAIL_IS_EMPTY={EMAIL_IS_EMPTY}
          HIDE_EMAIL_ERROR={HIDE_EMAIL_ERROR}
        />}
        {this.props.forgotPassword && (
          <Message />
        )}
      </div>);
  }
}

const mapDispatchToProps = dispatch => ({

  changeVisibilityComponent: bindActionCreators(
    changeVisibilityComponent, dispatch),
});

const mapStateToProps = state => ({
  forgotPassword: state.forgotPassword,
});

ForgotPassword.propTypes = {
  forgotPassword: PropTypes.bool.isRequired,
  changeVisibilityComponent: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, mapDispatchToProps)(ForgotPassword);
