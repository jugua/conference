import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import MyTalks from '../../../components/MyTalks/MyTalks';
import UpdateTalk from '../../../components/UpdateTalk/UpdateTalk';
import SlideBlock from '../../../components/SlideBlock/SlideBlock';

class TalksWrapper extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isOpened: 0,
      talk: null,
    };
  }

  getTalkById = (talks, id) => (
    talks.find(talk => talk.id === id)
  );

  showUpdateTalk = (rowId, colId, { target: { dataset: { talkId } } }) => {
    if (!talkId || isNaN(rowId) || isNaN(colId)) return;

    this.setState({
      isOpened: 1,
      talk: this.getTalkById(this.props.talks, +talkId),
    });
  };

  closeUpdateTalk = () => {
    this.setState({
      isOpened: 0,
      talk: null,
    });
  };

  render() {
    const { talk, isOpened } = this.state;
    return (
      <SlideBlock isOpened={isOpened}>
        <MyTalks onClick={this.showUpdateTalk} />
        <UpdateTalk talk={talk} close={this.closeUpdateTalk} />
      </SlideBlock>
    );
  }
}

TalksWrapper.propTypes = {
  talks: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default connect(
  ({ talks }) => ({ talks }),
)(TalksWrapper);
