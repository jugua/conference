import React, { Component } from 'react';
import { connect } from 'react-redux';
import Routers from '../Routers';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { date: new Date() };
  }
  render() {
    const name = this.props.name;
    return (
      <div>
        Привет { name }, я App
        <Routers />
      </div>
    );
  }
}

App.PropTypes = { user: React.PropTypes.object.isRequired };

function mapStateToProps(state) {
  return {
    name: state.user.name,
  };
}

export default connect(mapStateToProps)(App);
