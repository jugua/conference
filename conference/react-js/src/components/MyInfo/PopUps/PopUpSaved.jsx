import React, { PureComponent } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import { RaisedButton } from 'material-ui';

class PopUpSaved extends PureComponent {
  render() {
    const { showModal, closeModal, buttonStyles } = this.props;
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
          <RaisedButton
            label="OK"
            primary
            style={buttonStyles}
            onClick={closeModal}
          />
        </ReactModal>
      </div>
    );
  }
}

PopUpSaved.propTypes = {
  showModal: PropTypes.bool,
  closeModal: PropTypes.func,
  buttonStyles: PropTypes.shape({
    float: PropTypes.string,
    margin: PropTypes.string,
  }),
};

PopUpSaved.defaultProps = {
  showModal: false,
  closeModal: false,
  buttonStyles: PropTypes.shape({
    float: 'right',
    margin: '15px',
  }),
};

export default PopUpSaved;
