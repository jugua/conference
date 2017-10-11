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
import SettingsPage from '../containers/SettingsPage';
import { baseUrl, forgotPassword, signUp } from '../constants/route-url';

const App = () => (
  <Router>
    <div>
      <Header />
      <SettingsPage />
      <Route
        path={baseUrl}
        exact
        component={Tabs}
      />
      <Route
        path={forgotPassword}
        exact
        component={ForgotPassword}
      />
      <Route
        path={signUp}
        component={SignUp}
      />
    </div>
  </Router>
);

export default App;
