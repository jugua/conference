import React from 'react';
import PropTypes from 'prop-types';

import InputBlock from '../../components/InputBlock';

const NameChangeForm = ({
  submit, change, fname, lname, cancel,
}) => (
  <form
    onSubmit={submit}
    onChange={change}
    className="settings__row-content"
  >
    <InputBlock
      value={fname}
      label="First name"
      name="fname"
    />
    <InputBlock
      value={lname}
      label="Last name"
      name="lname"
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
);

NameChangeForm.propTypes = {
  cancel: PropTypes.func.isRequired,
  change: PropTypes.func.isRequired,
  submit: PropTypes.func.isRequired,
  fname: PropTypes.string.isRequired,
  lname: PropTypes.string.isRequired,
};

export default NameChangeForm;
