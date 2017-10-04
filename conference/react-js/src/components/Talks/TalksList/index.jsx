import React from 'react';
import { PropTypes } from 'prop-types';

const TalksList = ({ talks }) => (
  <div className="data-table__inner-wrapper">
    {
      talks.map(({ _id, date, status, title, topic }) =>
        (<div className="data-table__row" key={_id}>
          <div className="data-table__column data-table__column_check-talk">
            <input type="checkbox" />
          </div>
          <div className="data-table__column data-table__column_speaker-talk">
            <a className="link">Speaker</a></div>
          <div className="data-table__column data-table__column_title-talk">
            <a className="link">{title}</a>
          </div>
          <div className="data-table__column data-table__column_topic-talk">
            {topic}
          </div>
          <div className="data-table__column data-table__column_date-talk">
            {date}
          </div>
          <div className="data-table__column data-table__column_status-talk">
            {status}
          </div>
          <div className="data-table__column data-table__column_comments-talk">
            comment
          </div>
          <div className="data-table__column data-table__column_assign-talk">
            assignee
          </div>
        </div>),
      )
    }
  </div>
);

TalksList.propTypes = {
  talks: PropTypes.arrayOf(PropTypes.shape({
    _id: PropTypes.string.isRequired,
    date: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    lang: PropTypes.string.isRequired,
    level: PropTypes.string.isRequired,
    status: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    topic: PropTypes.string.isRequired,
    type: PropTypes.string.isRequired,

  }).isRequired).isRequired,
};

export default TalksList;
