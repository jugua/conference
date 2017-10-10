import React from 'react';
import PropTypes from 'prop-types';

const Input = props => (

  <input
    type="text"
    className="field field_border"
    id={props.id}
    disabled={props.disabled}
  />
);

Input.propTypes = {
  id: PropTypes.string.isRequired,
  disabled: PropTypes.string.isRequired,
};

Input.defaultProps = {
  disabled: 'disabled',
};

export default Input;
