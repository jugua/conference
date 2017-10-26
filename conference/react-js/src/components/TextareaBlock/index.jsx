import React from 'react';
import PropTypes from 'prop-types';

const TextareaBlock = props => (
  <div className="textarea-block">
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
      value={props.value}
    />
  </div>
);

TextareaBlock.propTypes = {
  name: PropTypes.string,
  id: PropTypes.string,
  className: PropTypes.string,
  labelClassName: PropTypes.string,
  label: PropTypes.string,
  maxLen: PropTypes.number,
  rows: PropTypes.number,
  disabled: PropTypes.bool,
  value: PropTypes.string,
};

TextareaBlock.defaultProps = {
  name: null,
  id: null,
  className: 'textarea',
  labelClassName: '',
  label: null,
  maxLen: null,
  disabled: false,
  rows: null,
  value: undefined,
};

export default TextareaBlock;

