import React from 'react';

const SpeakerInfoTest = () => (
  <div className="my-info-wrapper">
    <img src="" alt="" />
    <form
      className="my-info"
      name="userInfoForm"
      noValidate
    >
      <label
        htmlFor="my-info-bio"
        className="form-label form-label_required my-info__label
                   my-info__label_bio"
      >
          Short Bio</label>
      <textarea
        name="bio"
        id="my-info-bio"
        className="textarea my-info__bio"
        maxLength="2000"
      />
      <label
        htmlFor="my-info-job"
        className="form-label form-label_requiredmy-info__label"
      >
                        Job Title</label>
      <input
        type="text"
        name="job"
        id="my-info-job"
        className="field field_border my-info__field_job"
        maxLength="256"
      />
      <label
        htmlFor="my-info-company"
        className="form-label form-label_required my-info__label"
      >
                            Company
        <img className="icon icon_info" src="" alt="" />
        <span className="tooltip">e.g. Company name or self-employed</span>
      </label>
      <input
        type="text"
        name="company"
        id="my-info-company"
        className="field field_border my-info__field_company"
        maxLength="256"
      />
      <label
        htmlFor="my-info-linkedin-past-conferences"
        className="form-label my-info__label"
      >
                                Past Conferences</label>
      <textarea
        type="text"
        name="past"
        id="my-info-linkedin-past-conferences"
        rows="5"
        className="textarea"
        maxLength="1000"
      />
      <label
        htmlFor="my-info-linkedin"
        className="form-label my-info__label"
      >
                                LinkedIn</label>
      <input
        type="text"
        name="linkedin"
        id="my-info-linkedin"
        className="field field_border"
      />
      <label
        htmlFor="my-info-twitter"
        className="form-label my-info__label"
      >twitter</label>
      <input
        type="text"
        name="twitter"
        id="my-info-twitter"
        className="field field_border"
      />
      <label
        htmlFor="my-info-facebook"
        className="form-label my-info__label"
      >facebook</label>
      <input
        type="text"
        name="facebook"
        id="my-info-facebook"
        className="field field_border"
      />
      <label
        htmlFor="my-info-blog"
        className="form-label my-info__label"
      >blog</label>
      <input
        type="text"
        name="blog"
        id="my-info-blog"
        className="field field_border"
      />
      <label
        htmlFor="my-info-additional-info"
        className="form-label my-info__label"
      >Additional Info</label>
      <textarea
        name="info"
        id="my-info-additional-info"
        rows="5"
        className="textarea"
        maxLength="1000"
      />
      <input
        type="submit"
        value="save"
        className="btn my-info__button"
      />
    </form>
  </div>

);
export default SpeakerInfoTest;
