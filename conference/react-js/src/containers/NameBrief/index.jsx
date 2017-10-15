import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

const BriefName = ({ fname, lname, show }) => (
  <div
    role="button"
    tabIndex={0}
    onClick={show}
  >
    <span className="col-md-4">Name</span>
    <span>{`${fname} ${lname}`}</span>
  </div>
);

const mapStateToProps = ({ user: { fname, lname } }) => ({ fname, lname });

BriefName.propTypes = {
  fname: PropTypes.string.isRequired,
  lname: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
};

export default connect(mapStateToProps)(BriefName);
