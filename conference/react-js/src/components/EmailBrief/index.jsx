import React from 'react';
import PropTypes from 'prop-types';

const BriefEmail = ({ mail, show }) => (
  <div
    role="button"
    onClick={show}
    tabIndex={0}
    className="settings__brief settings__row"
  >
    <div className="settings__title">Email</div>
    <div className="settings__row-content">{`${mail}`}</div>
  </div>
);

BriefEmail.propTypes = {
  mail: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
};

export default BriefEmail;
