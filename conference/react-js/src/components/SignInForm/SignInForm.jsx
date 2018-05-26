import React, { PureComponent } from 'react';
import classNames from 'classnames';
import { Link, withRouter } from 'react-router-dom';
import PropTypes from 'prop-types';

import { signUp, root } from '../../constants/route-url';
import login from '../../actions/login';
import loginValidation from '../../actions/loginVlidation';
import actionTypes from '../../constants/actions-types';

class SignInForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      isValidCredentials: true,
    };
  }

  onLoginSuccess = ({ data }) => {
    const { load, close, history } = this.props;
    load(actionTypes.SET_USER, data);
    history.push(root);
    close();
  };

  onForgotPassword = () => {
    this.props.close();
    this.props.setForgotPasswordVisibility(true);
  }

  onLoginFail = () => {
    this.setState({
      isValidCredentials: false,
    });
  };

  onChange = ({ target: { name, value } }) => {
    this.setState({
      [name]: value,
      isValidCredentials: true,
    });
  };

  onSubmit = (event) => {
    event.preventDefault();
    if (loginValidation(this.state)) {
      login(this.state)
        .then(this.onLoginSuccess)
        .catch(this.onLoginFail);
    } else {
      this.onLoginFail();
    }
  };

  render() {
    const { close } = this.props;
    return (
      <div className="sign-in-wrapper">
        <form
          className="sign-in"
          onSubmit={this.onSubmit}
          onChange={this.onChange}
        >
          <h2 className="form-title sign-in__title">sign in</h2>
          <label
            htmlFor="sign-in-email"
            className="sign-in__label form-label"
          >
            email:
          </label>
          <input
            type="email"
            name="email"
            id="sign-in-email"
            className={classNames({
              field: true,
              field__invalid: !this.state.isValidCredentials,
            })}
            value={this.state.email}
            required
          />
          <div className="sign-in__password-cont">
            <label htmlFor="sign-in-password" className="form-label">
              password:
            </label>
            <a
              id="lnk-forgot-password"
              role="button"
              onClick={this.onForgotPassword}
              className="sign-in__forgot"
              tabIndex="-1"
            >
              forgot password?
            </a>
          </div>
          <input
            type="password"
            name="password"
            className={classNames({
              field: true,
              field__invalid: !this.state.isValidCredentials,
            })}
            id="sign-in-password"
            value={this.state.password}
            required
          />
          { this.state.isValidCredentials || (
            <span className="field-error">
                Your credentials is incorrect
            </span>) }

          <input
            type="submit"
            id="btn-sign-in"
            value="Sign In"
            className="btn sign-in__submit"
          />
        </form>

        <div className="sign-in__separator" />
        <Link
          className="btn sign-in__create"
          id="lnk-sign-up"
          onClick={close}
          to={signUp}
        >
          create new account
        </Link >

      </div>
    );
  }
}

SignInForm.propTypes = {
  load: PropTypes.func.isRequired,
  close: PropTypes.func.isRequired,
  history: PropTypes.shape({}).isRequired,
  setForgotPasswordVisibility: PropTypes.func.isRequired,
};

export default withRouter(SignInForm);
