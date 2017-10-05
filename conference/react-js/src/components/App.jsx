import React from 'react';
import {
  BrowserRouter as Router,
  Route,
} from 'react-router-dom';
import ForgotPassword from './Forgot-password';
import Tabs from './Tabs';
import Header from './Header';
import '../scss/main.scss';
import SignUp from '../containers/SignUpForm';

const App = () => (
  <Router>
    <div>
      <Header />
      <Route
        path="/"
        exact
        component={Tabs}
      />
      <Route
        path="/forgot-password"
        exact
        component={ForgotPassword}
      />
      <Route
        path="/sign-up"

        component={SignUp}
      />
    </div>
  </Router>
);

export default App;
