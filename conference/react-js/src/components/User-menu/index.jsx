import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import classNames from 'classnames';

const menuList = data => data.map(({ title, link, last, click = null }) => (
  <li
    key={title}
    className={
      classNames(
        'menu-list__item',
        { 'menu-list__item_sign-out': last,
        })}
  >
    <Link
      className="menu-list__title"
      to={link}
      onClick={click}
    >
      {title}
    </Link>
  </li>
));

const UserMenu = ({ data, close }) => (
  <div
    className="dropdown"
    role="menu"
    tabIndex={0}
    onClick={close}
  >
    <div className="menu-arrow" />
    <ul className="menu-list">
      {menuList(data)}
    </ul>
  </div>);

UserMenu.propTypes = {
  data: PropTypes.arrayOf(PropTypes.object).isRequired,
  close: PropTypes.func.isRequired,
};

export default UserMenu;
