import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import axios from 'axios';
import changeVisibilityComponent from '../../actions/forgot-password';
import ErrorText
  from '../../components/Forgot-password/forgot-password-error-text';
import { forgotPassword } from '../../constants/backend-url';
import { baseUrl } from '../../constants/route-url';

class ForgotPasswordForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { email: '' };
  }
  componentWillUnmount() {
    const {
      HIDE_EMAIL_ERROR,
      changeVisibilityComponent: changeVisibility } = this.props;
    changeVisibility(HIDE_EMAIL_ERROR);
  }

  handleChange = ({ target: { value } }) => {
    this.setState({ email: value });
  };

  sendMail =(e) => {
    e.preventDefault();
    const { email } = this.state;
    const {
      EMAIL_IS_EMPTY,
      show,
      changeVisibilityComponent: changeVisibility } = this.props;
    if (email.length !== 0) {
      axios.post(`${forgotPassword}`,
        { mail: email })
        .then(() => {
          changeVisibility(show);
        }).catch((
          { response: { data: { error } } }) => {
          changeVisibility(error);
        });
    } else {
      changeVisibility(EMAIL_IS_EMPTY);
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
              to={baseUrl}
            >
              Cancel
            </Link >
          </div>
        </form>
        <Link
          className="pop-up__close"
          to={baseUrl}
        />
      </div>
    );
  }
}

ForgotPasswordForm.propTypes = {
  show: PropTypes.string.isRequired,
  EMAIL_IS_EMPTY: PropTypes.string.isRequired,
  HIDE_EMAIL_ERROR: PropTypes.string.isRequired,
  changeVisibilityComponent: PropTypes.func.isRequired,
  forgotPasswordErrorMessage: PropTypes.string.isRequired,
};

const mapDispatchToProps = dispatch => ({

  changeVisibilityComponent: bindActionCreators(
    changeVisibilityComponent, dispatch),
});

const mapStateToProps = state => (
  { forgotPasswordErrorMessage: state.forgotPasswordErrorMessage }
);

export default connect(mapStateToProps, mapDispatchToProps)(ForgotPasswordForm);

