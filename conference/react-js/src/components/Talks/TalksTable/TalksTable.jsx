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
        >
            Speaker
        </TableHeaderColumn>
      );
    case 'conferenceName':
      return (
        <TableHeaderColumn
          key={col}
          data-name="conferenceName"
          className="table-header__item"
        >
            Event
        </TableHeaderColumn>
      );
    case 'eventDate':
      return (
        <TableHeaderColumn
          key={col}
          data-name="eventDate"
          className="table-header__item"
        >
            Event Date
        </TableHeaderColumn>
      );
    case 'cfpDate':
      return (
        <TableHeaderColumn
          key={col}
          data-name="cfpDate"
          className="table-header__item"
        >
            Call for Paper
        </TableHeaderColumn>
      );
    case 'notificationDue':
      return (
        <TableHeaderColumn
          key={col}
          data-name="notificationDue"
          className="table-header__item"
        >
            Notification Due
        </TableHeaderColumn>
      );
    default:
      return (<TableHeaderColumn
        key={col}
        data-name={`${col}`}
        className="table-header__item"
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
    case 'eventDate':
      return (
        <TableRowColumn key={col}>
          {data.startDate} - {data.endDate}
        </TableRowColumn>
      );
    case 'cfpDate':
      return (
        <TableRowColumn key={col}>
          {data.cfpStartDate} - {data.cfpEndDate}
        </TableRowColumn>
      );
    case 'notificationDue':
      return (
        <TableRowColumn key={col}>
          {data.notificationDue}
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
