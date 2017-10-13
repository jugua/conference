import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import TalksList from '../../../components/TalksList';

class DisplayTalks extends Component {
  setTalks = data => Object.values(data)
    .map(element => (
      <TalksList data={element} key={element.id} />),
    );

  render() {
    const { talk } = this.props;
    return (
      <div className="data-table__inner-wrapper">
        {this.setTalks(talk)}
      </div>
    );
  }
}

DisplayTalks.propTypes = {
  talk: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
};

export default DisplayTalks;
