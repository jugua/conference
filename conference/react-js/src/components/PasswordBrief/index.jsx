import React from 'react';
import PropTypes from 'prop-types';

const PasswordBrief = ({ show }) => (
  <div
    role="button"
    tabIndex={0}
    onClick={show}
  >
    <span className="">Password</span>
    <span>********</span>
  </div>
);

PasswordBrief.propTypes = {
  show: PropTypes.func.isRequired,
};

export default PasswordBrief;
