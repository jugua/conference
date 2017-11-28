import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import changePassword from '../../actions/changePassword';
import changeEmail from '../../actions/changeEmail';
import userShape from '../../constants/user-shape';
import changeUserInfo from '../../actions/changeUserInfo';

import SlideBlock from '../../components/SlideBlock';
import NameBrief from '../../components/Settings/NameBrief/NameBrief';
import EmailBrief from '../../components/Settings/EmailBrief/EmailBrief';
import PasswordBrief
  from '../../components/Settings/PasswordBrief/PasswordBrief';
import NameChangeForm
  from '../../components/Settings/NameChangeForm/NameChangeForm';
import EmailChangeForm
  from '../../components/Settings/EmailChangeForm/EmailChangeForm';
import PasswordChangeForm
  from '../../components/Settings/PasswordChangeForm/PasswordChangeForm';

class SettingsContainer extends PureComponent {
  constructor(props) {
    const { user } = props;
    super(props);
    this.state = {
      ...user,
      oldMail: user.email,
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
      currentBlock: null,
    };
  }

  componentWillReceiveProps(nextProps) {
    this.setDefaultValues(nextProps);
  }

  setDefaultValues = ({ user }) => {
    this.setState({
      ...user,
      oldMail: user.email,
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
    });
  };

  showBlock = (title) => {
    this.setDefaultValues(this.props);
    this.setState({ currentBlock: title });
    this.props.reset();
  };

  change = ({ target: { name, value } }) => {
    this.setState({
      [name]: value,
    });
  };

  showInfo = ({ error, message }) => {
    const { setMessage, setError } = this.props;
    if (error) {
      setError(error);
    } else {
      setMessage(message);
      this.hideBlocks();
    }
  };

  submitEmail = (e) => {
    e.preventDefault();
    const { email } = this.state;

    changeEmail(email)
      .then(this.showInfo);
  };

  submitName = (e) => {
    e.preventDefault();
    const { editUser, userKeys } = this.props;

    editUser(this.state, userKeys)
      .then((res) => {
        this.showInfo(res);
      });
  };

  submitPassword = (e) => {
    e.preventDefault();
    const {
      newPassword,
      confirmNewPassword,
      currentPassword,
    } = this.state;

    if (newPassword === confirmNewPassword) {
      changePassword({ currentPassword, newPassword, confirmNewPassword })
        .then(this.showInfo);
    } else {
      this.props.setError('Passwords do not match');
    }
  };

  hideBlocks = () => {
    this.setDefaultValues(this.props);
    this.setState({ currentBlock: null });
  };

  cancel = () => {
    this.hideBlocks();
    this.props.reset();
  };

  render() {
    const {
      firstName, lastName, email, oldMail,
      currentPassword, newPassword, confirmNewPassword,
      currentBlock,
    } = this.state;

    const emailTitle = 'Email';
    const nameTitle = 'Name';
    const passwordTitle = 'Password';

    return (
      <div>
        <SlideBlock isOpened={currentBlock === nameTitle}>
          <NameBrief
            title={nameTitle}
            show={() => this.showBlock(nameTitle)}
            firstName={firstName}
            lastName={lastName}
          />
          <NameChangeForm
            title={nameTitle}
            cancel={this.cancel}
            submit={this.submitName}
            change={this.change}
            firstName={firstName}
            lastName={lastName}
          />
        </SlideBlock>
        <SlideBlock isOpened={currentBlock === emailTitle}>
          <EmailBrief
            title={emailTitle}
            show={() => this.showBlock(emailTitle)}
            mail={oldMail}
          />
          <EmailChangeForm
            title={emailTitle}
            cancel={this.cancel}
            submit={this.submitEmail}
            change={this.change}
            email={email}
            oldMail={oldMail}
          />
        </SlideBlock>
        <SlideBlock isOpened={currentBlock === passwordTitle}>
          <PasswordBrief
            title={passwordTitle}
            show={() => this.showBlock(passwordTitle)}
          />
          <PasswordChangeForm
            title={passwordTitle}
            cancel={this.cancel}
            submit={this.submitPassword}
            change={this.change}
            currentPassword={currentPassword}
            newPassword={newPassword}
            confirmNewPassword={confirmNewPassword}
          />
        </SlideBlock>
      </div>
    );
  }
}

SettingsContainer.propTypes = {
  editUser: PropTypes.func.isRequired,
  setMessage: PropTypes.func.isRequired,
  setError: PropTypes.func.isRequired,
  reset: PropTypes.func.isRequired,
  user: PropTypes.shape(userShape).isRequired,
  userKeys: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default connect(
  ({ user, userKeys }) => ({ user, userKeys }),
  dispatch => ({
    editUser: (updatedUser, userKeys) => (
      dispatch(changeUserInfo(updatedUser, userKeys))
    ),
  }),
)(SettingsContainer);
