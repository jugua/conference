import React from 'react';
import PropTypes from 'prop-types';

const Textarea = props => (
  <textarea
    name={props.name}
    id={props.id}
    className={props.className}
    maxLength={props.maxLen}
    rows={props.rows}
    disabled={props.disabled}
  />
);

Textarea.propTypes = {
  name: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  className: PropTypes.string.isRequired,
  maxLen: PropTypes.number.isRequired,
  rows: PropTypes.number.isRequired,
  disabled: PropTypes.string.isRequired,
};

Textarea.defaultProps = {
  className: 'textarea',
  disabled: 'disabled',
};

export default Textarea;
