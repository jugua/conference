import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const menuList = data => data.map(element => (
  <li
    key={Object.keys(element)[0]}
    className={`menu-list__item
      ${Object.keys(element)[0].indexOf('Out') === -1 ?
    '' : 'menu-list__item_sign-out'} `}
  >
    <Link
      className="menu-list__title"
      to={Object.values(element)[0]}
    >
      {Object.keys(element)[0]}
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
