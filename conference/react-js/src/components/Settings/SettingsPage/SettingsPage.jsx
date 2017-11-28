import React, { PureComponent } from 'react';
import classNames from 'classnames';

import SettingsContainer
  from '../../../containers/SettingsContainer/SettingsContainer';

class SettingsPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      message: null,
    };
  }

  setMessage = msg => this.setState({ message: msg, error: null });

  setError = err => this.setState({ error: err, message: null });

  reset = () => this.setState({ error: null, message: null });

  render() {
    const { error, message } = this.state;

    return (
      <div className="settings-wrapper">
        <h2 className="settings__header">Account settings</h2>
        <div className={classNames({
          settings__info: true,
          settings__error: error,
          settings__success: message,
        })}
        >
          { error || message }
        </div>
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
