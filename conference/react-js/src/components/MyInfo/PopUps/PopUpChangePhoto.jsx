import React, { PureComponent } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import InputBlock from '../../InputBlock/InputBlock';

class PopUpChangePhoto extends PureComponent {
  render() {
    const { showModal,
      closeModal,
      changeProfilePhoto,
      uploadPhotoToDB,
      photoIsSelected,
      photoUpdateIsSuccessful } = this.props;
    return (
      <div>
        <ReactModal
          isOpen={showModal}
          contentLabel="onRequestClose"
          onRequestClose={closeModal}
          className={{
            base: classNames({
              'pop-up pop-up__change-photo': true,
              'pop-up_close': !showModal,
            }),
          }}
          overlayClassName={{
            afterOpen: classNames({
              'pop-up-wrapper': true,
              'pop-up_close': !showModal,
            }),
          }}
        >
          <h3 className="pop-up__title">Upload New Photo</h3>
          <form noValidate encType="multipart/form-data">
            <div required name="dragfile">
              <p className="pop-up__notification pop-up__notification_light">
                It is much easier to identify you if you have a photo.
              </p>
              <p className="pop-up__notification pop-up__notification_light">
                You can upload an image in JPG, PNG or GIF formats.
                The maximum allowed size for uploads is 2 Mb.
              </p>
              <div className="internal-wrapper">
                <InputBlock
                  wrapperClass="choose-photo__block"
                  id="choose-photo__btn"
                  type="file"
                  inputClass="file-upload__uploading"
                  labelClass="btn choose-photo__btn"
                  name="file"
                  onChange={changeProfilePhoto}
                  label="Choose"
                  accept="image/jpeg,image/png,image/gif"
                  size="2MB"
                  required
                />
                <button
                  className="btn my-info__save-button"
                  onClick={uploadPhotoToDB}
                >
                  Save Photo</button>
              </div>
            </div>
          </form>
          {photoIsSelected && <p className="change-photo__successful">
            *Please choose photo</p>}
          {photoUpdateIsSuccessful && <p className="change-photo__successful">
            *The photo has been saved</p>}
          <button
            className="pop-up__close"
            onClick={closeModal}
          />
        </ReactModal>
      </div>
    );
  }
}

PopUpChangePhoto.propTypes = {
  showModal: PropTypes.bool.isRequired,
  closeModal: PropTypes.func.isRequired,
  changeProfilePhoto: PropTypes.func.isRequired,
  uploadPhotoToDB: PropTypes.func.isRequired,
  photoIsSelected: PropTypes.bool.isRequired,
  photoUpdateIsSuccessful: PropTypes.bool.isRequired,
};

export default PopUpChangePhoto;
