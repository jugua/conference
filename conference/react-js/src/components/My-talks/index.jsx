import React from 'react';
import Talks from '../../containers/Talks';

const columsList = [
  'conferenceName',
  'id',
  'title',
  'status',
];

const MyTalks = () => (
  <Talks
    columns={columsList}
  />
);

export default MyTalks;
