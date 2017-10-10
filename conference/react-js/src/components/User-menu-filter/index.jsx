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
import { speaker,
  organiser,
  admin } from '../../constants/roles';

const UserMenuFilter = ({ roles }) => {
  let data = [];
  switch (roles) {
  case speaker :
    data = [
      { title: 'Conferences', link: baseUrl },
      { title: 'My talks', link: myTalks },
      { title: 'My info', link: myInfo },
      { title: 'Talks', link: talks },
      { title: 'Settings', link: settings },
      { title: 'Sign Out', link: baseUrl, last: true },
    ];
    break;
  case organiser:

    data = [
      { title: 'Conferences', link: baseUrl },
      { title: 'Talks', link: talks },
      { title: 'Settings', link: settings },
      { title: 'Sign Out', link: baseUrl, last: true },
    ];
    break;
  case admin:

    data = [
      { title: 'Conferences', link: baseUrl },
      { title: 'Talks', link: talks },
      { title: 'Settings', link: settings },
      { title: 'Manage user', link: manageUser },
      { title: 'Sign Out', link: baseUrl, last: true },
    ];
    break;
  default:
    data = null;
  }
  return (
    <UserMenu data={data} />
  );
};

UserMenuFilter.propTypes = { roles: PropTypes.string.isRequired };

export default UserMenuFilter;

