import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  Link,
} from 'react-router-dom';
import Upcoming from '../Upcoming';
import Past from '../Past';

const Routers = () => (
  <Router>
    <div className="tabs-layout">
      <div className="tabs-wrapper">
        <div className="tabs-wrapper_embedded">
          <ul className="tabs-list">
            <li className="tabs-list__item">
              <Link
                className="tabs-list__anchor tabs-list__anchor_active"
                to="/"
              >
                Upcoming
              </Link>
            </li>
            <li className="tabs-list__item">
              <Link
                className="tabs-list__anchor"
                to="/past"
              >
                Past
              </Link>
            </li>
          </ul>
          <div className="tabs-container">
            <Route
              exact
              path="/"
              component={Upcoming}
            />
            <Route path="/past" component={Past} />
          </div>
        </div>
      </div>
    </div>
  </Router>
);

export default Routers;
