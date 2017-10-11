import React, { Component } from 'react';
import classNames from 'classnames';
import axios from 'axios';
import PropTypes from 'prop-types';
import { createNewUser } from '../../constants/backend-url';

class AddNewUser extends Component {
  constructor(props) {
    super(props);
    this.state = {
      addNewUser: {
        mail: '',
        password: '',
        confirm: '',
        fname: '',
        lname: '',
        roleName: 'ROLE_SPEAKER',

      },
      isIndentPassword: true,
      isEmailIsExist: false,
    };
  }

  setFieldValueToUserData = ({ target: { name, value } }) => (
    this.setState({
      addNewUser: {
        ...this.state.addNewUser,
        [name]: value,
      },
    })
  );
  stopPropaganation = e => (e.stopPropagation());

  sendData = () => {
    const { addNewUser } = this.state;
    this.setState({ isIndentPassword: true });
    axios.post(createNewUser,
      addNewUser).then(resp => (
      console.log(resp)
    )).catch(() => (
      this.setState({ isEmailIsExist: true })
    ),
    );
    return true;
  };

    validation = (pass, confirm) => (
      pass === confirm
    );

    submit = (e) => {
      e.preventDefault();
      const {
        addNewUser: {
          password, confirm,
        },
      } = this.state;
      const res = this.validation(password, confirm) ? this.sendData()
        : false;
      this.setState({
        isIndentPassword: res,
      });
    };

    render() {
      const errorIndentPassword = classNames({
        ' field-error_hidden': this.state.isIndentPassword,
        'field-error field-error_center': true,
      });

      const errorExistEmail = classNames({
        ' field-error_hidden': !this.state.isEmailIsExist,
        'field-error field-error_center': true,
      });
      const { toggleAddUserPopUp } = this.props;
      return (
        <div
          onClick={toggleAddUserPopUp}
          role="presentation"
          className="pop-up-wrapper"
        >
          <div
            role="presentation"
            onClick={this.stopPropaganation}
            className="pop-up pop-up_big"
          >
            <h3 className="pop-up__title">add new user</h3>
            <button
              onClick={toggleAddUserPopUp}
              className="pop-up__close"
            />
            <form
              onSubmit={this.submit}
              className="new-talk"
            >
              <label
                htmlFor="role-name"
                className="form-label form-label_required"
              >
                role
              </label>
              <select
                onChange={this.setFieldValueToUserData}
                name="roleName"
                id="role-name"
                className="new-talk__select"
                required="required"
              >
                <option
                  value="ROLE_SPEAKER"
                  label="Speaker"
                  selected="selected"
                >
                  Speaker
                </option>
                <option
                  value="ROLE_ORGANISER"
                  label="Organiser"
                >
                  Organiser
                </option>
              </select>
              <label
                htmlFor="add-user-name"
                className="form-label form-label_required"
              >
                first name:
              </label>
              <input
                onChange={this.setFieldValueToUserData}
                type="text"
                id="add-user-name"
                name="fname"
                className="field field_border"
                required="required"
                maxLength="56"
              />
              <label
                htmlFor="add-user-surname"
                className="form-label form-label_required"
              >last name:</label>
              <input
                onChange={this.setFieldValueToUserData}
                type="text"
                id="add-user-surname"
                name="lname"
                className="field field_border"
                required=""
                maxLength="56"
              />
              <label
                htmlFor="add-user-mail"
                className="form-label form-label_required"
              >Email:</label>
              <input
                onChange={this.setFieldValueToUserData}
                type="email"
                id="add-user-mail"
                name="mail"
                className="field field_border"
                required=""
              />
              <span
                className="field-error field-error_hidden"
              >
                  There is an existing account associated with
              </span>
              <label
                htmlFor="add-user-password"
                className="form-label form-label_required"
              >
                password:
              </label>
              <input
                onChange={this.setFieldValueToUserData}
                type="password"
                id="add-user-password"
                name="password"
                className="field field_border"
                required="required"
                minLength="6"
                maxLength="30"
              />
              <label
                htmlFor="confirm"
                className="form-label form-label_required"
              >
                confirm password:
              </label>
              <input
                onChange={this.setFieldValueToUserData}
                type="password"
                id="confirm"
                name="confirm"
                className="field field_border"
                required="required"
                minLength="6"
                maxLength="30"
              />
              <span className={errorIndentPassword}>
              The passwords does not indent;
              </span>
              <span
                className={errorExistEmail}
              >
                This email is already in use
              </span>
              <button
                type="submit"
                className="sign-up__button btn"
              >
                save
              </button>

            </form>
          </div>
        </div>);
    }
}

AddNewUser.propTypes = { toggleAddUserPopUp: PropTypes.func.isRequired };

export default AddNewUser;
