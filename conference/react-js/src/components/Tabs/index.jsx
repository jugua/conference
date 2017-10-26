import React from 'react';
import { Route,
  BrowserRouter as Router } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Upcoming from '../Upcoming';
import AutorizedUserMenu from '../Autorized-user-menu';
import { upcoming, myTalks, myEvents } from '../../constants/route-url';
import MyTalks from '../My-talks';
import MyEvents from '../MyEvents/MyEvents';
import UpdateTalk from '../UpdateTalk/UpdateTalk';

const Tabs = ({ userTalks: { length } }) => (
  <Router>
    <div className="tabs-layout">
      <div className="tabs-wrapper">
        <AutorizedUserMenu length={length} />
        <Route
          path={upcoming}
          exact
          component={Upcoming}
        />
        <Route
          path={myTalks}
          exact
          component={MyTalks}
        />
        <Route
          path={`${myTalks}/*`}
          exact
          component={UpdateTalk}
        />
        <Route
          path={myEvents}
          exact
          component={MyEvents}
        />
      </div>
    </div>
  </Router>
);

Tabs.propTypes = {
  userTalks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
};

const mapStateToProps = state => ({
  userTalks: state.userTalks,
});

export default connect(mapStateToProps)(Tabs);
