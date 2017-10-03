import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  NavLink,
} from 'react-router-dom';
import PropTypes from 'prop-types';
import Upcoming from '../Upcoming';
import Past from '../Past';

function Tabs({ match: { path } }) {
  return (
    <Router>
      <div className="tabs-layout">
        <div className="tabs-wrapper">
          <div className="tabs-wrapper_embedded">
            <ul className="tabs-list">
              <li className="tabs-list__item">
                <NavLink
                  exact
                  className="tabs-list__anchor"
                  to={`${path}`}
                  activeClassName="tabs-list__anchor_active"
                >
                  Upcoming
                </NavLink>
              </li>
              <li className="tabs-list__item">
                <NavLink
                  className="tabs-list__anchor"
                  to={`${path}past`}
                  activeClassName="tabs-list__anchor_active"
                >
                  Past
                </NavLink>
              </li>
            </ul>
            <Route
              exact
              path={`${path}`}
              component={Upcoming}

            />
            <Route
              path={`${path}past`}
              component={Past}
            />
          </div>
        </div>
      </div>
    </Router>
  );
}

Tabs.propTypes = { match: PropTypes.shape({
  isExact: PropTypes.boolean,
  path: PropTypes.string,
  url: PropTypes.string,
  params: PropTypes.object,
}).isRequired };

export default Tabs;
