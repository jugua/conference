import React from 'react';
import { PropTypes } from 'prop-types';

const TalksList = ({ data, columns }) =>
  (<div className="data-table__row">
    <div className="data-table__column data-table__column_check-talk">
      <input type="checkbox" />
    </div>
    {columns.map((col) => {
      switch (col) {
      case 'id':
        return null;
      case 'name':
        return (<div
          key={col}
          className="data-table__column
        data-table__column_speaker-talk"
        >
          <a className="link">{data.name}</a></div>);
      case 'title':
        return (<div
          key={col}
          className="data-table__column
        data-table__column_title-talk"
        >
          <a className="link">{data.title}</a>
        </div>);

      default:
        return (<div
          key={col}
          className={`${'data-table__column' +
            ' data-table__column_'}${col}-talk`}
        >
          {data[col]}
        </div>);
      }
    })}
  </div>);

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
columns: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default TalksList;
