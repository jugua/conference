import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
  BrowserRouter as Router,
  Route,
} from 'react-router-dom';

import PropTypes from 'prop-types';
import ForgotPassword from './Forgot-password';
import Tabs from '../components/Tabs';
import Header from '../components/Header';
import '../scss/main.scss';
import SignUp from './SignUpForm';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = new Date();
  }

  render() {
    const { name } = this.props;
    return (
      <Router>
        <div>
          <Header />
        Привет {name}, я App
          <Route path="/" component={Tabs} />
          <Route
            path="/forgot-password"
            exact
            component={ForgotPassword}
          />
          <Route
            path="/sign-up"
            exact
            component={SignUp}
          />
        </div>
      </Router>
    )
    ;
  }
}

function mapStateToProps(state) {
  return {
    name: state.user.name,
  };
}

App.propTypes = { name: PropTypes.string.isRequired };

export default connect(mapStateToProps)(App);
