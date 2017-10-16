import React from 'react';
import PropTypes from 'prop-types';

import InputBlock from '../../components/InputBlock';
import { namePattern } from '../../constants/patterns';

const NameChangeForm = ({
  submit, change, fname, lname, cancel,
}) => (
  <div className="settings__row settings__details">
    <div className="settings__title">Name</div>
    <form
      onSubmit={submit}
      onChange={change}
      className="settings__row-content"
    >
      <InputBlock
        value={fname}
        label="First name"
        name="fname"
        required
        pattern={namePattern.source}
      />
      <InputBlock
        value={lname}
        label="Last name"
        name="lname"
        required
        pattern={namePattern.source}
      />
      <input
        type="submit"
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
  cancel: PropTypes.func.isRequired,
  change: PropTypes.func.isRequired,
  submit: PropTypes.func.isRequired,
  fname: PropTypes.string.isRequired,
  lname: PropTypes.string.isRequired,
};

export default NameChangeForm;
