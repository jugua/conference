import React from 'react';
import PropTypes from 'prop-types';

const PasswordBrief = ({ show }) => (
  <div
    role="button"
    tabIndex={0}
    onClick={show}
    className="settings__brief settings__row"
  >
    <div className="settings__title">Password</div>
    <div className="settings__row-content">********</div>
  </div>
);

PasswordBrief.propTypes = {
  show: PropTypes.func.isRequired,
};

export default PasswordBrief;
