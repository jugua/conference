import React from 'react';
import actions from '../../constants/actions-types';
import Talks from '../../containers/Talks';

const columsList = [
  'id',
  'title',
  'status',
  'conferenceName',
];

const MyTalks = () => (
  <Talks
    columns={columsList}
    sort={actions.SORT_USER_TALKS}
  />
);

export default MyTalks;
