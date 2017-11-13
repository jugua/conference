import React from 'react';
import ShowMainPage from './ShowMainPage';

const SignUpPopUp = () => (
  <div className="pop-up-wrapper">
    <div className="pop-up">
      <h3 className="pop-up__title">
        You&#39;ve successfully registered.
      </h3>
      <p className="pop-up__notification pop-up__notification_small">
        Please check your email to complete the registration.</p>
      <ShowMainPage />
    </div>
  </div>
);

export default SignUpPopUp;
