import { connect } from 'react-redux';
import TalksList from '../../../components/Talks/TalksList/index';

const equalsOrLeftUndefined = (left, right) =>
  left === undefined || left === '' || left === right;

const matches = (talk, filter) => equalsOrLeftUndefined(filter.status,
  talk.status) && equalsOrLeftUndefined(filter.topic, talk.topic);

const getFilteredTalks = (talks, filter) =>
  talks.filter(talk => matches(talk, filter));

const mapStateToProps = state => ({
  talks: getFilteredTalks(state.talks, state.filter),
});

const DisplayTalks = connect(mapStateToProps)(TalksList);

export default DisplayTalks;
