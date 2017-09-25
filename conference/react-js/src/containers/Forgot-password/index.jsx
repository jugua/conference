import React from 'react';

function ForgotPassword() {
  return (
    <div className="pop-up-wrapper">
      <div className="pop-up">
        <h3 className="pop-up__title">Forgot password?</h3>
        <form noValidate name="userForm">
          <p className="pop-up__notification">Please enter your email:</p>
          <input
            type="email"
            className="field pop-up__input"
            name="mail"
            required
          />
          <span className="field-error error-title_pop-up">
            Please enter your registered email
          </span>
          <span className="field-error error-title_pop-up">
            Please enter a valid email address
          </span>
          <span className="field-error error-title_pop-up">
            We can not find an account with that email address
          </span>
          <div className="pop-up-button-wrapper">
            <input
              type="submit"
              className="btn pop-up__button"
              value="Continue"
            />
            <input
              type="button"
              className="btn pop-up__button btn_cancel"
              value="Cancel"
            />
          </div>
        </form>
        <button className="pop-up__close" />
      </div>

      <div className="pop-up">
        <h3 className="pop-up__title">
          Password forgotten
        </h3>
        <p className="pop-up__notification">
          We just emailed you a link.
          Please check your email and click the secure
          link.
        </p>
        <button
          className="btn pop-up__button"
        >
          Close
        </button>
      </div>
    </div>
  );
}

export default ForgotPassword;
