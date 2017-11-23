import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classNames from 'classnames';
// import axios from 'axios';
import PropTypes from 'prop-types';

// import { myInfo } from '../../constants/backend-url';
import SettingsPage from '../Settings/SettingsPage/SettingsPage';
// import MyInfo from '../MyInfo/MyInfo';
import getUserInfo from '../../actions/getUserInfo';
import changeUserInfo from '../../actions/changeUserInfo';

const tabsList = [
  // {
  //   id: 1,
  //   name: 'My Info',
  //   component: MyInfo,
  // },
  {
    id: 2,
    name: 'Settings',
    component: SettingsPage,
  },
  // new tabs...
];

const getTabById = (tabs, id) => (
  tabs.find(tab => tab.id === id)
);

class AccountPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      currentTabIndex: tabsList[0].id,
      user: {},
    };
  }

  componentDidMount() {
    this.props.getUser();
  }

  // updateMyInfo = () => {
  //   this.props.getUserInfo();
  // }

  changeTab = ({ target: { dataset: { index } } }) => {
    this.setState({
      currentTabIndex: +index,
    });
  }

  render() {
    const { currentTabIndex } = this.state;
    const { editUser, user } = this.props;
    const CurrentComponent = getTabById(tabsList, currentTabIndex).component;

    return (
      <div className="tabs-layout tabs-wrapper">
        <ul className="tabs-list">
          {
            tabsList.map(({ id, name }) => (
              <a
                className={classNames({
                  'tabs-list__item': true,
                  'tabs-list__anchor': true,
                  'tabs-list__anchor_active': id === currentTabIndex,
                })}
                data-index={id}
                role="button"
                tabIndex={0}
                onClick={this.changeTab}
                key={id}
              >
                {name}
              </a>
            ))
          }
        </ul>
        <div className="tabs-container">
          <CurrentComponent
            user={user}
            editUser={editUser}
          />
        </div>
      </div>
    );
  }
}

AccountPage.propTypes = {
  getUser: PropTypes.func.isRequired,
  editUser: PropTypes.func.isRequired,
  user: PropTypes.shape({}).isRequired,
};

const mapStateToProps = ({ user }) => ({ user });
const mapDispatchToProps = dispatch => ({
  getUser: () => dispatch(getUserInfo),
  editUser: updatedUser => dispatch(changeUserInfo(updatedUser)),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(AccountPage);
