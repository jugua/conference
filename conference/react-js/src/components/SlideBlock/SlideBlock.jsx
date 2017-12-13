import React from 'react';
import PropTypes from 'prop-types';

const SlideBlock = ({ isOpened, children }) => (
  <div>
    {
      children[isOpened]
    }
  </div>
);

SlideBlock.propTypes = {
  isOpened: PropTypes.number.isRequired,
  children: PropTypes.arrayOf(PropTypes.node).isRequired,
};

export default SlideBlock;
