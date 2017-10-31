import React, { PureComponent } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import InputBlock from '../../InputBlock/InputBlock';

class PopUpChangePhoto extends PureComponent {
  render() {
    const { showModal, closeModal } = this.props;
    return (
      <div>
        <ReactModal
          isOpen={showModal}
          contentLabel="onRequestClose"
          onRequestClose={closeModal}
          className={{
            base: classNames({
              'pop-up': true,
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
          <form noValidate>
            <div required name="dragfile">
              <p className="pop-up__notification pop-up__notification_light">
                It is much easier to identify you if you have a photo.
              </p>
              <p className="pop-up__notification pop-up__notification_light">
                You can upload an image in JPG, PNG or GIF format.
                The maximum allowed size for uploads is 2 Mb
              </p>
              <InputBlock
                id="choose-photo__btn"
                type="file"
                inputClass="file-upload__uploading"
                labelClass="btn choose-photo__btn"
                name="file"
                label="Choose"
                required
              />
            </div>
          </form>
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
};

PopUpChangePhoto.defaultProps = {
  showModal: false,
};

export default PopUpChangePhoto;
