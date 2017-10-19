import React from 'react';

export default class SignUp extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      fname: '',
      lname: '',
      mail: '',
      password: '',
      confirm: '',
      isRegistrated: true,
    };

    this.submitHandler = (e) => {
      // simulate bad credentials

      this.setState({
        isRegistrated: false,
      });

      e.preventDefault();
    };

    this.formChangeHandler = (e) => {
      const target = e.target;

      this.setState({
        [target.name]: target.value,
      });
    };
  }

  render() {
    return (
      <div className="sign-up-wrapper">
        <form
          className="sign-up"
          onSubmit={this.submitHandler}
          onChange={this.formChangeHandler}
        >
          <h2 className="form-title sign-up__title">create new account</h2>
          <label htmlFor="name" className="form-label form-label_required">
              first name:
          </label>
          <input
            type="text"
            id="name"
            name="fname"
            className="field sign-up__field"
            required
            maxLength="56"
          />
          <label htmlFor="surname" className="form-label form-label_required">
              last name:
          </label>
          <input
            type="text"
            id="surname"
            name="lname"
            className="field sign-up__field"
            required
            maxLength="56"
          />
          <label htmlFor="mail" className="form-label form-label_required">
              Email:
          </label>
          <input
            type="email"
            id="mail"
            name="mail"
            className="field sign-up__field"
            required
          />
          {/* {this.state.isValidCredentials ||
            (<span className="field-error">
            Please enter a valid email address</span>)} */}
          {/* {this.state.isValidCredentials ||
            ( <span className="field-error">There is an existing account
            associated with</span>)} */}
          <label
            htmlFor="password"
            className="form-label form-label_required"
          >
              password:
          </label>
          <input
            type="password"
            id="password"
            name="password"
            className="field sign-up__field"
            required
            minLength="6"
            maxLength="30"
          />
          {/* {this.state.isValidCredentials ||
            (<span className="field-error">Please use at least one non-space
            character in your password</span>)} */}
          <label htmlFor="confirm" className="form-label form-label_required">
              confirm password:
          </label>
          <input
            type="password"
            id="confirm"
            name="confirm"
            className="field sign-up__field"
            required
            minLength="6"
            maxLength="30"
          />
          {this.state.password !== this.state.confirm ?
            (<span className="field-error">Passwords do not match</span>)
            : null}
          {/* {this.state.isValidCredentials ||
            ( <span className="field-error">Passwords do not match</span>)} */}
          <input
            type="submit"
            className="sign-up__button btn"
            value="submit"
          />
        </form>
        {/* <div className="pop-up-wrapper"> */}
        {/* <div className="pop-up"> */}
        {/* <h3 className="pop-up__title"> */}
        {/* You&#39;ve successfully registered. */}
        {/* </h3> */}
        {/* <p className="pop-up__notification"> */}
        {/* <span className="pop-up__notification-user" />, go to */}
        {/* <span className="pop-up__notification-user" /> */}
        {/* to complete the sign-up process</p> */}
        {/* <button className="btn pop-up__button" >OK</button> */}
        {/* </div> */}
        {/* </div> */}
      </div>
    );
  }
}
