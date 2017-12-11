import React from 'react';
import PropTypes from 'prop-types';

import SpeakerInfo from '../SpeakerInfo/SpeakerInfo';
import userShape from '../../../constants/user-shape';

const SpeakerInfoPage = props => (
  <div className="tabs-container">
    <SpeakerInfo speaker={props.speaker} />
  </div>
);

SpeakerInfoPage.propTypes = {
  speaker: PropTypes.shape(userShape).isRequired,
};

export default SpeakerInfoPage;
