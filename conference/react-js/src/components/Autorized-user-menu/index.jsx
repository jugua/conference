import React from 'react';
import {
  NavLink,
} from 'react-router-dom';
import PropTypes from 'prop-types';

import { upcoming, myTalks, myEvents } from '../../constants/route-url';

const AutorizedUserMenu = ({ conferenceCount, talksCount }) => (
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
    {talksCount > 0 &&
      (<li className="tabs-list__item">
        <NavLink
          exact
          className="tabs-list__anchor"
          to={myTalks}
          activeClassName="tabs-list__anchor_active"
        >
        My talks
        </NavLink>
      </li>
      )
    }
    {conferenceCount > 0 &&
    (<li className="tabs-list__item">
      <NavLink
        exact
        className="tabs-list__anchor"
        to={myEvents}
        activeClassName="tabs-list__anchor_active"
      >
          My Events
      </NavLink>
    </li>
    )
    }
  </ul>
);

AutorizedUserMenu.propTypes = {
  talksCount: PropTypes.number,
  conferenceCount: PropTypes.number,
};

AutorizedUserMenu.defaultProps = {
  talksCount: 0,
  conferenceCount: 0,
};

export default AutorizedUserMenu;
