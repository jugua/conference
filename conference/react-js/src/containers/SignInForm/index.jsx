import React, { PureComponent } from 'react';

class SignInForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      isValidCredentials: true,
    };

    this.emailChangeHandler = this.emailChangeHandler.bind(this);
    this.submitHandler = this.submitHandler.bind(this);
    this.passwordChangeHandler = this.passwordChangeHandler.bind(this);
  }

  submitHandler(event) {
    // simulate bad credentials
    this.setState({
      isValidCredentials: false,
    });

    event.preventDefault();
  }

  emailChangeHandler(event) {
    const target = event.target;

    this.setState({
      email: target.value,
      isValidCredentials: true,
    });
  }

  passwordChangeHandler(event) {
    const target = event.target;

    this.setState({
      password: target.value,
      isValidCredentials: true,
    });
  }

  render() {
    return (
      <div className="sign-in-wrapper">
        <form className="sign-in" onSubmit={this.submitHandler}>
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
            className={this.state.isValidCredentials ?
              'field' :
              'field field__invalid'}
            value={this.state.email}
            onChange={this.emailChangeHandler}
            required
          />
          {/* <span className="field-error"> */}
          {/* We can not find an account with that email address */}
          {/* </span> */}
          {/* <span className="field-error"> */}
          {/* You have to go to your email and confirm your account */}
          {/* </span> */}
          <div className="sign-in__password-cont">
            <label htmlFor="sign-in-password" className="form-label">
              password:
            </label>
            <a href="" className="sign-in__forgot">forgot password?</a>
          </div>
          <input
            type="password"
            name="password"
            className={this.state.isValidCredentials ?
              'field' :
              'field field__invalid'}
            id="sign-in-password"
            onChange={this.passwordChangeHandler}
            value={this.state.password}
          />
          {
            this.state.isValidCredentials ?
              '' :
              <span className="field-error">
                Your credentials is incorrect
              </span>
          }

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
