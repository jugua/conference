import React from 'react';
import {
  Switch,
  BrowserRouter as Router,
  Route,
} from 'react-router-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import ForgotPassword from './Forgot-password';
import Tabs from './Tabs/Tabs';
import Header from '../containers/Header/Header';
import '../scss/main.scss';
import SignUp from '../containers/SignUpForm/SignUpForm';
import Past from './Past/Past';
import ManageUser from '../containers/ManageUsers/ManageUsers';
import Talks from '../containers/Talks';
import AccountPage from '../components/AccountPage/AccountPage';
import {
  root,
  forgotPassword,
  signUp,
  manageUser,
  talks,
  account,
  past,
} from '../constants/route-url';

const App = () => (
  <MuiThemeProvider>
    <Router>
      <div>
        <Header />
        <Switch>
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
            exact
            component={SignUp}
          />
          <Route
            path={manageUser}
            exact
            component={ManageUser}
          />
          <Route
            path={talks}
            exact
            component={Talks}
          />
          <Route
            path={past}
            exact
            component={Past}
          />
          <Route
            path={root}
            component={Tabs}
          />
        </Switch>
      </div>
    </Router>
  </MuiThemeProvider>
);

export default App;
