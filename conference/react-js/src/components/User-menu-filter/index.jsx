import React from 'react';
import PropTypes from 'prop-types';
import UserMenu from '../User-menu';
import {
  baseUrl,
  settings,
  manageUser,
  talks,
  myTalks,
  myInfo,
} from '../../constants/route-url';
import {
  speaker,
  admin,
} from '../../constants/roles';

const UserMenuFilter = ({ roles }) => {
  let data = [
    { title: 'Conferences', link: baseUrl },
    { title: 'Talks', link: talks },
  ];
  const speakerRole = roles.indexOf(speaker);
  const adminRole = roles.indexOf(admin);
  if (speakerRole > -1) {
    data = [...data, { title: 'My talks', link: myTalks },
      { title: 'My info', link: myInfo }];
  } else if (adminRole > -1) {
    data = [...data, { title: 'Manage user', link: manageUser }];
  }
  data = [...data,
    { title: 'Settings', link: settings },
    { title: 'Sign Out', link: baseUrl, last: true },
  ];
  return (
    <UserMenu data={data} />
  );
};

UserMenuFilter.propTypes = {
  roles: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default UserMenuFilter;

