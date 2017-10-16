import React from 'react';
import PropTypes from 'prop-types';

import InputBlock from '../../components/InputBlock';

const EmailChangeForm = ({
  oldMail, mail, submit, change, cancel,
}) => (
  <form
    onSubmit={submit}
    onChange={change}
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
);

EmailChangeForm.propTypes = {
  mail: PropTypes.string.isRequired,
  oldMail: PropTypes.string.isRequired,
  submit: PropTypes.func.isRequired,
  cancel: PropTypes.func.isRequired,
  change: PropTypes.func.isRequired,
};

export default EmailChangeForm;
