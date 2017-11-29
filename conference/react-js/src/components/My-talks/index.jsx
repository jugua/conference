import React from 'react';
import PropTypes from 'prop-types';
import Talks from '../../containers/Talks/Talks';

const columsList = [
  'conferenceName',
  'id',
  'title',
  'status',
];

const MyTalks = ({ onClick }) => (
  <Talks
    columns={columsList}
    onClick={onClick}
  />
);

MyTalks.propTypes = {
  onClick: PropTypes.func.isRequired,
};

export default MyTalks;
