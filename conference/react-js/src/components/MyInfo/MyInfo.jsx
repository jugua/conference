import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import { connect } from 'react-redux';
import axios from 'axios';
import { myInfo } from '../../constants/backend-url';

import InputBlock from '../InputBlock/InputBlock';
import TextareaBlock from '../TextareaBlock/index';
import PopUpSaved from './PopUps/PopUpSaved';
import PopUpPreventUnsavedExit from './PopUps/PopUpPreventUnsavedExit';
import PopUpChangePhoto from './PopUps/PopUpChangePhoto';

class MyInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showPreventUnsavedExitModal: false,
      showInfoSavedModal: false,
      showChangePhotoModal: false,
      user: {},
    };
  }

  componentDidMount() {
    this.setDefaultValues(this.props);
    console.log(this.props.user);
  }

  setDefaultValues = ({ user }) => {
    this.setState({ user });
  };

  handleOpenModal = (e) => {
    e.preventDefault();
    this.setState({ showInfoSavedModal: true });
  };

  handleCloseModal = (e) => {
    e.preventDefault();
    this.setState({ showInfoSavedModal: false });
  };

  handleCloseModal1 = (e) => {
    e.preventDefault();
    this.setState({ showChangePhotoModal: false });
  };

  handleInput = (e) => {
    e.preventDefault();
    const upUser = {
      ...this.state.user,
      [e.target.name]: e.target.value,
    };
    this.setState(() => ({ user: upUser }));
  };

  handleSaveInfo = (e) => {
    e.preventDefault();

    const saveUserInfo = ({ bio, job, company }) => {
      const body = { bio, job, company };
      return axios.post(myInfo, body);
    };

    saveUserInfo(this.state.user)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
    this.setState({ showInfoSavedModal: true });
  };

  handleChangePhoto = (e) => {
    e.preventDefault();
    this.setState({ showChangePhotoModal: true });
  }

  render() {
    const { bio, job, company, past, photo, info } = this.state.user;

    return (
      <div className="tabs-container">
        <div className="my-info__ava-block">
          <img
            className="my-info__ava"
            src={photo}
            alt=""
          />
          <button
            className="my-info__remove"
          />
          <span
            className="change-photo"
            onClick={this.handleChangePhoto}
            role="button"
            tabIndex="-1"
          >Change photo</span>
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
            onChange={this.handleInput}
          />
          <InputBlock
            id="my-job-title"
            labelClass="form-label_required my-info__label"
            label="Job Title"
            name="job"
            inputClass="field_border my-info__field_job"
            maxLength={256}
            value={job}
            onChange={this.handleInput}
          />
          <InputBlock
            id="my-info-company"
            labelClass="form-label_required my-info__label"
            label="Company"
            name="company"
            inputClass="field_border my-info__field_company"
            maxLength={256}
            value={company}
            onChange={this.handleInput}
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
            onChange={this.handleInput}
          />
          {/* <InputBlock */}
          {/* id="my-info-linkedin" */}
          {/* labelClass="my-info__label" */}
          {/* label="linkedIn" */}
          {/* name="linkedin" */}
          {/* inputClass="field_border" */}
          {/* value={contacts.linkedin} */}
          {/* onChange={this.handleContactsInput} */}
          {/* /> */}
          {/* <InputBlock */}
          {/* id="my-info-twitter" */}
          {/* labelClass="my-info__label" */}
          {/* label="twitter" */}
          {/* name="twitter" */}
          {/* inputClass="field_border" */}
          {/* value={contacts.twitter} */}
          {/* onChange={this.handleContactsInput} */}
          {/* /> */}
          {/* <InputBlock */}
          {/* id="my-info-facebook" */}
          {/* labelClass="my-info__label" */}
          {/* label="facebook" */}
          {/* name="facebook" */}
          {/* inputClass="field_border" */}
          {/* value={contacts.facebook} */}
          {/* onChange={this.handleContactsInput} */}
          {/* /> */}
          {/* <InputBlock */}
          {/* id="my-info-blog" */}
          {/* labelClass="my-info__label" */}
          {/* label="blog" */}
          {/* name="blog" */}
          {/* inputClass="field_border" */}
          {/* value={contacts.blog} */}
          {/* onChange={this.handleContactsInput} */}
          {/* /> */}
          <TextareaBlock
            id="my-additional-info"
            labelClassName="form-label"
            className="textarea textarea-focus"
            label="Additional Info"
            name="info"
            rows={5}
            maxLen={1000}
            value={info}
            onChange={this.handleInput}
          />
          <input
            type="submit"
            value="save"
            className="btn my-info__button"
            onClick={this.handleSaveInfo}
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
        {this.state.showChangePhotoModal &&
        <PopUpChangePhoto
          showModal={this.state.showChangePhotoModal}
          closeModal={this.handleCloseModal1}
        />}
      </div>
    );
  }
}

MyInfo.propTypes = {
  user: PropTypes.shape({
    bio: PropTypes.string,
    job: PropTypes.string,
    company: PropTypes.string,
    past: PropTypes.string,
    photo: PropTypes.string,
    contacts: PropTypes.array,
    info: PropTypes.string,
  }),

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
