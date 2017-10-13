import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import SlideBlock from '../../components/SlideBlock';
import CustomForm from '../../components/CustomForm';
import InputBlock from '../../components/InputBlock';
import actionTypes from '../../constants/actions-types';
import changeEmail from '../../actions/changeEmail';
import changePassword from '../../actions/changePassword';
import changeName from '../../actions/changeName';
import {
  emailPattern,
  namePattern,
  passwordPattern,
} from '../../constants/patterns';
import { technicalError, expiredSession } from '../../constants/errors';

class SettingsPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      current: '',
      error: '',
      message: '',
    };
  }

  setCurrent = (header) => {
    this.setState({
      current: header,
      error: '',
      message: '',
    });
  };

  cancelAction = () => {
    this.setState({ current: '' });
  };

  saveEmail = ({ newEmail }) => {
    changeEmail(newEmail)
      .then(() => {
        this.props.updateUser({
          ...this.props.user,
          mail: newEmail,
        });

        this.setState({
          message: 'Please, check your email!',
        });
      })
      .catch(() => {
        let error = '';
        switch (status) {
        case 409:
          error = 'This email already exists';
          break;
        case 401:
          error = expiredSession;
          break;
        default:
          error = technicalError;
        }

        this.setState({
          error,
        });
      });
  };

  saveName = ({ firstName, lastName }) => {
    const updatedUser = {
      ...this.props.user,
      fname: firstName,
      lname: lastName,
    };

    changeName(updatedUser)
      .then(() => {
        this.props.updateUser(updatedUser);
        this.setState({
          message: 'Your info updated!',
        });
      })
      .catch(() => {
        this.setState({
          error: technicalError,
        });
      });
  };

  savePassword = ({ currentPassword, newPassword, confirmNewPassword }) => {
    if (newPassword === confirmNewPassword) {
      changePassword({ currentPassword, newPassword, confirmNewPassword })
        .then(() => {
          this.setState({
            message: 'Your password changed!',
          });
        })
        .catch(({ status }) => {
          let error = '';

          if (status === 403) {
            error = 'Wrong password';
          } else {
            error = technicalError;
          }

          this.setState({
            error,
          });
        });
    } else {
      this.setState({
        error: 'Passwords do not match',
      });
    }
  };

  render() {
    const { lname = '', fname = '', mail = '' } = this.props.user;
    const { error, message, current } = this.state;

    return (
      <div className="settings-wrapper">
        <div className="settings__block">
          <div className="settings__header">Account settings</div>

          { error && <div className="settings__info settings__error">
            {error}
          </div> }

          { message && <div className="settings__info settings__success">
            {message}
          </div> }

          <SlideBlock
            header="Email"
            current={current}
            brief={mail}
            show={this.setCurrent}
          >
            <CustomForm
              saveAction={this.saveEmail}
              cancelAction={this.cancelAction}
            >
              <InputBlock
                name="oldEmail"
                label="Old email"
                type="email"
                value={mail}
                pattern={emailPattern}
                readonly
              />
              <InputBlock
                name="newEmail"
                label="New email"
                type="email"
                pattern={emailPattern}
              />
            </CustomForm>
          </SlideBlock>

          <SlideBlock
            header="Name"
            current={current}
            brief={`${fname} ${lname}`}
            show={this.setCurrent}
          >
            <CustomForm
              saveAction={this.saveName}
              cancelAction={this.cancelAction}

              inputs={[
                {
                  name: 'firstName',
                  id: 'first-name',
                  label: 'First name',
                  type: 'text',
                  value: fname,
                  pattern: namePattern.source,
                  required: true,
                },
                {
                  name: 'lastName',
                  id: 'last-name',
                  label: 'Last name',
                  type: 'text',
                  value: lname,
                  pattern: namePattern.source,
                  required: true,
                },
              ]}
            />
          </SlideBlock>

          <SlideBlock
            header="Password"
            current={current}
            brief="******"
            show={this.setCurrent}
          >
            <CustomForm
              saveAction={this.savePassword}
              cancelAction={this.cancelAction}
              inputs={[
                {
                  name: 'currentPassword',
                  id: 'current-password',
                  label: 'Current password',
                  type: 'password',
                  value: '',
                  pattern: passwordPattern.source,
                  required: true,
                },
                {
                  name: 'newPassword',
                  id: 'new-password',
                  label: 'New password',
                  type: 'password',
                  value: '',
                  pattern: passwordPattern.source,
                  required: true,
                },
                {
                  name: 'confirmNewPassword',
                  id: 'confirm-password',
                  label: 'Confirm password',
                  type: 'password',
                  value: '',
                  pattern: passwordPattern.source,
                  required: true,
                },
              ]}
            />
          </SlideBlock>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ user }) => ({
  user,
});

const mapDispatchToProps = dispatch => ({
  updateUser: (user) => {
    dispatch({
      type: actionTypes.SET_USER,
      payload: user,
    });
  },
});

SettingsPage.propTypes = {
  updateUser: PropTypes.func.isRequired,
  user: PropTypes.shape({
    id: PropTypes.number,
    roles: PropTypes.array,
    mail: PropTypes.string,
    fname: PropTypes.string,
    lname: PropTypes.string,
    bio: PropTypes.string,
    job: PropTypes.string,
    company: PropTypes.string,
    past: PropTypes.string,
    photo: PropTypes.string,
    linkedin: PropTypes.string,
    twitter: PropTypes.string,
    facebook: PropTypes.string,
    blog: PropTypes.string,
    info: PropTypes.string,
  }).isRequired,
};

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
