import React, { PureComponent } from 'react';

import SettingsContainer from '../../containers/SettingsContainer';

class SettingsPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      message: null,
    };
  }

  setMessage = msg => this.setMessage({ message: msg, error: null });

  setError = err => this.setState({ error: err, message: null });

  reset = () => this.setState({ error: null, message: null });

  render() {
    const { error, message } = this.state;

    return (
      <div className="settings-wrapper">
        <h2 className="settings__header">Account settings</h2>
        { error &&
          <div className="settings__info settings__error">
            {error}
          </div> }
        { message &&
          <div className="settings__info settings__success">
            {message}
          </div> }
        <SettingsContainer
          setMessage={this.setMessage}
          setError={this.setError}
          reset={this.reset}
        />
      </div>
    );
  }
}

export default SettingsPage;
