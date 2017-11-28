import React from 'react';
import PropTypes from 'prop-types';

const NameBrief = ({ firstName, lastName, show, title }) => (
  <div
    role="button"
    tabIndex={0}
    onClick={show}
    className="settings__brief settings__row"
  >
    <div className="settings__title">{title}</div>
    <div className="settings__row-content">{firstName} {lastName}</div>
  </div>
);

NameBrief.propTypes = {
  title: PropTypes.string.isRequired,
  firstName: PropTypes.string.isRequired,
  lastName: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
};

export default NameBrief;
