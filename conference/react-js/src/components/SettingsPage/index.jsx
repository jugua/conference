import React, { PureComponent } from 'react';

import NameChangeForm from '../../containers/NameChangeForm';
import NameBrief from '../../containers/NameBrief';
import EmailChangeForm from '../../containers/EmailChangeForm';
import EmailBrief from '../../containers/EmailBrief';
import SlideBlock from '../SlideBlock';
import PasswordChangeForm from '../PasswordChangeForm';

class SettingsPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      current: -1,
    };
  }

  show = (id) => {
    this.setState({ current: id });
  }

  cancel = () => {
    this.setState({ current: -1 });
  }

  render() {
    const { current } = this.state;

    return (
      <div className="settings-wrapper">
        <h2 className="settings__header">Account settings</h2>
        <SlideBlock isOpened={current === 0}>
          <NameBrief show={() => this.show(0)} />
          <NameChangeForm cancel={this.cancel} />
        </SlideBlock>
        <SlideBlock isOpened={current === 1}>
          <EmailBrief show={() => this.show(1)} />
          <EmailChangeForm cancel={this.cancel} />
        </SlideBlock>
        <SlideBlock isOpened={current === 2}>
          <EmailBrief show={() => this.show(2)} />
          <PasswordChangeForm cancel={this.cancel} />
        </SlideBlock>
      </div>
    );
  }
}

export default SettingsPage;
