import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import SlideBlock from '../../components/SlideBlock';
import actionTypes from '../../constants/actions-types';
import changeEmail from '../../actions/changeEmail';
import { emailPattern,
  namePattern } from '../../constants/patterns';

class SettingsPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      current: -1,
    };
  }

  componentWillMount() {
    // this.props.addMockUser();
  }

  showCurrent = (index) => {
    this.setState({ current: index });
  };

  cancelAction = () => {
    this.setState({ current: -1 });
  };

  saveEmail = ({ newEmail }) => {
    changeEmail(newEmail)
      .then(() => {
        this.props.updateUser({
          ...this.props.user,
          mail: newEmail,
        });
      })
      .catch((err) => { throw err; });
  };

  saveName = ({ firstName, lastName }) => {
    this.props.updateUser({
      ...this.props.user,
      fname: firstName,
      lname: lastName,
    });
  };

  savePassword = () => {

  };

  render() {
    const {
      lname = '',
      fname = '',
      mail = '',
    } = this.props.user;

    const blocks = [
      {
        header: 'Email',
        brief: mail,
        action: this.saveEmail,
        inputs: [
          {
            name: 'oldEmail',
            id: 'old-email',
            label: 'Old email',
            type: 'email',
            value: mail,
            readonly: true,
          },
          {
            name: 'newEmail',
            id: 'new-email',
            label: 'New email',
            type: 'text',
            value: '',
            pattern: emailPattern.source,
            required: true,
          },
        ],
      },
      {
        header: 'Name',
        brief: `${fname} ${lname}`,
        action: this.saveName,
        inputs: [
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
        ],
      },
    ];

    return (
      <div className="settings-wrapper">
        <div className="settings__block">
          {
            blocks.map(({
              brief,
              header,
              action,
              inputs,
              readonly,
              required,
              pattern,
            }, index) => (
              <SlideBlock
                index={index}
                brief={brief}
                readonly={readonly || false}
                pattern={pattern || null}
                key={header}
                header={header}
                saveAction={action}
                cancelAction={this.cancelAction}
                inputs={inputs}
                isOpened={index === this.state.current}
                show={this.showCurrent}
                required={required || false}
              />))
          }
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
  // addMockUser: () => {
  //   dispatch({
  //     type: actionTypes.SET_USER,
  //     payload: {
  //       bio: '',
  //       blog: null,
  //       company: '',
  //       facebook: null,
  //       fname: 'Organiser',
  //       id: 3,
  //       info: null,
  //       job: '',
  //       linkedin: null,
  //       lname: 'Organiser',
  //       mail: 'organiser@gmail.com',
  //       past: null,
  //       photo: null,
  //       roles: ['ROLE_ORGANISER'],
  //       twitter: null,
  //     },
  //   });
  // },
});

SettingsPage.propTypes = {
  // addMockUser: PropTypes.func.isRequired,
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
