import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import TalksList from '../../../components/Talks/TalksList';
// import TalksList from '../../../components/Talks/TalksList/index';
//
// const equalsOrLeftUndefined = (left, right) =>
//   left === undefined || left === '' || left === right;
//
// const matches = (talk, filter) => equalsOrLeftUndefined(filter.status,
//   talk.status) && equalsOrLeftUndefined(filter.topic, talk.topic);
//
// const getFilteredTalks = (talks, filter) =>
//   talks.filter(talk => matches(talk, filter));
//
// const mapStateToProps = state => ({
//   talks: getFilteredTalks(state.talks, state.filter),
// });
//
// const DisplayTalks = connect(mapStateToProps)(TalksList);

class DisplayTalks extends Component {
  setTalks = data => Object.values(data)
    .map(element => (
      /* eslint-disable */
      <TalksList data={element} key={element._id}/>)
      /* eslint-enable */
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
