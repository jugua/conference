import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  NavLink,
} from 'react-router-dom';
import Upcoming from '../../containers/Conference-cards/Upcoming';
import Past from '../../containers/Conference-cards/Past';

function Tabs() {
  return (
    <Router>
      <div className="tabs-layout">
        <div className="tabs-wrapper">
          <div className="tabs-wrapper_embedded">
            <ul className="tabs-list">
              <li className="tabs-list__item">
                <NavLink
                  className="tabs-list__anchor"
                  to="/"
                  exact
                  activeClassName="tabs-list__anchor_active"
                >
                  Upcoming
                </NavLink>
              </li>
              <li className="tabs-list__item">
                <NavLink
                  className="tabs-list__anchor"
                  to="/past"
                  exact
                  activeClassName="tabs-list__anchor_active"
                >
                  Past
                </NavLink>
              </li>
            </ul>
            <Route
              exact
              path="/"
              component={Upcoming}

            />
            <Route
              path="/past"
              component={Past}
            />
          </div>
        </div>
      </div>
    </Router>
  );
}

export default Tabs;
