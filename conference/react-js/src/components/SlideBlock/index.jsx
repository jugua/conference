import React from 'react';
import PropTypes from 'prop-types';

const SlideBlock = ({ isOpened, children }) => (
  <div>
    {
      isOpened ?
        children[1] :
        children[0]
    }
  </div>
);

SlideBlock.propTypes = {
  isOpened: PropTypes.bool.isRequired,
  children: PropTypes.arrayOf(PropTypes.node).isRequired,
};

export default SlideBlock;
