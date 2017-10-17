import React from 'react';
import PropTypes from 'prop-types';
import UserMenu from '../User-menu';
import {
  baseUrl,
  settings,
  manageUser,
  talks,
  myInfo,
} from '../../constants/route-url';
import {
  admin,
} from '../../constants/roles';

const UserMenuFilter = ({ roles }) => {
  const data = [
    { title: 'Talks', link: talks },
  ];
  const adminRole = roles.indexOf(admin);
  if (adminRole > -1) {
    data.push({ title: 'Manage user', link: manageUser });
  }
  data.push(
    { title: 'My info', link: myInfo },
    { title: 'Settings', link: settings },
    { title: 'Sign Out', link: baseUrl, last: true },
  );
  return (
    <UserMenu data={data} />
  );
};

UserMenuFilter.propTypes = {
  roles: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default UserMenuFilter;

