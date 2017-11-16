import React from 'react';
import PropTypes from 'prop-types';

import MenuItem from './MenuItem';
import { root, account } from '../../constants/route-url';

const menuItemClass = 'menu-list__item menu menu-list__title';
const signOutClass = `${menuItemClass} menu-list__item_sign-out`;

const UserMenu = ({ close, logout }) => (
  <div
    className="dropdown"
    role="menu"
    tabIndex={0}
    onClick={close}
  >
    <div className="menu-arrow" />
    <ul className="menu-list">
      <MenuItem
        title="Account"
        link={account}
        className={menuItemClass}
      />
      <MenuItem
        title="Sign out"
        link={root}
        className={signOutClass}
        click={logout}
      />
    </ul>
  </div>);

UserMenu.propTypes = {
  close: PropTypes.func.isRequired,
  logout: PropTypes.func.isRequired,
};

export default UserMenu;
