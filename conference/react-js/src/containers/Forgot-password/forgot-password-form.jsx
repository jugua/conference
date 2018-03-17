import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import forgotPasswordActions from '../../actions/forgot-password';
import ErrorText
  from '../../components/Forgot-password/forgot-password-error-text';

class ForgotPasswordForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { email: '' };
  }

  componentWillUnmount() {
    this.clearError();
  }

  sendEmail = () => this.props.dispatch(
    forgotPasswordActions.sendForgotPasswordEmail(this.state.email),
  );

  close = () => this.props.dispatch(
    forgotPasswordActions.setForgotPasswordVisibility(false),
  );

  handleChange = ({ target: { value } }) => {
    this.setState({ email: value });
    this.clearError();
  };

  clearError = () => this.props.dispatch(
    forgotPasswordActions.clearForgotPasswordError(),
  );

  render() {
    return (
      <form noValidate name="userForm">
        <p className="pop-up__notification">Please enter your email:</p>
        <input
          onChange={this.handleChange}
          id="forgot-password-email"
          type="email"
          className="field pop-up__input"
          name="mail"
          required
          value={this.state.email}
        />
        <ErrorText data={this.props.error} />
        <div className="pop-up-button-wrapper">
          <button
            type="button"
            className="btn pop-up__button"
            id="btn-forgot-password-send"
            onClick={this.sendEmail}
          >
            Continue
          </button>
          <button
            type="button"
            className="btn pop-up__button btn_cancel"
            id="lnk-forgot-password-cancel"
            onClick={this.close}
          >
            Cancel
          </button >
        </div>
      </form>
    );
  }
}

ForgotPasswordForm.defaultProps = {
  error: '',
};

ForgotPasswordForm.propTypes = {
  dispatch: PropTypes.func.isRequired,
  error: PropTypes.string,
};

export default connect(
  ({ forgotPassword: { error } }) => ({ error }),
)(ForgotPasswordForm);
