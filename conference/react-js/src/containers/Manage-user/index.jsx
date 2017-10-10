import React, { PureComponent } from 'react';
import axios from 'axios';

class ManageUser extends PureComponent {
  componentDidMount() {
    axios.get('/get').then(resp => (console.log(resp)));
  }

  render() {
    return (
      <div className="tabs-layout">
        <div className="tabs-wrapper">
          <ul className="tabs-list">
            <li className="tabs-list__item">
              <a
                className="tabs-list__anchor tabs-list__anchor_active"
              >
           Manage Users
              </a>
            </li>
          </ul>
          <div className="tabs-container">
            <div className="my-talks__header">
              <a href="" className="btn my-talks__button">
             Add New User
              </a>
            </div>
            <div className="data-table">
              <div className="table-header">
                <div className="table-header__item table-header__item_role">
               role
                </div>
                <div className="table-header__item table-header__item_name">
               name
                </div>
                <div className="table-header__item table-header__item_email">
               email
                </div>
                <div className="table-header__item table-header__scroll-fix" />
              </div>
              <div className="data-table__inner-wrapper">
                {/* {ShowListUsers(data)} */}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default ManageUser;
