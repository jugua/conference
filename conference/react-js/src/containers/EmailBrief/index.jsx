import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

const BriefEmail = ({ mail, show }) => (
  <div
    role="button"
    onClick={show}
    tabIndex={0}
  >
    <div>
      <span className="">Email</span>
      <span>{`${mail}`}</span>
    </div>
  </div>
);

const mapStateToProps = ({ user: { mail } }) => ({ mail });

BriefEmail.propTypes = {
  mail: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
};

export default connect(mapStateToProps)(BriefEmail);
