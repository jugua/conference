import React from 'react';
import PropTypes from 'prop-types';

import InputBlock from '../../components/InputBlock';
import { emailPattern } from '../../constants/patterns';

const EmailChangeForm = ({
  oldMail, mail, submit, change, cancel,
}) => (
  <div className="settings__row settings__details">
    <div className="settings__title">Password</div>
    <form
      onSubmit={submit}
      onChange={change}
      className="settings__row-content"
    >
      <InputBlock
        label="Old email"
        name="oldEmail"
        value={oldMail}
        readonly
        onChange={() => false}
      />
      <InputBlock
        label="New email"
        name="mail"
        onChange={change}
        value={mail}
        required
        pattern={emailPattern.source}
      />
      <input
        type="submit"
        className="btn btn__inline"
        disabled={mail === oldMail}
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

EmailChangeForm.propTypes = {
  mail: PropTypes.string.isRequired,
  oldMail: PropTypes.string.isRequired,
  submit: PropTypes.func.isRequired,
  cancel: PropTypes.func.isRequired,
  change: PropTypes.func.isRequired,
};

export default EmailChangeForm;
