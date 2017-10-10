import React, { Component } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import InputBox from '../Form-components/InputBox';
import TextareaBox from '../Form-components/TextareaBox';

class SpeakerInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
    };
  }

  handleOpenModal = () => {
    this.setState({ showModal: true });
  };

  handleCloseModal = () => {
    this.setState({
      showModal: false,
    });
  };

  render() {
    return (
      <div>
        <button onClick={this.handleOpenModal}>Trigger Modal</button>
        <ReactModal
          isOpen={this.state.showModal}
          contentLabel="onRequestClose"
          onRequestClose={this.handleCloseModal}
          className={{
            base: classNames({
              'pop-up pop-up_big': true,
              'pop-up_close': !this.state.showModal,
            }),
          }}
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
              <TextareaBox
                id="my-info-bio"
                labelClassName="my-info__label my-info__label_bio"
                className="textarea my-info__bio"
                label="Short Bio"
                name="bio"
                maxLen={2000}
              />
              <InputBox id="my-job-title" label="Job Title" disabled />
              <InputBox id="my-company" label="Company" disabled />
              <TextareaBox
                id="my-past-conferences"
                label="Past Conferences"
                name="past"
                rows={5}
              />
              <InputBox id="my-email" label="Email" disabled />
              <InputBox id="my-linkedin" label="LinkedIn" disabled />
              <InputBox id="my-twitter" label="Twitter" disabled />
              <InputBox id="my-facebook" label="Facebook" disabled />
              <InputBox id="my-blog" label="Blog" disabled />
              <TextareaBox
                id="my-additional-info"
                label="Additional Info"
                name="past"
                rows={5}
                maxLen={1000}
              />
            </form>
            <button
              className="btn talks-user-info-popup__button"
              onClick={this.handleCloseModal}
            >close
            </button>
          </div>
        </ReactModal>
      </div>
    );
  }
}

export default SpeakerInfo;
