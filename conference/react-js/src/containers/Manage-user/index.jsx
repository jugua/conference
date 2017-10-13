import React, { PureComponent } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import loadData from '../../actions/load-data';
import actions from '../../constants/actions-types';
import { rolesUI } from '../../constants/roles';
import { allUsers } from '../../constants/backend-url';
import AddNewUser from '../Add-new-user';

class ManageUser extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isShowAddNewUserPopUp: false,
      fname: '',
      roles: '',
      mail: '',
    };
  }
  componentDidMount() {
    this.getAllUsers();
  }
  getAllUsers = () => (
    axios.get(allUsers)
      .then(({ data }) => (this.showNewUsers(data)))
  );

  showNewUsers = (data) => {
    const { LOAD_USER_DATA } = actions;
    const { load } = this.props;
    load(LOAD_USER_DATA, data);
  };

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

  sortedList = ({ target: { dataset: { name } } }) => {
    const { ASC } = actions;
    const { users, load } = this.props;
    const value = this.state[name] === '' ? ASC : '';
    console.log(load('sort', { users, direction: value, field: name }));
    load('sort', { users, direction: value, field: name });
    this.setState({ [name]: value });
  };

  toggleAddUserPopUp = () => (
    this.setState({
      isShowAddNewUserPopUp: !this.state.isShowAddNewUserPopUp,
    })
  );

  render() {
    const { isShowAddNewUserPopUp } = this.state;
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
              <button
                onClick={this.toggleAddUserPopUp}
                className="btn my-talks__button"
              >
             Add New User
              </button>
            </div>
            <div className="data-table">
              <div className="table-header">
                <div
                  data-name="roles"
                  role="button"
                  tabIndex={0}
                  onClick={this.sortedList}
                  className="table-header__item table-header__item_role"
                >
               role
                </div>
                <div
                  data-name="fname"
                  role="button"
                  tabIndex={0}
                  onClick={this.sortedList}
                  className="table-header__item table-header__item_name"
                >
               name
                </div>
                <div
                  data-name="mail"
                  role="button"
                  tabIndex={0}
                  onClick={this.sortedList}
                  className="table-header__item table-header__item_email"
                >
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
        {isShowAddNewUserPopUp &&
        <AddNewUser
          toggleAddUserPopUp={this.toggleAddUserPopUp}
          getAllUsers={this.getAllUsers}
        />}
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
