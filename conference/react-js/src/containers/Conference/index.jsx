import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

import Talks from '../Talks';

const Conference = ({ conference: {
  title },
match: { params } }) => (
  <div className="tabs-layout tabs-wrapper">
    <h1 className="tabs-title">{title}</h1>
    <Link
      to="/react/"
      className="conference_button"
    >
      back
    </Link>
    <Talks url={`/conference/${params.id}/talks`} />
  </div>
);

Conference.propTypes = { conference: PropTypes.shape({
  title: PropTypes.string,
}),
match: PropTypes.shape({
  params: PropTypes.shape({
    id: PropTypes.string,
  }).isRequired,
}).isRequired,
};

Conference.defaultProps = {
  conference: {},
};

const mapStateToProps = conference => conference;

export default connect(mapStateToProps)(Conference);
