import React from 'react';
import axios from 'axios';
import { registrationUrl } from '../../constants/backend-url';
import SignUpPopUp from './SignUpPopUp/SignUpPopUp';

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
      responseOk: false,
    };

    this.formChangeHandler = (e) => {
      const target = e.target;

      this.setState({
        [target.name]: target.value,
      });
    };

    const registration = ({ mail, password, confirm, fname, lname }) => {
      const body = { mail, password, confirm, fname, lname };
      return axios.post(registrationUrl, body);
    };

    this.submitHandler = (e) => {
      e.preventDefault();
      registration(this.state)
        .then(() => (this.setState({ responseOk: true })))
        .catch(error => console.log(error));
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
        {this.state.responseOk === true ? <SignUpPopUp
          userName={this.state.fname}
          mail={this.state.mail}
        /> : null}
      </div>
    );
  }
}
