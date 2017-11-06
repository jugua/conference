import React from 'react';
import {
  Switch,
  BrowserRouter as Router,
  Route,
} from 'react-router-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import ForgotPassword from './Forgot-password';
import Tabs from './Tabs';
import Header from '../containers/Header/Header';
import '../scss/main.scss';
import SignUp from '../containers/SignUpForm';
import History from './History';
import ManageUser from '../containers/Manage-user/';
import Talks from '../containers/Talks';
import MyInfo from './MyInfo/MyInfo';
import AccountPage from '../components/AccountPage/AccountPage';
import {
  baseUrl,
  forgotPassword,
  signUp,
  manageUser,
  talks,
  account,
  history,
  myInfo,
} from '../constants/route-url';

const App = () => (
  <MuiThemeProvider>
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
            path={account}
            exact
            component={AccountPage}
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
        </Switch>
      </div>
    </Router>
  </MuiThemeProvider>
);

export default App;
