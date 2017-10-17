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
  pattern,
  readonly,
  required,
  type,
  value,
}) => (
  <div>
    <label
      className={`form-label ${labelClass}`}
      htmlFor={id}
    >
      {label}
      <input
        disabled={disabled}
        id={id}
        className={`field ${inputClass}`}
        name={name}
        onChange={onChange}
        pattern={pattern}
        readOnly={readonly}
        required={required}
        type={type}
        value={value}
      />
    </label>
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
  pattern: PropTypes.string,
  readonly: PropTypes.bool,
  required: PropTypes.bool,
  type: PropTypes.string,
  value: PropTypes.string,
};

InputBlock.defaultProps = {
  disabled: false,
  id: null,
  inputClass: null,
  label: null,
  labelClass: null,
  onChange: null,
  pattern: null,
  readonly: false,
  required: false,
  type: 'text',
  value: null,
};

export default InputBlock;
