import React from 'react';
import { PropTypes } from 'prop-types';
import { Link } from 'react-router-dom';

const PopUpMessage = ({ header, body, url }) => (
  <div className="pop-up-wrapper">
    <div className="pop-up" >
      <h3 className="pop-up__title">
        {header}
      </h3>
      <p className="pop-up__notification">
        {body}
      </p>
      <Link
        className="btn pop-up__button"
        to={url}
      >
        Close
      </Link >
    </div>
  </div>);

PopUpMessage.propTypes = {
  header: PropTypes.string.isRequired,
  body: PropTypes.string.isRequired,
  url: PropTypes.string.isRequired,
};

export default PopUpMessage;
