import React from 'react';
import PropTypes from 'prop-types';

const InputBlock = (props) => {
  const { name,
    id,
    label,
    type,
    readonly,
    pattern,
    value } = props;

  return (
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
};

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
  id: '',
  pattern: '',
  label: '',
  type: 'text',
  value: '',
  readonly: false,
};

export default InputBlock;
