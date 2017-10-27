import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

function Conference({ conference }) {
  const { call_for_paper_end_date: callForPaperEndDate,
    call_for_paper_start_date: CallForPaperStartDate,
    cfp_active: cfpActive,
    description,
    end_date: endDate,
    location,
    id,
    start_date: startDate,
    title } = conference;
  return (
    <div>
      <p>Conference callForPaperEndDate {callForPaperEndDate} </p>
      <p>Conference CallForPaperStartDate {CallForPaperStartDate} </p>
      <p>Conference cfpActive {cfpActive} </p>
      <p>Conference description {description} </p>
      <p>Conference endDate {endDate} </p>
      <p>Conference location {location} </p>
      <p>Conference id {id} </p>
      <p>Conference startDate {startDate} </p>
      <p>Conference title {title} </p>
    </div>
  );
}

Conference.propTypes = { conference: PropTypes.shape({
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

Conference.defaultProps = {
  conference: {},
};

const mapStateToProps = ({ conference }) => (
  { conference });

export default connect(mapStateToProps)(Conference);
