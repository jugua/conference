import React from 'react';
import actions from '../../constants/actions-types';
import Talks from '../../containers/Talks';

const MyTalks = () => (
  <Talks
    columns={[
      'id',
      'title',
      'status',
      'conferenceName',
    ]}
    sort={actions.SORT_USER_TALKS}
  />
);

export default MyTalks;
