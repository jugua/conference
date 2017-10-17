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
    <tr className="data-table__row">
      <td className="data-table__column data-table__column_check-talk">
        <input type="checkbox" />
      </td>
      <td className="data-table__column data-table__column_speaker-talk">
        <a className="link">{name}</a></td>
      <td className="data-table__column data-table__column_title-talk">
        <a className="link">{title}</a>
      </td>
      <td className="data-table__column data-table__column_topic-talk">
        {topic}
      </td>
      <td className="data-table__column data-table__column_date-talk">
        {date}
      </td>
      <td className="data-table__column data-table__column_status-talk">
        {status}
      </td>
      <td className="data-table__column data-table__column_comments-talk">
        {comment}
      </td>
      <td className="data-table__column data-table__column_assign-talk">
        {assignee}
      </td>
    </tr>
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

export default TalksList;
