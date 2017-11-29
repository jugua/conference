import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

import RaisedButton from 'material-ui/RaisedButton';

import { myEvents } from '../../constants/route-url';
import Talks from '../Talks';

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
    <Link to={myEvents} >
      <RaisedButton
        label="Back"
        primary
      />
    </Link>
    <Talks
      columns={columnsList}
      onClick={onClick}
      url={`/conference/${id}/talks`}
    />
  </div>
);

EventTalks.propTypes = { conference: PropTypes.shape({
  id: PropTypes.number,
}),
onClick: PropTypes.func.isRequired,
};

EventTalks.defaultProps = {
  conference: {},
};

const mapStateToProps = conference => conference;

export default connect(mapStateToProps)(EventTalks);
