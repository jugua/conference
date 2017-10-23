import React from 'react';
import CardsList from '../../containers/CardsList';
import { past } from '../../constants/backend-url';

const History = () => (
  <div className="tabs-layout">
    <div className="tabs-wrapper">
      <div className="tabs-wrapper_embedded">
        <ul className="tabs-list">
          <li className="tabs-list__item">
            <a className="tabs-list__anchor tabs-list__anchor_active">
              History
            </a>
          </li>
        </ul>
        <CardsList url={past} />
      </div>
    </div>
  </div>
);

export default History;

