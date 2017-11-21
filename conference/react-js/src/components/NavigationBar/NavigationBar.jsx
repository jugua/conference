import React from 'react';
import PropTypes from 'prop-types';

const NavigationBar = ({ children }) => (
  <ul className="tabs-list">
    {
      children.map(child => (
        <li className="tabs-list__item" key={child.props.to}>
          {child}
        </li>
      ))
    }
  </ul>
);

NavigationBar.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.node,
    PropTypes.arrayOf(PropTypes.node),
  ]).isRequired,
};

export default NavigationBar;
