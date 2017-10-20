import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import TalksList from '../../../components/Talks/TalksList';

class DisplayTalks extends Component {
  setTalks = (data, coloms) => data
    .map(element => (
      <TalksList data={element} key={element.id} coloms={coloms} />),
    );

  render() {
    const { talk, coloms } = this.props;
    return (
      <div className="data-table__inner-wrapper">
        {this.setTalks(talk, coloms)}
      </div>
    );
  }
}

DisplayTalks.propTypes = {
  talk: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  coloms: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default DisplayTalks;
