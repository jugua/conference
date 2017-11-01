import React from 'react';
import PropTypes from 'prop-types';

import InputBlock from '../InputBlock/InputBlock';
import { passwordPattern } from '../../constants/patterns';

const PasswordChangeForm = ({
  currentPassword, newPassword, confirmNewPassword,
  submit, cancel, change,
}) => (
  <div className="settings__row settings__details">
    <div className="settings__title">Password</div>
    <form
      onSubmit={submit}
      className="settings__row-content"
    >
      <InputBlock
        value={currentPassword}
        label="Current password"
        name="currentPassword"
        type="password"
        required
        pattern={passwordPattern.source}
        onChange={change}
      />
      <InputBlock
        value={newPassword}
        label="New password"
        name="newPassword"
        type="password"
        required
        pattern={passwordPattern.source}
        onChange={change}
      />
      <InputBlock
        value={confirmNewPassword}
        label="Confirm new password"
        name="confirmNewPassword"
        type="password"
        required
        pattern={passwordPattern.source}
        onChange={change}
      />
      <input
        type="submit"
        value="Save"
        className="btn btn__inline"
      />
      <input
        type="button"
        className="btn btn__inline"
        value="Cancel"
        onClick={cancel}
      />
    </form>
  </div>
);

PasswordChangeForm.propTypes = {
  cancel: PropTypes.func.isRequired,
  submit: PropTypes.func.isRequired,
  change: PropTypes.func.isRequired,
  currentPassword: PropTypes.string.isRequired,
  newPassword: PropTypes.string.isRequired,
  confirmNewPassword: PropTypes.string.isRequired,
};

export default PasswordChangeForm;
