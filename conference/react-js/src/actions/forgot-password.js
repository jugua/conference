import axios from 'axios';

import ACTIONS_TYPES from '../constants/actions-types';
import { forgotPassword } from '../constants/backend-url';

const SUCCESS_MESSAGE = 'SUCCESS_MESSAGE';
const ERROR_MESSAGE = 'ERROR_MESSAGE';

const forgotPasswordAction = (type, payload) => ({ type, payload });

const setForgotPasswordError = (error = '') => forgotPasswordAction(
  ACTIONS_TYPES.SET_FORGOT_PASSWORD_ERROR,
  error,
);

const setForgotPasswordMessage = (message = '') => forgotPasswordAction(
  ACTIONS_TYPES.SET_FORGOT_PASSWORD_MESSAGE,
  message,
);

const setForgotPasswordVisibility =
  (visibility = false) => dispatch => dispatch(forgotPasswordAction(
    ACTIONS_TYPES.SET_FORGOT_PASSWORD_VISIBILITY,
    visibility,
  ));

const sendForgotPasswordEmail = mail => dispatch => (
  axios.post(forgotPassword, { mail })
    .then(() => dispatch(setForgotPasswordMessage(SUCCESS_MESSAGE)))
    .catch(() => dispatch(setForgotPasswordError(ERROR_MESSAGE)))
);

const clearForgotPasswordError = () => dispatch => dispatch(
  setForgotPasswordError(),
);

export default {
  clearForgotPasswordError,
  sendForgotPasswordEmail,
  setForgotPasswordVisibility,
};
