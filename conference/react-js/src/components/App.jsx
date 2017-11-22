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
import Conference from '../containers/Conference/Conference';
import AccountPage from '../components/AccountPage/AccountPage';
import {
  root,
  forgotPassword,
  signUp,
  account,
  conference,
} from '../constants/route-url';

const getRoutes = () => {
  const routes = [
    { path: forgotPassword, component: ForgotPassword },
    { path: signUp, component: SignUp },
    { path: account, component: AccountPage },
    { path: `${conference}/:id`, component: Conference },
    { path: root, component: Tabs, exact: false },
  ];

  return routes.map(({ path, component, exact = true }) => (
    <Route key={path} path={path} component={component} exact={exact} />
  ));
};

const App = () => (
  <MuiThemeProvider>
    <Router>
      <div>
        <Header />
        <Switch>
          { getRoutes() }
        </Switch>
      </div>
    </Router>
  </MuiThemeProvider>
);

export default App;
