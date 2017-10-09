import React, { Component } from 'react';

class SpeakerInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      popupIsShown: false,
      closePop: false,
    };
  }

  closePopUp = () => {
    const showPopUp = this.state.popupIsShown;
    this.setState({
      popupIsShown: !showPopUp,
    });
  }

    handleClick = (e) => {
      const closePop = this.state.closePop;
      this.setState({
        closePop: !closePop,
      });
      console.log(e.target);
    }

    render() {
      return (
        <div
          className={`pop-up-wrapper ${this.state.popupIsShown ? 'test' : ''} ${this.state.closePop ? 'close' : ''}`}
          role="presentation"
          onClick={this.handleClick}
        >
          <div
            className={`pop-up pop-up_big ${this.state.popupIsShown ? 'test' : ''}`}
          >
            <h3 className="pop-up__title talks-user-info-popup__title">Test</h3>
            <button
              className="pop-up__close"
              onClick={this.closePopUp}
            />
            <div className="talks-user-info-popup__form-wrapper">
              <img className="my-info__ava" src="" alt="" />
              <form className="my-info" name="">
                <label
                  htmlFor="my-info-bio"
                  className="form-label my-info__label my-info__label_bio"
                >Short Bio
                </label>
                <textarea
                  name="bio"
                  id="my-info-bio"
                  className="textarea my-info__bio"
                  maxLength="2000"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-job-title"
                >Job Title</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-job-title"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-company"
                >Company</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-company"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-past-conferences"
                >Past Conferences</label>
                <textarea
                  type="text"
                  name="past"
                  id="my-past-conferences"
                  rows="5"
                  className="textarea"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-email"
                >Email</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-email"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-linkedin"
                >LinkedIn</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-linkedin"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-twitter"
                >twitter</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-twitter"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-facebook"
                >facebook</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-facebook"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-blog"
                >blog</label>
                <input
                  type="text"
                  className="field field_border"
                  id="my-blog"
                  disabled
                />
                <label
                  className="form-label"
                  htmlFor="my-additional-info"
                >Additional Info</label>
                <textarea
                  rows="5"
                  className="textarea"
                  id="my-additional-info"
                  disabled
                  maxLength="1000"
                />
              </form>
              <button className="btn talks-user-info-popup__button">close</button>
            </div>
          </div>
        </div>
      );
    }
}

export default SpeakerInfo;
