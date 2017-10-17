import React from 'react';
import { Route,
  BrowserRouter as Router } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Upcoming from '../Upcoming';
import AutorizedUserMenu from '../Autorized-user-menu';
import { upcoming, myTalks } from '../../constants/route-url';
import MyTalks from '../../containers/My-talks';

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
