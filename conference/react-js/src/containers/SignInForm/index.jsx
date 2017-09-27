import React, { PureComponent } from 'react';
import classNames from 'classnames/bind';

class SignInForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      isValidCredentials: true,
    };

    this.submitHandler = (event) => {
      // simulate bad credentials
      this.setState({
        isValidCredentials: false,
      });

      event.preventDefault();
    };

    this.formChangeHandler = (event) => {
      const target = event.target;

      this.setState({
        [target.name]: target.value,
        isValidCredentials: true,
      });
    };
  }

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
            <a href="" className="sign-in__forgot">forgot password?</a>
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

        <a className="btn sign-in__create">create new account</a>

      </div>
    );
  }
}

export default SignInForm;
