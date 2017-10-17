import React from 'react';
import {
  Switch,
  BrowserRouter as Router,
  Route,
} from 'react-router-dom';
import ForgotPassword from './Forgot-password';
import Tabs from './Tabs';
import Header from './Header';
import '../scss/main.scss';
import SignUp from '../containers/SignUpForm';
import {
  baseUrl,
  forgotPassword,
  signUp,
  manageUser,
  talks } from '../constants/route-url';
import ManageUser from '../containers/Manage-user/';
import Talks from '../containers/Talks';

const App = () => (

  <Router>
    <div>
      <Header />
      <Switch>
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
        <Route
          path={manageUser}
          component={ManageUser}
        />
        <Route
          path={talks}
          component={Talks}
        />
      </Switch>
    </div>
  </Router>
);

export default App;
