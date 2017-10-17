import React from 'react';
import { Route } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import Upcoming from '../Upcoming';
import AutorizedUserMenu from '../Autorized-user-menu';
import { upcoming, myTalks } from '../../constants/route-url';

const Tabs = ({ userTalks: { length } }) => (

  <div className="tabs-layout">
    <div className="tabs-wrapper">
      <div className="tabs-wrapper_embedded">
        <AutorizedUserMenu length={length} />
        <Route
          path={upcoming}
          component={Upcoming}
        />
        <Route
          path={myTalks}
          component={Upcoming}
        />
      </div>
    </div>
  </div>

);

Tabs.propTypes = {
  userTalks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
};

const mapStateToProps = state => ({
  userTalks: state.userTalks,
});

export default connect(mapStateToProps)(Tabs);
