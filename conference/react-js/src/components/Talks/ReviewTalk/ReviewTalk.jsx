import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import axios from 'axios';

class ReviewTalk extends Component {
  constructor(props) {
    super(props);
    this.state = {
      status: false,
    };
  }

  changeStatus = () => {
    const id = this.props.talk.id;
    axios.patch('/talk',
      { id,
        status: 'Ap' })
      .then(() => {
        console.log('success');
      });
  };

  render() {
    const { close, talk } = this.props;
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
            <label
              htmlFor="my-talk-status"
              className="form-label my-talk-settings__label"
            >Status
            </label>
            <select
              name="status"
              id="my-talk-status"
              className="my-talk-settings__select"
            >
              <option defaultValue="" />
              {this.setStatus(status)}
            </select>
          </div>
          <input
            className="review-talk__button"
            type="button"
            value="Draft"
            onClick={this.changeStatus}
          />
          <input
            className="review-talk__button"
            type="button"
            value="Submit"
            onClick={this.changeStatus}
          />
          <input
            className="review-talk__button"
            type="button"
            value="Pending"
            onClick={this.changeStatus}
          />
          <input
            className="review-talk__button"
            type="button"
            value="Update Request"
            onClick={this.changeStatus}
          />
          <input
            className="review-talk__button"
            type="button"
            value="Accept"
            onClick={this.changeStatus}
          />
          <input
            className="review-talk__button"
            type="button"
            value="NotAccept"
            onClick={this.changeStatus}
          />

          <button className="review-talk__button review-talk__button_reject" onClick={this.changeStatus}>Reject</button>
          <button
            className="review-talk__button review-talk__button_reject"
            onClick={close}
          >
            Cancel
          </button>
        </div>
      </div>
    );
  }
}

ReviewTalk.defaultProps = {
  talk: {},
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
};

export default ReviewTalk;
