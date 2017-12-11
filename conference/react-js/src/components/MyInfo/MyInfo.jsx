import React, { Component } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { TextField, RaisedButton } from 'material-ui';

import { uploadUserPhoto, defaultUserPhoto } from '../../constants/backend-url';
import userShape from '../../constants/user-shape';
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
      user: props.user,
      photoIsSelected: false,
    };
  }

  componentDidMount() {
    this.setDefaultUserPhoto();
    this.getUserPhoto(this.props.user.id);
  }

  componentWillReceiveProps({ user }) {
    this.setState({ user });
    this.setDefaultUserPhoto();
  }

  setDefaultUserPhoto = () => {
    axios.get(defaultUserPhoto)
      .then(() => {
        this.setState(prevState => ({
          user: {
            ...prevState.user,
            photo: defaultUserPhoto,
          },
        }));
      });
  };

  getUserPhoto = (id) => {
    axios.get(`${uploadUserPhoto}/${id}`)
      .then(() => {
        this.setState(prevState => ({
          user: {
            ...prevState.user,
            photo: `${uploadUserPhoto}/${id}`,
          },
        }
        ));
      });
  };

  handleOpenModal = () => {
    this.setState({ showInfoSavedModal: true });
  };

  handleCloseModal = () => {
    this.setState({ showInfoSavedModal: false });
  };

  handleCloseModal1 = () => {
    this.setState({ showChangePhotoModal: false,
      photoUpdateIsSuccessful: false,
      photoIsSelected: false,
    });
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

  requiredInput = (e) => {
    const inputName = e.target.name;
    const errorMsg = document.querySelector(`span[data-requiredInput=${inputName}]`);
    if (e.target.value === '' || e.target.value === ' ') {
      errorMsg.style.visibility = 'visible';
      this.setState({
        ...this.state,
        [`${inputName}FieldEmpty`]: true,
      });
    } else {
      errorMsg.style.visibility = 'hidden';
      this.setState({
        ...this.state,
        [`${inputName}FieldEmpty`]: false,
      });
    }
  };

  handleSaveInfo = (e) => {
    e.preventDefault();
    this.props.editUser(this.state.user);
    this.handleOpenModal();
    this.getUserPhoto(this.props.user.id);
  };

  handleChangePhoto = () => {
    this.setState({ showChangePhotoModal: true });
  };

  changeProfilePhoto = (e) => {
    e.preventDefault();

    const file = e.target.files[0];
    const photoURL = window.URL.createObjectURL(file);
    this.setState(prevState => ({
      user: {
        ...prevState.user,
        photo: photoURL,
      },
    }));
  };

  uploadPhotoToDB = (e) => {
    e.preventDefault();

    const choosePhotoBtn = document.querySelector('#choose-photo__btn');
    const userPhoto = choosePhotoBtn.files[0];

    const data = new FormData();
    data.append('file', userPhoto);

    if (choosePhotoBtn.files.length > 0) {
      axios.post(uploadUserPhoto, data)
        .then(() => {
          this.setState({ photoUpdateIsSuccessful: true,
            photoIsSelected: false });
        });
    } else {
      this.setState({ photoIsSelected: true });
    }
  };

  removePhotoPopUp = () => {
    this.setState({ showRemovePhotoConfirmationModal: true });
  };

  removePhoto = () => {
    axios.delete(uploadUserPhoto);
    this.setState(prevState => ({
      showRemovePhotoConfirmationModal: false,
      user: {
        ...prevState.user,
        photo: defaultUserPhoto,
      },
    }));
  };

  render() {
    const { user: {
      bio,
      job,
      company,
      past,
      info,
      photo,
    } } = this.state;

    const styles = {
      textarea: {
        width: '85%',
      },
      centeredButton: {
        display: 'block',
        width: '100px',
        margin: '10px auto',
      },
      rightButton: {
        float: 'right',
        margin: '15px',
      },
    };

    return (
      <div>
        <div className="my-info__photo-block">
          <img
            className="my-info__photo"
            src={photo || ''}
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
        <form className="my-info">
          <div className="input-required input-required__bio">
            <TextField
              id="my-info-bio"
              name="bio"
              floatingLabelText="Short Bio*"
              multiLine
              fullWidth
              rows={5}
              maxLength={2000}
              value={bio || ''}
              onChange={this.handleInput}
              onBlur={this.requiredInput}
              required
            />
            <span
              data-requiredInput="bio"
              className="my-info__required-message"
            >
              * this field is required</span>
          </div>
          <div className="input-wrapper">
            <div className="input-required">
              <TextField
                id="my-job-title"
                name="job"
                floatingLabelText="Job Title*"
                fullWidth
                maxLength={256}
                value={job || ''}
                onChange={this.handleInput}
                onBlur={this.requiredInput}
                required
              />
              <span
                data-requiredInput="job"
                className="my-info__required-message"
              >
                * this field is required</span>
            </div>
            <div className="input-required">
              <TextField
                id="my-info-company"
                name="company"
                floatingLabelText="Company*"
                fullWidth
                maxLength={256}
                value={company || ''}
                onChange={this.handleInput}
                onBlur={this.requiredInput}
                required
              />
              <span
                data-requiredInput="company"
                className="my-info__required-message"
              >
                * this field is required</span>
            </div>
          </div>
          <TextField
            id="my-past-conferences"
            name="past"
            floatingLabelText="Past Conferences"
            multiLine
            rows={5}
            maxLength={1000}
            value={past || ''}
            fullWidth
            onChange={this.handleInput}
          />
          <TextField
            id="my-additional-info"
            name="info"
            floatingLabelText="Additional Info"
            multiLine
            rows={5}
            maxLength={1000}
            value={info || ''}
            fullWidth
            onChange={this.handleInput}
          />
          <RaisedButton
            type="submit"
            label="save"
            style={styles.centeredButton}
            primary
            disabled={this.state.bioFieldEmpty ||
            this.state.jobFieldEmpty ||
            this.state.companyFieldEmpty}
            onClick={this.handleSaveInfo}
          />
        </form>
        {this.state.showInfoSavedModal &&
          <PopUpSaved
            showModal={this.state.showInfoSavedModal}
            closeModal={this.handleCloseModal}
            buttonStyles={styles.rightButton}
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
            photoIsSelected={this.state.photoIsSelected}
            photoUpdateIsSuccessful={this.state.photoUpdateIsSuccessful}
            buttonStyles={styles.rightButton}
          />}
        {this.state.showRemovePhotoConfirmationModal &&
          <PopUpRemovePhotoConfirmation
            showModal={this.state.showRemovePhotoConfirmationModal}
            closeModal={this.closeDeletePhotoModal}
            removePhoto={this.removePhoto}
            buttonStyles={styles.rightButton}
          />}
      </div>
    );
  }
}

MyInfo.propTypes = {
  editUser: PropTypes.func.isRequired,
  user: PropTypes.shape(userShape).isRequired,
};

export default MyInfo;
