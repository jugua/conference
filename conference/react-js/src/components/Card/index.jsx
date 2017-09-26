import React from 'react';

function Card(args) {
  const { id,
    title,
    start_date: startDate,
    end_date: endDate,
    call_for_paper_end_date: callForPaperEndDate,
    call_for_paper_start_date: CallForPaperStartDate,
    description,
    location } = args.data;
  const { enableButton = true } = args;
  return (
    <div className="tabs-container conference-card">
      <div className="conference-card-title">
        <a
          href={`#/conference/${id}`}
          className="conference-card-title__link"
        >{title}</a>
      </div>
      {enableButton ? <button
        className="btn btn-right conference-card-title__btn"
      >
        Submit Talk
      </button> : ''}

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

export default Card;
