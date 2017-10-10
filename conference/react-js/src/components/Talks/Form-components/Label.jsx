import React from 'react';
import PropTypes from 'prop-types';

const Label = props => (

  <label
    htmlFor={props.htmlFor}
    className={props.className}
  >{props.label}</label>
);

Label.propTypes = {
  htmlFor: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  className: PropTypes.string.isRequired,
};

export default Label;
