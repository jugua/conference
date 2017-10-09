import React, { Component } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';

class SpeakerInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
    };
  }

    handleOpenModal = () => {
      this.setState({ showModal: true });
    }

    handleCloseModal = () => {
      this.setState({
        showModal: false,
      });
    }

    render() {
      return (
        <div>
          <button onClick={this.handleOpenModal}>Trigger Modal</button>
          <ReactModal
            isOpen={this.state.showModal}
            contentLabel="onRequestClose Example"
            onRequestClose={this.handleCloseModal}
            className={{
              base: classNames({
                'pop-up pop-up_big': true,
                'pop-up_close': !this.state.showModal,
              }) }}
            overlayClassName={{
              afterOpen: classNames({
                'pop-up-wrapper': true,
                'pop-up_close': !this.state.showModal,
              }),
            }}
          >
            <h3 className="pop-up__title talks-user-info-popup__title">
              User&#39;s info</h3>
            <button
              className="pop-up__close"
              onClick={this.handleCloseModal}
            />
            <div className="talks-user-info-popup__form-wrapper">
              <img className="my-info__ava" src="" alt="" />
              <form className="my-info" name="">
                <label
                  htmlFor="my-info-bio"
                  className="form-label my-info__label my-info__label_bio"
                >Short Bio
                </label>
                <textarea
                  name="bio"
                  id="my-info-bio"
                  className="textarea my-info__bio"
                  maxLength="2000"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-job-title"
                >Job Title</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-job-title"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-company"
                >Company</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-company"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-past-conferences"
                >Past Conferences</label>
                <textarea
                  type="text"
                  name="past"
                  id="my-past-conferences"
                  rows="5"
                  className="textarea"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-email"
                >Email</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-email"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-linkedin"
                >LinkedIn</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-linkedin"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-twitter"
                >twitter</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-twitter"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-facebook"
                >facebook</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-facebook"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-blog"
                >blog</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-blog"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-additional-info"
                >Additional Info</label>
                <textarea
                  rows="5"
                  className="textarea"
                  id="my-additional-info"
                  disabled
                  maxLength="1000"
                />
              </form>
              <button
                className="btn talks-user-info-popup__button"
                onClick={this.handleCloseModal}
              >close</button>
            </div>
          </ReactModal>
        </div>
      );
    }
}

export default SpeakerInfo;
