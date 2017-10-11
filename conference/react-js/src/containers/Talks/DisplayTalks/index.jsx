import { connect } from 'react-redux';
import React, { Component } from 'react';
import axios from 'axios';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';
import load from '../../../actions/load';
import TalksList from '../../../components/Talks/TalksList';
import { talk } from '../../../constants/backend-url';
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
  constructor(props) {
    super(props);
    console.log(this.props);
    //  const { data } = this.state;
    this.state = { data: '' };
  }

  componentDidMount() {
    axios.get(talk)
      .then(({ data }) => {
        this.props.load('load', data);
        // this.props.load('load', {filter, data})
        this.setState({ data });
      })
      .catch(({ response: { data } }) => (
        console.log(data)
      ));
  }

  setTalks = data => Object.values(data)
    .map(element => (
      /* eslint-disable */
      <TalksList data={element} key={element._id}/>)
      /* eslint-enable */
    );

  render() {
    const { data } = this.props;
    return (
      <div className="data-table__inner-wrapper">
        {this.setTalks(data)}
      </div>
    );
  }
}

DisplayTalks.propTypes = { load: PropTypes.func.isRequired,
  data: PropTypes.arrayOf(PropTypes.shape({
    _id: PropTypes.string,
    date: PropTypes.string,
    description: PropTypes.string,
    lang: PropTypes.string,
    level: PropTypes.string,
    status: PropTypes.string,
    title: PropTypes.string,
    topic: PropTypes.string,
    type: PropTypes.string,
  })).isRequired };

const mapStateToProps = state => ({
  data: state.talks,
});

const mapDispatchToProps = dispatch => ({
  load: bindActionCreators(
    load, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(DisplayTalks);
