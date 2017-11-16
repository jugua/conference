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
  past,
  conference } from '../../constants/route-url';
import { admin } from '../../constants/roles';
import MyEvents from '../MyEvents/MyEvents';
import Conference from '../../containers/Conference';

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

  return links;
};

const Tabs = ({
  talksCount, conferenceCount, role,
}) => (
  <div className="tabs-layout">
    <div className="tabs-wrapper">
      <NavigationBar>
        {
          getLinks(talksCount, conferenceCount, role)
            .map(({ to, title }) => (
              <NavLink
                exact
                to={to}
                key={to}
                className="tabs-list__anchor"
                activeClassName="tabs-list__anchor_active"
              >
                {title}
              </NavLink>
            ))
        }
      </NavigationBar>
      <Switch>
        <Route
          path={upcoming}
          exact
          component={Upcoming}
        />
        <Route
          path={past}
          exact
          component={Past}
        />
        <Route
          path={myTalks}
          exact
          component={TalksWrapper}
        />
        <Route
          path={myEvents}
          exact
          component={MyEvents}
        />
        <Route
          path={`${conference}/:id`}
          exact
          component={Conference}
        />
        <Route
          path={manageUsers}
          exact
          component={ManageUsers}
        />
        <Route
          path={talks}
          exact
          component={Talks}
        />
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
