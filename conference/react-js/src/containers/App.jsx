import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Tabs from '../components/Tabs';
import '../scss/main.scss';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { date: new Date() };
  }
  render() {
    const name = this.props.name;
    return (
      <div>
        Привет {name}, я App
        <Tabs />
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    name: state.user.name,
  };
}

App.propTypes = { name: PropTypes.string.isRequired };

export default connect(mapStateToProps)(App);
