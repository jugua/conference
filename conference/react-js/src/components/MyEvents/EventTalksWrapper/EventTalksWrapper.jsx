import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import EventTalks from '../../../containers/EventTalks/EventTalks';
import ReviewTalk from '../../Talks/ReviewTalk/ReviewTalk';
import SlideBlock from '../../SlideBlock';
import MyInfo from '../../MyInfo/MyInfo';

class EventTalksWrapper extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isReviewTalk: false,
      isReviewSpeakerInfo: false,
      showInfoSavedModal: false,
      talk: null,
    };
  }

  getTalkById = (talks, id) => (
    talks.find(talk => talk.id === id)
  );

  showReviewTalk = (rowId, colId, { target: { dataset: { talkId } } }) => {
    if (!talkId || isNaN(rowId) || isNaN(colId)) return;

    this.setState({
      isReviewTalk: true,
      talk: this.getTalkById(this.props.talks, +talkId),
    });
  };

  showSpeakerInfo = (rowId, colId) => {
    if (isNaN(rowId) || isNaN(colId)) return;

    this.setState({
      isReviewSpeakerInfo: true,
    });
  };

  handleCloseModal = () => {
    this.setState({ showInfoSavedModal: false });
  };

  closeReviewTalk = () => {
    this.setState({
      isReviewTalk: false,
      talk: null,
    });
  };

  render() {
    const { isReviewTalk, talk } = this.state;
    return (
      <SlideBlock isOpened={isReviewTalk}>
        <EventTalks onClick={this.showReviewTalk} />
        <MyInfo onClick={this.showReviewTalk} />
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
