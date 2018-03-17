import React from 'react';
import PropTypes from 'prop-types';

import ForgotPasswordForm from
  '../../containers/Forgot-password/forgot-password-form';
import ForgotPasswordMessage from './ForgotPasswordMessage';

const ForgotPasswordWrapper = ({ close, message }) => (
  <div className="pop-up-wrapper">
    <div className="pop-up">
      <h3 className="pop-up__title">
        Forgot password
      </h3>
      {
        message
          ? <ForgotPasswordMessage message={message} close={close} />
          : <ForgotPasswordForm />
      }
      <button
        className="pop-up__close"
        onClick={close}
      />
    </div>
  </div>
);

ForgotPasswordWrapper.defaultProps = {
  message: '',
};

ForgotPasswordWrapper.propTypes = {
  close: PropTypes.func.isRequired,
  message: PropTypes.string,
};

export default ForgotPasswordWrapper;
