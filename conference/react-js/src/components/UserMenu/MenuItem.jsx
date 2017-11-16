import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const MenuItem = ({
  title,
  link,
  click,
  className,
}) => (
  <li
    key={title}
  >
    <Link
      to={link}
      onClick={click}
      className={className}
    >
      {title}
    </Link>
  </li>
);

MenuItem.defaultProps = {
  click: null,
  className: '',
};

MenuItem.propTypes = {
  title: PropTypes.string.isRequired,
  link: PropTypes.string.isRequired,
  click: PropTypes.func,
  className: PropTypes.string,
};

export default MenuItem;
