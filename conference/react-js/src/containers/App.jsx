import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import ForgotPassword from './Forgot-password';
import Tabs from '../components/Tabs';
import SignInForm from '../containers/SignInForm/index';
import '../scss/main.scss';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = new Date();
  }
  render() {
    const { name } = this.props;
    return (
      <div>
        <SignInForm />
        Привет {name}, я App
        <Tabs />
        <ForgotPassword />
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
