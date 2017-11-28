import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { myEvents } from '../../constants/route-url';

import Talks from '../Talks';

const EventTalks = ({ conference: {
  title,
  id },
onClick,
}) => (
  <div className="tabs-layout">
    <h1 className="tabs-title">{title}</h1>
    <Link
      to={myEvents}
      // className="conference_button"
    >
      back
    </Link>
    <Talks onClick={onClick} url={`/conference/${id}/talks`} />
  </div>
);

EventTalks.propTypes = { conference: PropTypes.shape({
  title: PropTypes.string,
  id: PropTypes.number,
}),
onClick: PropTypes.func.isRequired,
};

EventTalks.defaultProps = {
  conference: {},
};

const mapStateToProps = conference => conference;

export default connect(mapStateToProps)(EventTalks);
