import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import TalksList from '../../../components/Talks/TalksList';

class DisplayTalks extends Component {
  setTalks = (data, columns) => data
    .map(element => (
      <TalksList data={element} key={element.id} columns={columns} />
    ),
    );

  render() {
    const { talk, columns } = this.props;
    return (
      <tbody className="data-table__inner-wrapper">
        {this.setTalks(talk, columns)}
      </tbody>
    );
  }
}

DisplayTalks.propTypes = {
  talk: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default DisplayTalks;
