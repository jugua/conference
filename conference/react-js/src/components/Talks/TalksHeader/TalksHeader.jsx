import React from 'react';
import { PropTypes } from 'prop-types';

import {
  TableRow,
  TableHeaderColumn,
} from 'material-ui/Table';

const renderTalksHeader = columns => (
  columns.map((col) => {
    switch (col) {
    case 'id':
      return null;
    case 'name':
      return (
        <TableHeaderColumn
          key={col}
          data-name="speaker"
        >
          speaker
        </TableHeaderColumn>
      );
    case 'conferenceName':
      return (
        <TableHeaderColumn
          key={col}
          data-name="conferenceName"
        >
          conference
        </TableHeaderColumn>
      );
    default:
      return (<TableHeaderColumn
        key={col}
        data-name={`${col}`}
      >
        {col}
      </TableHeaderColumn>);
    }
  })
);

const TalksHeader = ({ columns }) => (
  <TableRow>
    {renderTalksHeader(columns)}
  </TableRow>
);

TalksHeader.propTypes = {
  columns: PropTypes.arrayOf(PropTypes.string).isRequired,
  // sortTalks: PropTypes.func.isRequired,
};

export default TalksHeader;
