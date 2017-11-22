import React, { Component } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import changeUserInfo from '../../actions/change-user-info';
import { uploadUserPhoto } from '../../constants/backend-url';
import userShape from '../../constants/user-shape';

import InputBlock from '../InputBlock/InputBlock';
import TextareaBlock from '../TextareaBlock/TextareaBlock';
import PopUpSaved from './PopUps/PopUpSaved';
import PopUpPreventUnsavedExit from './PopUps/PopUpPreventUnsavedExit';
import PopUpChangePhoto from './PopUps/PopUpChangePhoto';
import PopUpRemovePhotoConfirmation from './PopUps/PopUpRemovePhotoConfirmation';

class MyInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showPreventUnsavedExitModal: false,
      showInfoSavedModal: false,
      showChangePhotoModal: false,
      photoUpdateIsSuccessful: false,
      showRemovePhotoConfirmationModal: false,
      user: {},
      file: '',
      userPhotoSrc: 'data:image/jpeg;base64,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',
    };
  }

  componentDidMount() {
    this.setUserInfo(this.props);
    this.props.updateInfo();
  }

  componentWillReceiveProps(nextProps) {
    this.setUserInfo(nextProps);
    this.getUserPhoto(nextProps.user.id);
  }

  setUserInfo = ({ user }) => {
    this.setState({ user });
  };

  getUserPhoto = (id) => {
    axios.get(`${uploadUserPhoto}/${id}`)
      .then(() => {
        this.setState({ userPhotoSrc: `${uploadUserPhoto}/${id}` });
      });
  };

  handleOpenModal = () => {
    this.setState({ showInfoSavedModal: true });
  };

  handleCloseModal = () => {
    this.setState({ showInfoSavedModal: false });
  };

  handleCloseModal1 = () => {
    this.setState({ showChangePhotoModal: false });
  };

  closeDeletePhotoModal = () => {
    this.setState({ showRemovePhotoConfirmationModal: false });
  };

  handleInput = (e) => {
    const upUser = {
      ...this.state.user,
      [e.target.name]: e.target.value,
    };
    this.setState(() => ({ user: upUser }));
  };

  handleSaveInfo = (e) => {
    e.preventDefault();
    changeUserInfo(this.state.user);
    this.handleOpenModal();
  };

  handleChangePhoto = () => {
    this.setState({ showChangePhotoModal: true });
  };

  changeProfilePhoto = (e) => {
    e.preventDefault();

    const file = e.target.files[0];
    const photoURL = window.URL.createObjectURL(file);

    this.setState({ userPhotoSrc: photoURL });
  };

  uploadPhotoToDB = (e) => {
    e.preventDefault();

    const choosePhotoBtn = document.querySelector('#choose-photo__btn');
    const userPhoto = choosePhotoBtn.files[0];

    const data = new FormData();
    data.append('file', userPhoto);

    axios.post(uploadUserPhoto, data)
      .then((res) => {
        console.log(res);
      });

    this.setState({ photoUpdateIsSuccessful: true });
  };

  removePhotoPopUp = () => {
    this.setState({ showRemovePhotoConfirmationModal: true });
  };

  removePhoto = () => {
    axios.delete(uploadUserPhoto)
      .then((res) => {
        console.log(res, 'photo was deleted');
      });

    this.setState({ showRemovePhotoConfirmationModal: false,
      userPhotoSrc: 'data:image/jpeg;base64,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',
    });
  };

  render() {
    const { user: {
      bio = '',
      job = '',
      company = '',
      past = '',
      info = '' } } = this.state;

    return (
      <div>
        <div className="my-info__photo-block">
          <img
            className="my-info__photo"
            src={this.state.userPhotoSrc}
            alt=""
          />
          <button
            className="my-info__remove"
            onClick={this.removePhotoPopUp}
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
            data-type="saveInfo"
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
          changeProfilePhoto={this.changeProfilePhoto}
          uploadPhotoToDB={this.uploadPhotoToDB}
          photoUpdateIsSuccessful={this.state.photoUpdateIsSuccessful}
        />}
        {this.state.showRemovePhotoConfirmationModal &&
        <PopUpRemovePhotoConfirmation
          showModal={this.state.showRemovePhotoConfirmationModal}
          closeModal={this.closeDeletePhotoModal}
          removePhoto={this.removePhoto}
        />}
      </div>
    );
  }
}

MyInfo.propTypes = {
  updateInfo: PropTypes.func.isRequired,
  user: PropTypes.shape(userShape).isRequired,
};

export default MyInfo;
