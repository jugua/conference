import React from 'react';
import PropTypes from 'prop-types';

const NameBrief = ({ fname, lname, show }) => (
  <div
    role="button"
    tabIndex={0}
    onClick={show}
  >
    <span className="">Name</span>
    <span>{`${fname} ${lname}`}</span>
  </div>
);

NameBrief.propTypes = {
  fname: PropTypes.string.isRequired,
  lname: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
};

export default NameBrief;
