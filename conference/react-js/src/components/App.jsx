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
import SettingsPage from './SettingsPage';
import History from './History';
import ManageUser from '../containers/Manage-user/';
import Talks from '../containers/Talks';
import MyInfo from './MyInfo/MyInfo';
import {
  baseUrl,
  forgotPassword,
  signUp,
  manageUser,
  talks,
  settings,
  history,
  myInfo,
} from '../constants/route-url';

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
          path={settings}
          exact
          component={SettingsPage}
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
        <Route
          path={history}
          component={History}
        />
        <Route
          path={myInfo}
          exact
          component={MyInfo}
        />
      </Switch>
    </div>
  </Router>
);

export default App;
