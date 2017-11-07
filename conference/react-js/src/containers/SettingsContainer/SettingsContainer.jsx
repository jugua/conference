import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import editUser from '../../actions/edit-user';
import changePassword from '../../actions/change-password';
import changeEmail from '../../actions/change-email';
import changeUserInfo from '../../actions/change-user-info';

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
    super(props);
    this.state = {
      fname: '',
      lname: '',
      oldMail: '',
      mail: '',
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
      currentBlock: null,
    };
  }

  componentDidMount() {
    this.setDefaultValues(this.props);
  }

  componentWillReceiveProps(nextProps) {
    this.setDefaultValues(nextProps);
  }

  setDefaultValues = ({ fname, lname, mail }) => {
    this.setState({
      fname,
      lname,
      oldMail: mail,
      mail: '',
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
    });
  };

  showBlock = (title) => {
    this.setState({ currentBlock: title });
    this.setDefaultValues(this.props);
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
    const { mail } = this.state;

    changeEmail(mail)
      .then(this.showInfo);
  };

  submitName = (e) => {
    e.preventDefault();
    const { fname, lname } = this.state;
    const { edit, user } = this.props;

    changeUserInfo({ ...user, fname, lname })
      .then((res) => {
        this.showInfo(res);
        if (!res.error) {
          edit({ fname, lname });
        }
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
    this.setState({ currentBlock: null });
    this.setDefaultValues(this.props);
  };

  cancel = () => {
    this.hideBlocks();
    this.props.reset();
  };

  render() {
    const {
      fname, lname, mail, oldMail,
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
            fname={fname}
            lname={lname}
          />
          <NameChangeForm
            title={nameTitle}
            cancel={this.cancel}
            submit={this.submitName}
            change={this.change}
            fname={fname}
            lname={lname}
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
            mail={mail}
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

const mapStateToProps = ({
  user: { fname, lname, mail },
  user,
}) => ({
  fname, lname, mail, user,
});

SettingsContainer.propTypes = {
  edit: PropTypes.func.isRequired,
  setMessage: PropTypes.func.isRequired,
  setError: PropTypes.func.isRequired,
  reset: PropTypes.func.isRequired,
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

export default connect(
  mapStateToProps,
  { edit: editUser },
)(SettingsContainer);
