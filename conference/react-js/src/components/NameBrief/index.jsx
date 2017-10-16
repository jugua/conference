import React from 'react';
import PropTypes from 'prop-types';

const NameBrief = ({ fname, lname, show }) => (
  <div
    role="button"
    tabIndex={0}
    onClick={show}
    className="settings__brief settings__row"
  >
    <div className="settings__title">Name</div>
    <div className="settings__row-content">{`${fname} ${lname}`}</div>
  </div>
);

NameBrief.propTypes = {
  fname: PropTypes.string.isRequired,
  lname: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
};

export default NameBrief;
