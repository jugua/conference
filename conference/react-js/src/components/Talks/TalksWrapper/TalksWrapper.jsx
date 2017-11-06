import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import MyTalks from '../../../components/My-talks';
import UpdateTalk from '../../../components/UpdateTalk/UpdateTalk';
import SlideBlock from '../../../components/SlideBlock';

class TalksWrapper extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isUpdateTalk: false,
      talk: null,
    };
  }

  getTalkById = (talks, id) => (
    talks.find(talk => talk.id === id)
  );

  showUpdateTalk = ({ target: { dataset: { talkId } } }) => {
    if (!talkId) return;

    this.setState({
      isUpdateTalk: true,
      talk: this.getTalkById(this.props.userTalks, +talkId),
    });
  };

  closeUpdateTalk = () => {
    this.setState({
      isUpdateTalk: false,
      talk: null,
    });
  };

  render() {
    const { isUpdateTalk, talk } = this.state;
    return (
      <SlideBlock isOpened={isUpdateTalk}>
        <MyTalks onClick={this.showUpdateTalk} />
        <UpdateTalk talk={talk} close={this.closeUpdateTalk} />
      </SlideBlock>
    );
  }
}

TalksWrapper.propTypes = {
  userTalks: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default connect(
  ({ userTalks }) => ({ userTalks }),
)(TalksWrapper);
