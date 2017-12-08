import React, { Component } from 'react';

class SpeakerInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      test: '',
    };
  }

  render() {
    return (
      <div>
        <h3 className="pop-up__title talks-user-info-popup__title">
                    User&#39;s info</h3>
      </div>
    );
  }
}

export default SpeakerInfo;
