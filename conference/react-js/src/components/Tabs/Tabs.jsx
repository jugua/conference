import React from 'react';
import { Route } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Upcoming from '../Upcoming';
import AutorizedUserMenu from '../Autorized-user-menu';
import TalksWrapper from '../Talks/TalksWrapper/TalksWrapper';
import {
  upcoming,
  myTalks,
  myEvents,
  conference } from '../../constants/route-url';
import MyEvents from '../MyEvents/MyEvents';
import Conference from '../../containers/Conference';

const Tabs = ({ user: { conferenceCount, talksCount } }) => (
  <div className="tabs-layout">
    <div className="tabs-wrapper">
      <AutorizedUserMenu
        conferenceCount={conferenceCount}
        talksCount={talksCount}
      />
      <Route
        path={upcoming}
        exact
        component={Upcoming}
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
    </div>
  </div>
);

Tabs.propTypes = {
  user: PropTypes.shape({}).isRequired,
};

const mapStateToProps = user => user;

export default connect(mapStateToProps)(Tabs);
