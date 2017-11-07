import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import userShape from '../../../constants/user-shape';

import SettingsContainer
  from '../../../containers/SettingsContainer/SettingsContainer';
import SettingsInfo from '../SettingsInfo/SettingsInfo';

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
        <SettingsInfo error={error} message={message} />
        <SettingsContainer
          user={this.props.user}
          setMessage={this.setMessage}
          setError={this.setError}
          reset={this.reset}
        />
      </div>
    );
  }
}

SettingsPage.propTypes = {
  user: PropTypes.shape(userShape).isRequired,
};

export default SettingsPage;
