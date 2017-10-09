import React from 'react';
import PropTypes from 'prop-types';

function Card({ data: {
  call_for_paper_end_date: callForPaperEndDate,
  call_for_paper_start_date: CallForPaperStartDate,
  cfp_active: cfpActive,
  description,
  end_date: endDate,
  location,
  id,
  start_date: startDate,
  title,
} }) {
  return (
    <div className="tabs-container conference-card">
      <div className="conference-card-title">
        <a
          href={`#/conference/${id}`}
          className="conference-card-title__link"
        >{title}</a>
      </div>
      {cfpActive && (<button
        className="btn btn-right conference-card-title__btn"
      >
        Submit Talk
      </button>)}

      <div className="conference-card-dates">
        <span className="conference-card-label">Dates:</span>
        {startDate || 'TBD'}
        —
        {endDate || 'TBD'}
      </div>
      <div className="conference-card-dates_cfp">
        <span className="conference-card-label">Call For Papers:</span>
        {callForPaperEndDate || 'TBD'}
        —
        {CallForPaperStartDate || 'TBD'}
      </div>
      <div className="conference-card-description">
        {description}
      </div>
      <div className="conference-card-location">
        <span
          className="conference-card-label"
        >Location:</span>
        {location}
      </div>
    </div>
  );
}

Card.propTypes = { data: PropTypes.shape({
  call_for_paper_end_date: PropTypes.string,
  call_for_paper_start_date: PropTypes.string,
  cfp_active: false,
  description: PropTypes.string,
  end_date: PropTypes.string,
  location: PropTypes.string,
  id: PropTypes.number,
  start_date: PropTypes.string,
  title: PropTypes.string,
}) };

Card.defaultProps = {
  data: {},
};

export default Card;
