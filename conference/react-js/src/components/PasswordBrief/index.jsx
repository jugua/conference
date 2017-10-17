import React from 'react';
import PropTypes from 'prop-types';

const PasswordBrief = ({ show, title }) => (
  <div
    role="button"
    tabIndex={0}
    onClick={show}
    className="settings__brief settings__row"
  >
    <div className="settings__title">{title}</div>
    <div className="settings__row-content">********</div>
  </div>
);

PasswordBrief.propTypes = {
  title: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
};

export default PasswordBrief;
