import React from 'react';
import { PropTypes } from 'prop-types';

const renderTalksHeader = columns => (
  columns.map((col) => {
    switch (col) {
    case 'id':
      return null;
    case 'name':
      return (<span
        key={col}
        className="table-header__item
                  table-header__item_speaker-talk"
        data-name="speaker"
      >
              speaker
      </span>);
    case 'conferenceName':
      return (<span
        key={col}
        className="table-header__item"
        data-name="conferenceName"
      >
              Conference
      </span>);
    default:
      return (<span
        key={col}
        className={`table-header__item table-header__item_${col}-talk`}
        data-name={`${col}`}
      >
        {col}
      </span>);
    }
  })
);
const TalksHeader = ({ columns, sortTalks }) => (
  <div
    role="presentation"
    className="data-table__header-block"
    onClick={sortTalks}
  >
    <div className="table-header">
      <div className="table-header__item
                  table-header__item_check-talk"
      >
        <input type="checkbox" />
      </div>
      {renderTalksHeader(columns)}
    </div>
  </div>
);

TalksHeader.propTypes = {
  columns: PropTypes.arrayOf(PropTypes.string).isRequired,
  sortTalks: PropTypes.func.isRequired };

export default TalksHeader;
