import React from 'react';
import { PropTypes } from 'prop-types';

const renderTalksHeader = columns => (
  columns.map((col) => {
    switch (col) {
    case 'id':
      return null;
    case 'name':
      return (<th
        key={col}
        className="table-header__item
                  table-header__item_speaker-talk"
        data-name="speaker"
      >
              speaker
      </th>);
    case 'conferenceName':
      return (<th
        key={col}
        className="table-header__item table-header__item_conference"
        data-name="conferenceName"
      >
              Conference
      </th>);
    default:
      return (<th
        key={col}
        className={`table-header__item table-header__item_${col}-talk`}
        data-name={`${col}`}
      >
        {col}
      </th>);
    }
  })
);
const TalksHeader = ({ columns, sortTalks }) => (
  <thead
    role="presentation"
    className="data-table__header-block"
    onClick={sortTalks}
  >
    <tr className="table-header">
      <th className="table-header__item
                  table-header__item_check-talk"
      >
        <input type="checkbox" />
      </th>
      {renderTalksHeader(columns)}
      <th className="table-header__item table-header__scroll-fix" />
    </tr>
  </thead>
);

TalksHeader.propTypes = {
  columns: PropTypes.arrayOf(PropTypes.string).isRequired,
  sortTalks: PropTypes.func.isRequired,
};

export default TalksHeader;
