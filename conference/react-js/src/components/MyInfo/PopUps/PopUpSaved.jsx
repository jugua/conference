import React, { PureComponent } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';

class PopUpSaved extends PureComponent {
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
          <h3 className="pop-up__title">Saved</h3>
          <button
            className="pop-up__close"
            onClick={closeModal}
          />
          <p className="pop-up__notification">
            Changes saved successfully
          </p>
          <button
            className="btn btn_right btn_small"
            onClick={closeModal}
          >ok</button>
        </ReactModal>
      </div>
    );
  }
}

PopUpSaved.propTypes = {
  showModal: PropTypes.bool.isRequired,
  closeModal: PropTypes.func.isRequired,
};

PopUpSaved.defaultProps = {
  showModal: false,
};

export default PopUpSaved;
