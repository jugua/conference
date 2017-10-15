import React from 'react';
import PropTypes from 'prop-types';

const InputBlock = ({
  name,
  id,
  label,
  type,
  readonly,
  pattern,
  value,
}) => (
  <div>
    <label
      className="form-label"
      htmlFor={id}
    >
      {label}
      <input
        className="field"
        name={name}
        id={id}
        type={type}
        readOnly={readonly}
        pattern={pattern}
        value={value}
      />
    </label>
  </div>
);

InputBlock.propTypes = {
  name: PropTypes.string.isRequired,
  id: PropTypes.string,
  pattern: PropTypes.string,
  label: PropTypes.string,
  type: PropTypes.string,
  value: PropTypes.string,
  readonly: PropTypes.bool,
};

InputBlock.defaultProps = {
  id: null,
  pattern: null,
  label: null,
  type: 'text',
  value: '',
  readonly: false,
};

export default InputBlock;
