import React, { PureComponent } from 'react';
import SlideBlock from '../../components/SlideBlock';

class SettingsPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      current: -1,
      email: 'azaza@gmail.com',
      'first-name': 'Admin',
      'last-name': 'Adminov',
    };
  }

  showCurrent = (index) => {
    this.setState({ current: index });
  };

  cancelAction = () => {
    this.setState({ current: -1 });
  };

  saveEmail = () => {

  };

  saveName = () => {

  };

  savePassword = () => {

  };

  render() {
    const blocks = [
      {
        header: 'Email',
        brief: this.state.email,
        action: this.saveEmail,
        inputs: [
          {
            name: 'old-email',
            id: 'old-email',
            label: 'Old email',
            type: 'text',
            value: this.state.email,
            readonly: true,
          },
          {
            name: 'new-email',
            id: 'new-email',
            label: 'New email',
            type: 'text',
            value: '',
          },
        ],
      },
      {
        header: 'Name',
        brief: `${this.state['first-name']} ${this.state['last-name']}`,
        action: this.saveName,
        inputs: [
          {
            name: 'first-name',
            id: 'first-name',
            label: 'First name',
            type: 'text',
            value: this.state['first-name'],
          },
          {
            name: 'last-name',
            id: 'last-name',
            label: 'Last name',
            type: 'text',
            value: this.state['last-name'],
          },
        ],
      },
    ];

    return (
      <div className="settings-wrapper">
        <div className="settings__block">
          {
            blocks.map(({
              brief,
              header,
              action,
              inputs,
              readonly,
            }, index) => (
              <SlideBlock
                index={index}
                brief={brief}
                readonly={readonly || false}
                key={header}
                header={header}
                saveAction={action}
                cancelAction={this.cancelAction}
                inputs={inputs}
                isOpened={index === this.state.current}
                show={this.showCurrent}
              />))
          }
        </div>
      </div>
    );
  }
}

export default SettingsPage;
