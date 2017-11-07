import React from 'react';
import classNames from 'classnames';
import PropTypes from 'prop-types';

const SettingsInfo = ({ message, error }) => (
  <div className={classNames({
    settings__info: true,
    settings__error: error,
    settings__success: message,
  })}
  >
    { error || message }
  </div>
);

SettingsInfo.defaultProps = {
  message: null,
  error: null,
};

SettingsInfo.propTypes = {
  message: PropTypes.string,
  error: PropTypes.string,
};

export default SettingsInfo;
