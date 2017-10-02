import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import axios from 'axios';
import baseUrl from '../../constants/backend-url';
import ErrorText
  from '../../components/ForgotPassword/forgot-password-error-text';

class ForgotPasswordForm extends Component {
  constructor(props) {
    super(props);
    this.state = { email: '' };
  }
  componentWillUnmount() {
    const { showError } = this.props;
    const { HIDE_EMAIL_ERROR } = this.props;
    showError(HIDE_EMAIL_ERROR);
  }
  handleChange = ({ target: { value } }) => {
    this.setState({ email: value });
  };
  sendMail =(e) => {
    e.preventDefault();
    const { email } = this.state;
    const { EMAIL_IS_EMPTY, SHOW_FORGOT_MESSAGE } = this.props;
    const { showSuccessMessage, showError } = this.props;
    if (email.length !== 0) {
      const config = {
        headers: { 'X-XSRF-TOKEN': '61be8830-1255-457b-af7d-4fb8c97862c0' },
      };
      axios.post(`${baseUrl}/api/forgot-password`,
        { mail: email },
        config,
      )
        .then(() => {
          showSuccessMessage(SHOW_FORGOT_MESSAGE);
        }).catch((
          { response: { data: { error } } }) => {
          showError(error);
        });
    } else {
      showError(EMAIL_IS_EMPTY);
    }
  };
  render() {
    const { forgotPasswordErrorMessage } = this.props;
    return (
      <div className="pop-up">
        <h3
          className="pop-up__title"
        >Forgot password?</h3>
        <form
          noValidate
          name="userForm"
        >
          <p className="pop-up__notification">Please enter your email:</p>

          <input
            onChange={this.handleChange}
            type="email"
            className="field pop-up__input"
            name="mail"
            required
            value={this.state.email}
          />
          <ErrorText data={forgotPasswordErrorMessage} />
          <div className="pop-up-button-wrapper">
            <button
              className="btn pop-up__button"
              onClick={this.sendMail}
            >Continue
            </button>
            <Link
              className="btn pop-up__button btn_cancel"
              to="/"
            >
              Cancel
            </Link >
          </div>
        </form>
        <Link
          className="pop-up__close"
          to="/"
        />
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    forgotPasswordErrorMessage: state.forgotPasswordErrorMessage,
  };
}

ForgotPasswordForm.propTypes = {
  SHOW_FORGOT_MESSAGE: PropTypes.string.isRequired,
  EMAIL_IS_EMPTY: PropTypes.string.isRequired,
  HIDE_EMAIL_ERROR: PropTypes.string.isRequired,
  forgotPasswordErrorMessage: PropTypes.string.isRequired,
  showSuccessMessage: PropTypes.func.isRequired,
  showError: PropTypes.func.isRequired,
};

export default connect(mapStateToProps)(ForgotPasswordForm);

