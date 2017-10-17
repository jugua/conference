import React, { Component } from 'react';
import axios from 'axios';
import { registrationUrl } from '../../constants/backend-url';
import SignUpPopUp from './SignUpPopUp/SignUpPopUp';
import ErrorMessage from './SignUpPopUp/ErrorMessages';
import RegistrationInputBox from './SignUpPopUp/RegistrationInputBox';

export default class SignUp extends Component {
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
      responseStatus: 0,
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
        .then(resp => (this.setState({
          responseOk: true,
          response: resp,
        })))
        .catch(error => (
          this.setState({
            responseStatus: error.response.status,
          })
        ));
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
          <RegistrationInputBox
            id="name"
            label="first name:"
            name="fname"
            maxLength={56}
          />
          <RegistrationInputBox
            id="surname"
            label="last name:"
            name="lname"
            maxLength={56}
          />
          <RegistrationInputBox
            id="mail"
            label="Email:"
            name="mail"
            inputType="email"
          />
          {this.state.responseStatus === 409 &&
            (<ErrorMessage errorMessage="There is an existing account
                 associated with"
            />)
          }
          <RegistrationInputBox
            id="password"
            label="password:"
            name="password"
            maxLength={30}
            minLength={6}
            inputType="password"
          />
          <RegistrationInputBox
            id="confirm"
            label="confirm password:"
            name="confirm"
            maxLength={30}
            minLength={6}
            inputType="password"
          />
          {this.state.password !== this.state.confirm ?
            (<ErrorMessage errorMessage="Passwords do not match" />)
            : null}
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
