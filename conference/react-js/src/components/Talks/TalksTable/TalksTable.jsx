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
          className="table-header__item"
          tabIndex="0"
        >
            speaker
        </TableHeaderColumn>
      );
    case 'conferenceName':
      return (
        <TableHeaderColumn
          key={col}
          data-name="conferenceName"
          className="table-header__item"
          tabIndex="0"
        >
            conference
        </TableHeaderColumn>
      );
    default:
      return (<TableHeaderColumn
        key={col}
        data-name={`${col}`}
        className="table-header__item"
        tabIndex="0"
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
          <a
            className="link"
            role="button"
            tabIndex="0"
            onClick={console.log(`speaker info ${data.name}`)}
          >
            {data.name}</a>
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

const TalksTable = ({ styleClass, listOfTalks, columns, onClick, sortTalks }) => (
  <div className={styleClass}>
    <Table
      onCellClick={onClick}
    >
      <TableHeader
        displaySelectAll={false}
        adjustForCheckbox={false}
      >
        <TableRow onClick={sortTalks}>
          {renderTalksHeader(columns)}
        </TableRow>
      </TableHeader>
      <TableBody
        showRowHover
        displayRowCheckbox={false}
      >
        {getRows(listOfTalks, columns)}
      </TableBody>
    </Table>
  </div>
);

TalksTable.propTypes = {
  styleClass: PropTypes.string,
  listOfTalks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string).isRequired,
  onClick: PropTypes.func.isRequired,
  sortTalks: PropTypes.func.isRequired,
};

TalksTable.defaultProps = {
  styleClass: 'table-container',
};

export default TalksTable;
