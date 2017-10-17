import React from 'react';
import PropTypes from 'prop-types';

const RegistrationInputBox = props => (
  <div className="input-box">
    <label
      htmlFor={props.id}
      className={'form-label form-label_required'}
    >{props.label}</label>
    <input
      type={props.inputType}
      name={props.name}
      className="field sign-up__field"
      id={props.id}
      maxLength={props.maxLength}
      minLength={props.minLength}
      required
    />
  </div>
);

RegistrationInputBox.propTypes = {
  id: PropTypes.string.isRequired,
  inputType: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  maxLength: PropTypes.number,
  minLength: PropTypes.number,
};

RegistrationInputBox.defaultProps = {
  inputType: 'text',
  maxLength: '',
  minLength: '',
};

export default RegistrationInputBox;
