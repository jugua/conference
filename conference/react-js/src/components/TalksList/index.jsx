import React from 'react';
import { PropTypes } from 'prop-types';

function TalksList({ data: {
  date,
  status,
  title,
  topic,
  assignee,
  name,
  comment,
} }) {
  return (
    <div className="data-table__row">
      <div className="data-table__column data-table__column_check-talk">
        <input type="checkbox" />
      </div>
      <div className="data-table__column data-table__column_speaker-talk">
        <a className="link">{name}</a></div>
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
        {comment}
      </div>
      <div className="data-table__column data-table__column_assign-talk">
        {assignee}
      </div>
    </div>
  );
}

TalksList.propTypes = { data: PropTypes.shape({
  date: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired,
  lang: PropTypes.string.isRequired,
  level: PropTypes.string.isRequired,
  status: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  topic: PropTypes.string.isRequired,
  type: PropTypes.string.isRequired,
  assignee: PropTypes.string,
  comment: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
}).isRequired,
};

TalksList.defaultProps = {
  talks: {},
};

export default TalksList;
