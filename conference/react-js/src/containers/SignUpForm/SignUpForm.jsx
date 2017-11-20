import React, { Component } from 'react';
import axios from 'axios';
import { registrationUrl } from '../../constants/backend-url';
import SignUpPopUp from './SignUpPopUp/SignUpPopUp';
import ErrorMessage from './SignUpPopUp/ErrorMessages';
import InputBlock from '../../components/InputBlock/InputBlock';

class SignUp extends Component {
  constructor(props) {
    super(props);

    this.state = {
      fname: '',
      lname: '',
      mail: '',
      password: '',
      confirm: '',
      passwordLength: false,
      isRegistrated: true,
      responseOk: false,
      responseStatus: 0,
    };
  }

    formChangeHandler = (e) => {
      const target = e.target;

      this.setState({
        [target.name]: target.value,
      });
    };

    registration = ({ mail, password, confirm, fname, lname }) => {
      const body = { mail, password, confirm, fname, lname };
      return axios.post(registrationUrl, body);
    };

    submitHandler = (e) => {
      e.preventDefault();
      this.registration(this.state)
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

    checkPasswordLength = () => {
      if (this.state.password.length < 6) {
        this.setState({
          passwordLength: true,
        });
      } else {
        this.setState({
          passwordLength: false,
        });
      }
    };

    render() {
      return (
        <div className="sign-up-wrapper">
          <form
            className="sign-up"
            onSubmit={this.submitHandler}
            onChange={this.formChangeHandler}
          >
            <h2 className="form-title sign-up__title">create new account</h2>
            <InputBlock
              id="name"
              label="first name:"
              labelClass="form-label_required"
              inputClass="sign-up__field"
              name="fname"
              maxLength={56}
              required
            />
            <InputBlock
              id="surname"
              label="last name:"
              labelClass="form-label_required"
              inputClass="sign-up__field"
              name="lname"
              maxLength={56}
              required
            />
            <InputBlock
              id="mail"
              type="email"
              label="Email:"
              labelClass="form-label_required"
              inputClass="sign-up__field"
              name="mail"
              required
            />
            {this.state.responseStatus === 409 &&
            (<ErrorMessage errorMessage="There is an existing account
                 associated with"
            />)
            }
            <InputBlock
              id="password"
              type="password"
              label="password:"
              labelClass="form-label_required"
              inputClass="sign-up__field"
              onBlur={this.checkPasswordLength}
              name="password"
              maxLength={30}
              minLength={6}
              required
            />
            {this.state.passwordLength &&
          (<ErrorMessage
            errorMessage="Must be longer than 6 symbols"
          />)
            }
            <InputBlock
              id="confirm"
              type="password"
              label="confirm password:"
              labelClass="form-label_required"
              inputClass="sign-up__field"
              name="confirm"
              maxLength={30}
              minLength={6}
              required
            />
            {this.state.password !== this.state.confirm ?
              (<ErrorMessage errorMessage="Passwords do not match" />)
              : null}
            <input
              type="submit"
              id="btn-sign-up"
              className="sign-up__button btn"
              value="submit"
            />
          </form>
          {this.state.responseOk && <SignUpPopUp
            userName={this.state.fname}
            mail={this.state.mail}
          /> }
        </div>
      );
    }
}

export default SignUp;
