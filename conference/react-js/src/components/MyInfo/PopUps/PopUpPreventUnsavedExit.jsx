import React, { Component } from 'react';
import ReactModal from 'react-modal';
import classNames from 'classnames';

class PopUpPreventUnsavedExit extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
    };
  }

  handleOpenModal = () => {
    this.setState({ showModal: true });
  };

  handleCloseModal = () => {
    this.setState({
      showModal: false,
    });
  };

  render() {
    return (
      <div>
        <button onClick={this.handleOpenModal}>Trigger Modal</button>
        <ReactModal
          isOpen={this.state.showModal}
          contentLabel="onRequestClose"
          onRequestClose={this.handleCloseModal}
          className={{
            base: classNames({
              'pop-up pop-up_big': true,
              'pop-up_close': !this.state.showModal,
            }),
          }}
          overlayClassName={{
            afterOpen: classNames({
              'pop-up-wrapper': true,
              'pop-up_close': !this.state.showModal,
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
              onClick={this.handleCloseModal}
            />
            <input
              type="button"
              value="no"
              className="btn btn_cancel"
              onClick={this.handleCloseModal}
            />
          </div>
          <button
            className="pop-up__close"
            onClick={this.handleCloseModal}
          />
        </ReactModal>
      </div>
    );
  }
}

export default PopUpPreventUnsavedExit;
