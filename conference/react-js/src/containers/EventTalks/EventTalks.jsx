import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import Talks from '../Talks/Talks';

const columnsList = [
  'name',
  'title',
  'topic',
  'status',
  'comment',
];

const EventTalks = ({
  conference: {
    id },
  onClick,
}) => (
  <div className="tabs-layout">
    <Talks
      columns={columnsList}
      onClick={onClick}
      url={`/conference/${id}/talks`}
    />
  </div>
);

EventTalks.propTypes = {
  conference: PropTypes.shape({
    id: PropTypes.number,
  }),
  onClick: PropTypes.func.isRequired,
};

EventTalks.defaultProps = {
  conference: {},
};

const mapStateToProps = conference => conference;

export default connect(mapStateToProps)(EventTalks);
