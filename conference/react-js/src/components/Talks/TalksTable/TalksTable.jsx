import React from 'react';
import { PropTypes } from 'prop-types';

import {
  Table,
  TableHeader,
  TableHeaderColumn,
  TableBody,
  TableRow,
  TableRowColumn,
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

const renderTalksList = (data, columns) => (
  columns.map((col) => {
    switch (col) {
    case 'id':
      return null;
    case 'name':
      return (
        <TableRowColumn key={col}>
          <a className="link">{data.name}</a>
        </TableRowColumn>
      );
    case 'title':
      return (
        <TableRowColumn key={col}>
          <a
            className="link"
            data-talk-id={data.id}
          >{data.title}</a>
        </TableRowColumn>
      );
    default:
      return (
        <TableRowColumn key={col}>
          {data[col]}
        </TableRowColumn>
      );
    }
  })
);

const getRows = (talks, columns) => (
  talks.map(element => (
    <TableRow key={element.id}>
      {renderTalksList(element, columns)}
    </TableRow>),
  )
);

const TalksTable = ({ listOfTalks, columns, onClick, sortTalks }) => (
  <div className="table-container">
    <Table
      selectable
      multiSelectable
      onCellClick={onClick}
    >
      <TableHeader
        displaySelectAll
        adjustForCheckbox
        enableSelectAll
      >
        <TableRow onClick={sortTalks}>
          {renderTalksHeader(columns)}
        </TableRow>
      </TableHeader>
      <TableBody
        showRowHover
      >
        {getRows(listOfTalks, columns)}
      </TableBody>
    </Table>
  </div>
);

TalksTable.propTypes = {
  listOfTalks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string).isRequired,
  onClick: PropTypes.func.isRequired,
  sortTalks: PropTypes.func.isRequired,
};

export default TalksTable;
