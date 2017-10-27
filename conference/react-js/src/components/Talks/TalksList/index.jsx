import React from 'react';
import {PropTypes} from 'prop-types';

const renderTalksList = (data, columns) => (
  columns.map((col) => {
    switch (col) {
      case 'id':
        return null;
      case 'name':
        return (<td
          key={col}
          className="data-table__column
        data-table__column_speaker-talk"
        >
          <a className="link">{data.name}</a></td>);
      case 'title':
        return (<td
          key={col}
          className="data-table__column
        data-table__column_title-talk"
        >
          <a className="link">{data.title}</a>
        </td>);
      case 'conferenceName':
        return (<td
          key={col}
          className="data-table__column
        data-table__column_conference"
        >{data.conferenceName}
        </td>);
      default:
        return (<td
          key={col}
          className={`${'data-table__column' +
          ' data-table__column_'}${col}-talk`}
        >
          {data[col]}
        </td>);
    }
  })
);

const TalksList = ({data, columns}) =>
  (<tr className="data-table__row">
    <td className="data-table__column data-table__column_check-talk">
      <input type="checkbox"/>
    </td>
    {renderTalksList(data, columns)}
  </tr>);

TalksList.propTypes = {
  data: PropTypes.shape({
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