import React from 'react';
import { Switch, BrowserRouter, Route } from 'react-router-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

import ForgotPassword from './Forgot-password';
import Tabs from './Tabs/Tabs';
import Header from '../containers/Header/Header';
import SignUp from '../containers/SignUpForm/SignUpForm';
import Conference from '../containers/Conference/Conference';
import AccountPage from '../components/AccountPage/AccountPage';
import * as routeUrl from '../constants/route-url';

import '../scss/main.scss';

const getRoutes = () => {
  const { root, signUp, account, conference } = routeUrl;
  const routes = [
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
  <MuiThemeProvider >
    <BrowserRouter>
      <div>
        <Header />
        <ForgotPassword />
        <Switch>
          { getRoutes() }
        </Switch>
      </div>
    </BrowserRouter>
  </MuiThemeProvider>
);

export default App;
