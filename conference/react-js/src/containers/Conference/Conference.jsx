import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { root } from '../../constants/route-url';

import Talks from '../Talks/Talks';

const Conference = ({ conference: {
  title,
  id },
}) => (
  <div className="tabs-layout tabs-wrapper">
    <h1 className="tabs-title">{title}</h1>
    <Link
      to={root}
      className="conference_button"
    >
      back
    </Link>
    <Talks url={`/conference/${id}/talks`} />
  </div>
);

Conference.propTypes = { conference: PropTypes.shape({
  title: PropTypes.string,
  id: PropTypes.number,
}),
};

Conference.defaultProps = {
  conference: {},
};

const mapStateToProps = conference => conference;

export default connect(mapStateToProps)(Conference);
