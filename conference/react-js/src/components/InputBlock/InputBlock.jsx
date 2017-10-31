import React from 'react';
import PropTypes from 'prop-types';

const InputBlock = ({
  disabled,
  id,
  inputClass,
  label,
  labelClass,
  name,
  onChange,
  onClick,
  pattern,
  readonly,
  required,
  type,
  value,
  maxLength,
  minLength,
  accept,
  size,
}) => (
  <div>
    <label
      className={`form-label ${labelClass}`}
      htmlFor={id}
    >
      {label}
    </label>
    <input
      disabled={disabled}
      id={id}
      className={`field ${inputClass}`}
      name={name}
      onChange={onChange}
      onClick={onClick}
      pattern={pattern}
      readOnly={readonly}
      required={required}
      type={type}
      maxLength={maxLength}
      minLength={minLength}
      value={value}
      accept={accept}
      size={size}
    />
  </div>
);

InputBlock.propTypes = {
  name: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
  id: PropTypes.string,
  inputClass: PropTypes.string,
  label: PropTypes.string,
  labelClass: PropTypes.string,
  onChange: PropTypes.func,
  onClick: PropTypes.func,
  pattern: PropTypes.string,
  readonly: PropTypes.bool,
  required: PropTypes.bool,
  type: PropTypes.string,
  value: PropTypes.string,
  maxLength: PropTypes.number,
  minLength: PropTypes.number,
  size: PropTypes.string,
  accept: PropTypes.string,
};

InputBlock.defaultProps = {
  disabled: false,
  id: null,
  inputClass: null,
  label: null,
  labelClass: null,
  onChange: null,
  onClick: null,
  pattern: null,
  readonly: false,
  required: false,
  type: 'text',
  value: undefined,
  maxLength: null,
  minLength: null,
  size: null,
  accept: null,
};

export default InputBlock;
