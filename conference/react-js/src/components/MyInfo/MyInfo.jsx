import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { connect } from 'react-redux';

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
      user: {
        id: null,
        bio: null,
        job: null,
        company: null,
        past: null,
        photo: null,
        info: null,
        contacts: null,
      },
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
    const { bio, job, company, past, photo, info, contacts } = this.props.user;
    return (
      <div className="tabs-container">
        <div className="my-info__ava-block">
          <img className="my-info__ava" src={photo} alt="" />
          <button className="my-info__remove" />
          <span className="change-photo">Change photo</span>
        </div>
        <form className="my-info" name="" noValidate>
          <TextareaBlock
            id="my-info-bio"
            labelClassName="form-label_required my-info__label
              my-info__label_bio"
            className="textarea textarea-focus my-info__bio"
            label="Short Bio"
            name="bio"
            maxLen={2000}
            value={bio}
          />
          <InputBlock
            id="my-job-title"
            labelClass="form-label_required my-info__label"
            label="Job Title"
            name="job"
            inputClass="field_border my-info__field_job"
            maxLength={256}
            value={job}
          />
          <InputBlock
            id="my-info-company"
            labelClass="form-label_required my-info__label"
            label="Company"
            name="company"
            inputClass="field_border my-info__field_company"
            maxLength={256}
            value={company}
          />
          <TextareaBlock
            id="my-past-conferences"
            labelClassName="my-info__label"
            className="textarea textarea-focus"
            label="Past Conferences"
            name="past"
            rows={5}
            maxLen={1000}
            value={past}
          />
          <InputBlock
            id="my-info-linkedin"
            labelClass="my-info__label"
            label="LinkedIn"
            name="linkedin"
            inputClass="field_border"
            value={contacts.linkedin}
          />
          <InputBlock
            id="my-info-twitter"
            labelClass="my-info__label"
            label="twitter"
            name="twitter"
            inputClass="field_border"
            value={contacts.twitter}
          />
          <InputBlock
            id="my-info-facebook"
            labelClass="my-info__label"
            label="facebook"
            name="facebook"
            inputClass="field_border"
            value={contacts.facebook}
          />
          <InputBlock
            id="my-info-blog"
            labelClass="my-info__label"
            label="blog"
            name="blog"
            inputClass="field_border"
            value={contacts.blog}
          />
          <TextareaBlock
            id="my-additional-info"
            labelClassName="form-label"
            className="textarea textarea-focus"
            label="Additional Info"
            name="info"
            rows={5}
            maxLen={1000}
            value={info}
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

MyInfo.propTypes = {
  user: PropTypes.objectOf(PropTypes.shape({})),
  bio: PropTypes.string,
  job: PropTypes.string,
  company: PropTypes.string,
  past: PropTypes.string,
  photo: PropTypes.string,
  contacts: PropTypes.objectOf(PropTypes.shape([])),
  info: PropTypes.string,
};

MyInfo.defaultProps = {
  user: undefined,
  bio: undefined,
  job: undefined,
  company: undefined,
  past: undefined,
  photo: undefined,
  contacts: undefined,
  info: undefined,
};

const mapStateToProps = user => user;

export default connect(mapStateToProps)(MyInfo);
