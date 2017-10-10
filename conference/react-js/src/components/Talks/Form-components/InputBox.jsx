import React from 'react';
import PropTypes from 'prop-types';

const InputBox = props => (
  <div className="input-box">
    <label
      htmlFor={props.id}
      className={`form-label ${props.labelClassName}`}
    >{props.label}</label>
    <input
      type="text"
      className="field field_border"
      id={props.id}
      disabled={props.disabled}
    />
  </div>
);

InputBox.propTypes = {
  id: PropTypes.string.isRequired,
  disabled: PropTypes.bool.isRequired,
  label: PropTypes.string.isRequired,
  labelClassName: PropTypes.string,
};

InputBox.defaultProps = {
  disabled: false,
  labelClassName: '',
};

export default InputBox;
