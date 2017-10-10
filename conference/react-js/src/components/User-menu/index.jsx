import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const menuList = data => data.map(({ title, link, last }) => (
  <li
    key={title}
    className={`menu-list__item
      ${last ?
    'menu-list__item_sign-out' : ''} `}
  >
    <Link
      className="menu-list__title"
      to={link}
    >
      {title}
    </Link>
  </li>
));

const UserMenu = ({ data }) => (
  <div
    className="dropdown ng-scope"
  >
    <div className="menu-arrow" />
    <ul className="menu-list">
      {menuList(data)}
    </ul>
  </div>);

UserMenu.propTypes = {
  data: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default UserMenu;
