import React, { PureComponent } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import { RaisedButton } from 'material-ui';

class PopUpRemovePhotoConfirmation extends PureComponent {
  render() {
    const { showModal,
      closeModal,
      removePhoto,
      buttonStyles } = this.props;
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
          <RaisedButton
            label="confirm"
            primary
            style={buttonStyles}
            onClick={removePhoto}
          />
          <RaisedButton
            label="cancel"
            secondary
            style={buttonStyles}
            onClick={closeModal}
          />
        </ReactModal>
      </div>
    );
  }
}

PopUpRemovePhotoConfirmation.propTypes = {
  showModal: PropTypes.bool,
  closeModal: PropTypes.func,
  removePhoto: PropTypes.func,
  buttonStyles: PropTypes.shape({
    float: PropTypes.string,
    margin: PropTypes.string,
  }),
};

PopUpRemovePhotoConfirmation.defaultProps = {
  showModal: false,
  closeModal: false,
  removePhoto: false,
  buttonStyles: PropTypes.shape({
    float: 'right',
    margin: '15px',
  }),
};

export default PopUpRemovePhotoConfirmation;
