import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import TalksList from '../../../components/Talks/TalksList';

class DisplayTalks extends Component {
  setTalks = data => data
    .map(element => (
      <TalksList data={element} key={element.id} />),
    );

  render() {
    const { talk } = this.props;
    return (
      <tbody className="data-table__inner-wrapper">
        {this.setTalks(talk)}
      </tbody>
    );
  }
}

DisplayTalks.propTypes = {
  talk: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
};

export default DisplayTalks;
