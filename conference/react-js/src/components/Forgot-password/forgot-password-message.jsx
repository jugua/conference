import React from 'react';
import { Link } from 'react-router-dom';

const ForgotPasswordMessage = () => (
  <div className="pop-up">
    <h3 className="pop-up__title">
      Password forgotten
    </h3>
    <p className="pop-up__notification">
      We just emailed you a link.
      Please check your email and click the secure
      link.
    </p>
    <Link
      className="btn pop-up__button"
      to="/"
    >
      Close
    </Link >
  </div>
);

export default ForgotPasswordMessage;
