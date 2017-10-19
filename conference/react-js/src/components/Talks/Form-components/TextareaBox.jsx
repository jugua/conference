import React from 'react';
import PropTypes from 'prop-types';

const TextareaBox = props => (
  <div className="input-box">
    <label
      htmlFor={props.id}
      className={`form-label ${props.labelClassName}`}
    >{props.label}</label>
    <textarea
      name={props.name}
      id={props.id}
      className={props.className}
      maxLength={props.maxLen}
      rows={props.rows}
      disabled={props.disabled}
    />
  </div>
);

TextareaBox.propTypes = {
  name: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  className: PropTypes.string.isRequired,
  labelClassName: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  maxLen: PropTypes.number,
  rows: PropTypes.number,
  disabled: PropTypes.string.isRequired,
};

TextareaBox.defaultProps = {
  className: 'textarea',
  disabled: 'disabled',
  labelClassName: '',
  maxLen: null,
  rows: null,
};

export default TextareaBox;

