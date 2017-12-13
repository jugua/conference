import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import axios from 'axios';

import EventTalks from '../../../containers/EventTalks/EventTalks';
import ReviewTalk from '../../Talks/ReviewTalk/ReviewTalk';
import SlideBlock from '../../SlideBlock/SlideBlock';
import SpeakerInfoPage from '../../Talks/SpeakerInfoPage/SpeakerInfoPage';
import { speakerInfoForOrganiser } from '../../../constants/backend-url';

class EventTalksWrapper extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isReviewTalk: false,
      isReviewSpeakerInfo: false,
      isOpened: 0,
      talk: null,
      speaker: {},
    };
  }

  getTalkById = (talks, id) => (
    talks.find(talk => talk.id === id)
  );

getUserInfo = (id) => {
  axios.get(`${speakerInfoForOrganiser}${id}`)
    .then((res) => {
      this.setState({
        speaker: res.data,
      });
    });
};

  showReviewTalk = (rowId, colId, { target: { dataset: { talkId, speakerId } } }) => {
    if (talkId) {
      this.setState({
        isReviewTalk: true,
        talk: this.getTalkById(this.props.talks, +talkId),
        isOpened: 2,
      });
    } else if (speakerId) {
      this.setState({
        isReviewSpeakerInfo: true,
        isOpened: 1,
      });
      this.getUserInfo(+speakerId);
    }
    return null;
  };

  closeSpeakerInfo = () => {
    this.setState({
      isOpened: 0,
    });
  };

  closeReviewTalk = () => {
    this.setState({
      isOpened: 0,
      talk: null,
    });
  };

  render() {
    const { talk, isOpened, speaker } = this.state;
    return (
      <SlideBlock isOpened={isOpened}>
        <EventTalks onClick={this.showReviewTalk} />
        <SpeakerInfoPage speaker={speaker} close={this.closeSpeakerInfo} />
        <ReviewTalk talk={talk} close={this.closeReviewTalk} />
      </SlideBlock>
    );
  }
}

EventTalksWrapper.propTypes = {
  talks: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default connect(
  ({ talks }) => ({ talks }),
)(EventTalksWrapper);
