import React, { Component } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import Input from '../Form-components/Input';
import Label from '../Form-components/Label';
import Textarea from '../Form-components/Textarea';

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
                <Label
                  className="form-label my-info__label my-info__label_bio"
                  htmlFor="my-info-bio"
                  label="Short Bio"
                />
                <Textarea
                  id="my-info-bio"
                  className="textarea my-info__bio"
                  name="bio"
                  maxLen="2000"
                />
                <Label
                  className="form-label"
                  htmlFor="my-job-title"
                  label="Job Title"
                />
                <Input
                  id="my-job-title"
                />
                <Label
                  className="form-label"
                  htmlFor="my-company"
                  label="Company"
                />
                <Input
                  id="my-company"
                />
                <Label
                  className="form-label"
                  htmlFor="my-past-conferences"
                  label="Past Conferences"
                />
                <Textarea
                  id="my-past-conferences"
                  name="past"
                  rows="5"
                />
                <Label
                  className="form-label"
                  htmlFor="my-email"
                  label="Email"
                />
                <Input
                  id="my-email"
                />
                <Label
                  className="form-label"
                  htmlFor="my-linkedin"
                  label="LinkedIn"
                />
                <Input id="my-linkedin" />
                <Label
                  className="form-label"
                  htmlFor="my-twitter"
                  label="Twitter"
                />
                <Input
                  id="my-twitter"
                />
                <Label
                  className="form-label"
                  htmlFor="my-facebook"
                  label="Facebook"
                />
                <Input
                  id="my-facebook"
                />
                <Label
                  className="form-label"
                  htmlFor="my-blog"
                  label="Blog"
                />
                <Input
                  id="my-blog"
                />
                <Label
                  className="form-label"
                  htmlFor="my-additional-info"
                  label="Additional Info"
                />
                <Textarea
                  id="my-additional-info"
                  name="past"
                  rows="5"
                  maxLen="1000"
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
