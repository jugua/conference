import React from 'react';
import PropTypes from 'prop-types';
import UserMenu from '../User-menu';
import {
  settings,
  manageUser,
  talks,
  myInfo,
  root,
} from '../../constants/route-url';
import {
  admin,
} from '../../constants/roles';

const UserMenuFilter = ({ roles, logout, close }) => {
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
    { title: 'Sign Out', link: root, last: true, click: logout },
  );
  return (
    <UserMenu data={data} close={close} />
  );
};

UserMenuFilter.propTypes = {
  roles: PropTypes.arrayOf(PropTypes.string).isRequired,
  logout: PropTypes.func.isRequired,
  close: PropTypes.func.isRequired,
};

export default UserMenuFilter;

