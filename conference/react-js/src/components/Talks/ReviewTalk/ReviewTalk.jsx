import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { connect } from 'react-redux';
import PopUpMessage from '../../PopUpMessage/PopUpMessage';
import { myEvents } from '../../../constants/route-url';

class ReviewTalk extends Component {
  constructor(props) {
    super(props);
    this.state = {
      newStatus: '',
      result: false,
    };
  }

  setStatus = data => (data.map(({ id, status }) =>
    (<option key={id}>{status}</option>),
  ));

  selectStatus = (e) => {
    this.setState({ newStatus: e.target.value });
  }

  changeStatus = () => {
    const id = this.props.talk.id;
    const status = this.state.newStatus;

    axios.patch('/talk',
      { id,
        status })
      .then(() => {
        this.setState({ result: true });
      });
  };

  render() {
    const { close, talk, status } = this.props;
    const header = 'Status';
    const body = 'Status was changed';
    const url = myEvents;
    return (
      <div className="tabs-container">
        <div className="review-talk__topic">
          {talk.topic}
        </div>
        <div className="review-talk__type">
          {talk.type}
        </div>
        <div className="review-talk__title">
          {talk.title}
        </div>
        <p className="review-talk__description">
          {talk.description}
        </p>
        <p className="review-talk__language">
          Language: {talk.lang}
        </p>
        <p className="review-talk__level">
          Level: {talk.level}
        </p>
        <div className="review-talk__additional-info">
          <a className="additional-info__title link">
            Additional info
          </a>
          <p className="additional-info__text">
            {talk.addon}
          </p>
        </div>
        <div className="review-talk__comments" />
        <div className="review-talk__button-group">
          <div className="my-talk-settings__select-wrapper">
            <select
              name="status"
              id="my-talk-status"
              className="my-talk-settings__select"
              onChange={this.selectStatus}
            >
              <option defaultValue="" />
              {this.setStatus(status)}
            </select>
          </div>
          <button className="review-talk__button" onClick={this.changeStatus}>Submit</button>
          <button
            className="review-talk__button review-talk__button_reject"
            onClick={close}
          >
            Cancel
          </button>
        </div>
        {this.state.result && (
          <PopUpMessage header={header} body={body} url={url} />
        )}
      </div>
    );
  }
}

ReviewTalk.defaultProps = {
  talk: {},
  status: [],
};

ReviewTalk.propTypes = {
  close: PropTypes.func.isRequired,
  talk: PropTypes.shape({
    id: PropTypes.number,
    title: PropTypes.string,
    description: PropTypes.string,
    topic: PropTypes.string,
    type: PropTypes.string,
    lang: PropTypes.string,
    level: PropTypes.string,
    addon: PropTypes.string,
  }),
  status: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
};

const mapStateToProps = status => status;

export default connect(mapStateToProps)(ReviewTalk);
