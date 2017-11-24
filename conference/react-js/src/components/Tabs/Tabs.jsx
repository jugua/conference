import React from 'react';
import { Route, Switch, NavLink } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import Upcoming from '../Upcoming';
import NavigationBar from '../NavigationBar/NavigationBar';
import Past from '../Past/Past';
import ManageUsers from '../../containers/ManageUsers/ManageUsers';
import Talks from '../../containers/Talks';
import TalksWrapper from '../Talks/TalksWrapper/TalksWrapper';
import {
  upcoming,
  myTalks,
  myEvents,
  manageUsers,
  talks,
  past } from '../../constants/route-url';
import { admin } from '../../constants/roles';
import MyEvents from '../MyEvents/MyEvents';

const getLinks = (talksCount, conferenceCount, role) => {
  const links = [];

  links.push({ to: upcoming, title: 'Upcoming' });
  links.push({ to: past, title: 'Past' });

  if (talksCount) links.push({ to: myTalks, title: 'My talks' });
  if (conferenceCount) links.push({ to: myEvents, title: 'MyEvents' });
  if (role === admin) {
    links.push({ to: talks, title: 'Talks' });
    links.push({ to: manageUsers, title: 'Manage users' });
  }

  return links.map(({ to, title }) => (
    <NavLink
      exact
      to={to}
      key={to}
      className="tabs-list__anchor"
      activeClassName="tabs-list__anchor_active"
    >
      {title}
    </NavLink>
  ));
};

const getRoutes = () => {
  const routes = [
    { path: upcoming, component: Upcoming },
    { path: past, component: Past },
    { path: myTalks, component: TalksWrapper },
    { path: myEvents, component: MyEvents },
    { path: manageUsers, component: ManageUsers },
    { path: talks, component: Talks },
  ];

  return routes.map(({ path, component }) => (
    <Route key={path} path={path} component={component} exact />
  ));
};

const Tabs = ({
  talksCount, conferenceCount, role,
}) => (
  <div className="tabs-layout">
    <div className="tabs-wrapper">
      <NavigationBar>
        { getLinks(talksCount, conferenceCount, role) }
      </NavigationBar>
      <Switch>
        { getRoutes() }
      </Switch>
    </div>
  </div>
);

Tabs.propTypes = {
  talksCount: PropTypes.number,
  conferenceCount: PropTypes.number,
  role: PropTypes.string,
};

Tabs.defaultProps = {
  talksCount: 0,
  conferenceCount: 0,
  role: '',
};

const mapStateToProps = ({
  user: {
    talksCount,
    conferenceCount,
    role,
  },
}) => ({
  talksCount,
  conferenceCount,
  role,
});

export default connect(mapStateToProps)(Tabs);
