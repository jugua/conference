import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

import InputBlock from '../InputBlock';

class PasswordChangeForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
    };
  }

  change = ({ target: { name, value } }) => {
    this.setState({
      [name]: value,
    });
  }

  submit = (e) => {
    e.preventDefault();
    this.props.cancel();
  }

  cancel = (e) => {
    e.preventDefault();
    this.props.cancel();
  }

  render() {
    const {
      currentPassword,
      newPassword,
      confirmNewPassword } = this.state;

    return (
      <form
        onSubmit={this.submit}
        onChange={this.change}
        className="settings__row-content"
      >
        <InputBlock
          value={currentPassword}
          label="Current password"
          name="currentPassword"
          type="password"
        />
        <InputBlock
          value={newPassword}
          label="New password"
          name="newPassword"
          type="password"
        />
        <InputBlock
          value={confirmNewPassword}
          label="Confirm new password"
          name="confirmNewPassword"
          type="password"
        />
        <input
          type="submit"
          className="btn btn__inline"
        />
        <input
          type="button"
          className="btn btn__inline"
          value="Cancel"
          onClick={this.cancel}
        />
      </form>
    );
  }
}

PasswordChangeForm.propTypes = {
  cancel: PropTypes.func.isRequired,
};

export default PasswordChangeForm;
