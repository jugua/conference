import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Talks from '../Talks';

const Conference = ({ conference: {
  call_for_paper_end_date: callForPaperEndDate,
  call_for_paper_start_date: callForPaperStartDate,
  cfp_active: cfpActive,
  description,
  end_date: endDate,
  location,
  id,
  start_date: startDate,
  title,
} }) => (
  <div className="tabs-container conference-card">
    <div className="conference-card-title">
      <div className="сonference-card-title__link">
        {title}
      </div>
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
      {callForPaperStartDate || 'TBD'}
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
    <Talks url={`/conference/${id}/talks`} />
  </div>
);

Conference.propTypes = { conference: PropTypes.shape({
  call_for_paper_end_date: PropTypes.string,
  call_for_paper_start_date: PropTypes.string,
  cfp_active: false,
  description: PropTypes.string,
  end_date: PropTypes.string,
  location: PropTypes.string,
  start_date: PropTypes.string,
  title: PropTypes.string,
  id: PropTypes.number,
}) };

Conference.defaultProps = {
  conference: {},
};

const mapStateToProps = conference => conference;

export default connect(mapStateToProps)(Conference);
