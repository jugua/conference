import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  NavLink,
} from 'react-router-dom';
import Upcoming from '../Upcoming';
import Past from '../Past';
import { past, upcoming } from '../../constants/route-url';

const Tabs = () => (
  <Router>
    <div className="tabs-layout">
      <div className="tabs-wrapper">
        <div className="tabs-wrapper_embedded">
          <ul className="tabs-list">
            <li className="tabs-list__item">
              <NavLink
                exact
                className="tabs-list__anchor"
                to={upcoming}
                activeClassName="tabs-list__anchor_active"
              >
                  Upcoming
              </NavLink>
            </li>
            <li className="tabs-list__item">
              <NavLink
                className="tabs-list__anchor"
                to={past}
                activeClassName="tabs-list__anchor_active"
              >
                  Past
              </NavLink>
            </li>
          </ul>
          <Route
            exact
            path={upcoming}
            component={Upcoming}

          />
          <Route
            path={past}
            component={Past}
          />
        </div>
      </div>
    </div>
  </Router>
);

export default Tabs;
