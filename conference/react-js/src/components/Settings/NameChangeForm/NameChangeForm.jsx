import React from 'react';
import PropTypes from 'prop-types';

import InputBlock from '../../../components/InputBlock/InputBlock';
import { namePattern } from '../../../constants/patterns';

const NameChangeForm = ({
  submit, change, firstName, lastName, cancel, title,
}) => (
  <div className="settings__row settings__details">
    <div className="settings__title">{title}</div>
    <form
      onSubmit={submit}
      className="settings__row-content"
    >
      <InputBlock
        value={firstName}
        label="First name"
        name="firstName"
        required
        pattern={namePattern.source}
        onChange={change}
      />
      <InputBlock
        value={lastName}
        label="Last name"
        name="lastName"
        required
        pattern={namePattern.source}
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

NameChangeForm.propTypes = {
  title: PropTypes.string.isRequired,
  cancel: PropTypes.func.isRequired,
  change: PropTypes.func.isRequired,
  submit: PropTypes.func.isRequired,
  firstName: PropTypes.string.isRequired,
  lastName: PropTypes.string.isRequired,
};

export default NameChangeForm;
