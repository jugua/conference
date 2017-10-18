import React, { Component } from 'react';
import InputBlock from '../InputBlock/index';
import TextareaBlock from '../TextareaBlock/index';
import PopUpSaved from './PopUps/PopUpSaved';
import PopUpPreventUnsavedExit from './PopUps/PopUpPreventUnsavedExit';

class MyInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showPreventUnsavedExitModal: false,
      showInfoSavedModal: false,
    };
  }

    handleOpenModal = (e) => {
      e.preventDefault();
      this.setState({ showInfoSavedModal: true });
    };

    handleCloseModal = (e) => {
      e.preventDefault();
      this.setState({
        showInfoSavedModal: false,
      });
    };

    render() {
      return (
        <div className="tabs-container">
          <div className="my-info__ava-block">
            <img className="my-info__ava" src="" alt="" />
          </div>
          <form className="my-info" name="" noValidate>
            <TextareaBlock
              id="my-info-bio"
              labelClassName="form-label_required my-info__label
              my-info__label_bio"
              className="textarea my-info__bio"
              label="Short Bio"
              name="bio"
              maxLen={2000}
            />
            <InputBlock
              id="my-job-title"
              labelClass="form-label_required my-info__label"
              label="Job Title"
              name="job"
              inputClass="field_border my-info__field_job"
              maxLength={256}
            />
            <InputBlock
              id="my-info-company"
              labelClass="form-label_required my-info__label"
              label="Company"
              name="company"
              inputClass="field_border my-info__field_company"
              maxLength={256}
            />
            <TextareaBlock
              id="my-past-conferences"
              labelClassName="my-info__label"
              className="textarea"
              label="Past Conferences"
              name="past"
              rows={5}
              maxLen={1000}
            />
            <InputBlock
              id="my-info-linkedin"
              labelClass="my-info__label"
              label="LinkedIn"
              name="linkedin"
              inputClass="field_border"
            />
            <InputBlock
              id="my-info-twitter"
              labelClass="my-info__label"
              label="twitter"
              name="twitter"
              inputClass="field_border"
            />
            <InputBlock
              id="my-info-facebook"
              labelClass="my-info__label"
              label="facebook"
              name="facebook"
              inputClass="field_border"
            />
            <InputBlock
              id="my-info-blog"
              labelClass="my-info__label"
              label="blog"
              name="blog"
              inputClass="field_border"
            />
            <TextareaBlock
              id="my-additional-info"
              labelClassName="form-label"
              className="textarea"
              label="Additional Info"
              name="info"
              rows={5}
              maxLen={1000}
            />
            <input
              type="submit"
              value="save"
              className="btn my-info__button"
              onClick={this.handleOpenModal}
            />
          </form>
          {this.state.showInfoSavedModal &&
          <PopUpSaved
            showModal={this.state.showInfoSavedModal}
            closeModal={this.handleCloseModal}
          />}
          {this.state.showPreventUnsavedExitModal &&
            <PopUpPreventUnsavedExit
              showModal={this.state.showPreventUnsavedExitModal}
              closeModal={this.handleCloseModal}
            />}
        </div>
      );
    }
}

export default MyInfo;
