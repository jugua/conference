import React, { PureComponent } from 'react';
import classNames from 'classnames';
import {
  Link,
} from 'react-router-dom';

import login from '../../actions/login';
import loginValidation from '../../actions/loginVlidation';

class SignInForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      isValidCredentials: true,
    };
  }

  onLoginSuccess = (res) => {
    console.log(res);
  };

  onLoginFail = () => {
    this.setState({
      isValidCredentials: false,
    });
  };

  formChangeHandler = (event) => {
    const target = event.target;

    this.setState({
      [target.name]: target.value,
      isValidCredentials: true,
    });
  };

  submitHandler = (event) => {
    event.preventDefault();

    if (loginValidation(this.state)) {
      login(this.state).then(
        res => this.onLoginSuccess(res),
        () => this.onLoginFail(),
      );
    } else {
      this.onLoginFail();
    }
  };

  render() {
    return (
      <div className="sign-in-wrapper">
        <form
          className="sign-in"
          onSubmit={this.submitHandler}
          onChange={this.formChangeHandler}
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
            <Link
              to="/forgot-password"
            >
              forgot password?
            </Link >
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
            value="Sign In"
            className="btn sign-in__submit"
          />
        </form>

        <div className="sign-in__separator" />
        <Link
          className="btn sign-in__create"
          to="/sign-up"
        >
          create new account
        </Link >

      </div>
    );
  }
}

export default SignInForm;
