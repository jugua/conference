import React, { PureComponent } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';

class PopUpRemovePhotoConfirmation extends PureComponent {
  render() {
    const { showModal,
      closeModal,
      removePhoto } = this.props;
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
          <h3 className="pop-up__title">Delete Photo</h3>
          <button
            className="pop-up__close"
            onClick={closeModal}
          />
          <p className="pop-up__notification">
            Do you want to delete your profile photo?
          </p>
          <button
            className="btn btn_right btn_small"
            onClick={removePhoto}
          >confirm</button>
          <button
            className="btn btn_right btn_small"
            onClick={closeModal}
          >cancel</button>
        </ReactModal>
      </div>
    );
  }
}

PopUpRemovePhotoConfirmation.propTypes = {
  showModal: PropTypes.bool,
  closeModal: PropTypes.func,
  removePhoto: PropTypes.func,
};

PopUpRemovePhotoConfirmation.defaultProps = {
  showModal: false,
  closeModal: false,
  removePhoto: false,
};

export default PopUpRemovePhotoConfirmation;
