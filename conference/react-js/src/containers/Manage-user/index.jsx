import React, { PureComponent } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import loadData from '../../actions/load';
import actions from '../../constants/actions-types';
import { rolesUI } from '../../constants/roles';
import { allUsers } from '../../constants/backend-url';

class ManageUser extends PureComponent {
  componentDidMount() {
    const { LOAD_USER_DATA } = actions;
    const { load } = this.props;
    axios.get(allUsers)
      .then(({ data }) => (load(LOAD_USER_DATA, data)));
  }

  ShowListUsers = data => (
    data.map(({ fname, roles: [roles], mail }) => (
      <div className="data-table__row" key={mail}>
        <div className="data-table__column data-table__column_role">
          {[rolesUI[roles]]}
        </div>
        <div className="data-table__column data-table__column_name">
          {fname}
        </div>
        <div className="data-table__column data-table__column_email">
          {mail}
        </div>
      </div>
    ))
  );

  render() {
    const { users } = this.props;
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
              <button className="btn my-talks__button">
             Add New User
              </button>
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
                {this.ShowListUsers(users)}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

ManageUser.propTypes = {
  load: PropTypes.func.isRequired,
  users: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number,
      roles: PropTypes.array,
      mail: PropTypes.string,
      fname: PropTypes.string,
      lname: PropTypes.string,
      bio: PropTypes.string,
      job: PropTypes.string,
      company: PropTypes.string,
      past: PropTypes.string,
      photo: PropTypes.string,
      linkedin: PropTypes.string,
      twitter: PropTypes.string,
      facebook: PropTypes.string,
      blog: PropTypes.string,
      info: PropTypes.string,
    }),
  ).isRequired };

const mapStateToProps = state => ({
  users: state.users,
});

const mapDispatchToProps = dispatch => ({

  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(ManageUser);
