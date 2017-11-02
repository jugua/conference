import React from 'react';
import PropTypes from 'prop-types';
import UserMenu from '../User-menu';
import {
  account,
  manageUser,
  talks,
  root,
  history,
} from '../../constants/route-url';
import {
  admin,
} from '../../constants/roles';

const UserMenuFilter = ({ role, logout, close }) => {
  const data = [{ title: 'History', link: history }];
  if (role === admin) {
    data.push({ title: 'Talks', link: talks },
      { title: 'Manage user', link: manageUser },
    );
  }
  data.push(
    { title: 'Account', link: account },
    { title: 'Sign Out', link: root, last: true, click: logout },
  );
  return (
    <UserMenu data={data} close={close} />
  );
};

UserMenuFilter.propTypes = {
  role: PropTypes.string.isRequired,
  logout: PropTypes.func.isRequired,
  close: PropTypes.func.isRequired,
};

export default UserMenuFilter;
