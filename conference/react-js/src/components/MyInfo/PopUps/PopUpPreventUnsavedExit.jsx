import React, { Component } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';

class PopUpPreventUnsavedExit extends Component {
  render() {
    const { showModal, closeModal } = this.props;
    return (
      <div>
        <ReactModal
          isOpen={showModal}
          contentLabel="onRequestClose"
          onRequestClose={this.handleCloseModal}
          name=""
          className={{
            base: classNames({
              'pop-up pop-up_big': true,
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
          <h3 className="pop-up__title">Attention</h3>
          <p className="pop-up__notification">
            Would you like to save changes?
          </p>
          <div className="pop-up-button-wrapper">
            <input
              type="button"
              value="yes"
              className="btn"
              onClick={closeModal}
            />
            <input
              type="button"
              value="no"
              className="btn btn_cancel"
              onClick={closeModal}
            />
          </div>
          <button
            className="pop-up__close"
            onClick={closeModal}
          />
        </ReactModal>
      </div>
    );
  }
}

PopUpPreventUnsavedExit.propTypes = {
  showModal: PropTypes.bool,
  closeModal: PropTypes.func,
};

PopUpPreventUnsavedExit.defaultProps = {
  showModal: false,
  closeModal: false,
};

export default PopUpPreventUnsavedExit;
