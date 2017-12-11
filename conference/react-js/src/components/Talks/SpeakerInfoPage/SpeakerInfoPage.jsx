import React from 'react';
import PropTypes from 'prop-types';

import SpeakerInfo from '../SpeakerInfo/SpeakerInfo';
import userShape from '../../../constants/user-shape';

const SpeakerInfoPage = ({ speaker, close }) => (
  <div className="tabs-container">
    <SpeakerInfo speaker={speaker} close={close} />
  </div>
);

SpeakerInfoPage.propTypes = {
  speaker: PropTypes.shape(userShape).isRequired,
  close: PropTypes.func.isRequired,
};

export default SpeakerInfoPage;
