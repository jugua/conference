import React from 'react';
import PropTypes from 'prop-types';

const SlideBlock = (props) => {
  const { show,
    current,
    brief: briefString,
    children,
    header } = props;

  const toggle = () => {
    show(header);
  };

  const details = (
    <div className="settings__details settings__row">
      <div className="settings__title">{header}</div>
      { children }
    </div>
  );

  const brief = (
    <div
      className="settings__brief settings__row"
      role="button"
      onClick={toggle}
      tabIndex={0}
    >
      <div className="settings__title">
        {header}
      </div>
      <div className="settings__row-content">
        {briefString}
      </div>
    </div>
  );

  return header === current ? details : brief;
};

SlideBlock.propTypes = {
  header: PropTypes.string.isRequired,
  brief: PropTypes.string.isRequired,
  show: PropTypes.func.isRequired,
  children: PropTypes.node,
};

export default SlideBlock;
