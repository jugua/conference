import React, { PureComponent } from 'react';
import classNames from 'classnames'; import axios from 'axios';
import { myInfo } from '../../constants/backend-url';

import SettingsPage from '../Settings/SettingsPage/SettingsPage';
import MyInfo from '../MyInfo/MyInfo';
import MyContactsContainer from '../MyContactsContainer/MyContactsContainer';

const tabsList = [
  {
    id: 1,
    name: 'My Info',
    component: MyInfo,
  },
  {
    id: 2,
    name: 'Contacts',
    component: MyContactsContainer,
  },
  {
    id: 3,
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
    this.updateMyInfo();
  }

  updateMyInfo = () => {
    axios.get(myInfo)
      .then(({ data }) => {
        this.setState({
          user: data,
        });
      });
  }

  changeTab = ({ target: { dataset: { index } } }) => {
    this.setState({
      currentTabIndex: +index,
    });
  }

  render() {
    const { currentTabIndex } = this.state;
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
            user={this.state.user}
            updateInfo={this.updateMyInfo}
          />
        </div>
      </div>
    );
  }
}

export default AccountPage;
